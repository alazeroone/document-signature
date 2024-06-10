package com.poc.project.documentsignature.service;

import com.poc.project.documentsignature.dto.*;
import com.poc.project.documentsignature.entity.*;
import com.poc.project.documentsignature.mapper.DocumentMapper;
import com.poc.project.documentsignature.mapper.SignerMapper;
import com.poc.project.documentsignature.mapper.SignatureRequestMapper;
import com.poc.project.documentsignature.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing signature requests.
 */
@Service
public class SignatureRequestService {

    @Autowired
    private SignatureRequestRepository signatureRequestRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private SignerRepository signerRepository;

    @Autowired
    private SignaturePositionRepository signaturePositionRepository;

    @Autowired
    private RequestDocumentRepository requestDocumentRepository;

    @Autowired
    private RequestSignerRepository requestSignerRepository;

    @Autowired
    private DocumentMapper documentMapper;

    @Autowired
    private SignerMapper signerMapper;

    @Autowired
    private SignatureRequestMapper signatureRequestMapper;

    @Autowired
    private AdobeSignService adobeSignService;

    @Autowired
    private DocuSignService docusignService;

    /**
     * Factory method to get the appropriate signature service based on the provider ID.
     *
     * @param providerId The provider ID.
     * @return The appropriate signature service.
     */
    private SignatureService getSignatureService(Integer providerId) {
        if (providerId == 1) { // Assuming 1 is the ID for Adobe Sign
            return adobeSignService;
        } else if (providerId == 2) { // Assuming 2 is the ID for DocuSign
            return docusignService;
        }
        throw new IllegalArgumentException("Invalid provider ID");
    }

    /**
     * Create a new signature request.
     *
     * @param request The request details.
     * @return The created signature request as a DTO.
     */
    @Transactional
    public SignatureRequestDTO createSignatureRequest(CreateSignatureRequest request) {

        SignatureRequest signatureRequest = new SignatureRequest();
        signatureRequest.setCreatedBy(request.getCreatedBy());
        signatureRequest.setProviderId(request.getProviderId());
        signatureRequest.setStatus("Created");
        signatureRequest = signatureRequestRepository.save(signatureRequest);

        for (DocumentDTO documentDTO : request.getDocuments()) {
            Document document = documentMapper.toEntity(documentDTO);
            document.setStatus("Pending");
            documentRepository.save(document);

            RequestDocument requestDocument = new RequestDocument();
            requestDocument.setRequestId(signatureRequest.getRequestId());
            requestDocument.setDocumentId(document.getDocumentId());
            requestDocumentRepository.save(requestDocument);
        }

        for (SignerDTO signerDTO : request.getSigners()) {
            Signer signer = signerMapper.toEntity(signerDTO);
            signer.setStatus("Pending");
            signerRepository.save(signer);

            RequestSigner requestSigner = new RequestSigner();
            requestSigner.setRequestId(signatureRequest.getRequestId());
            requestSigner.setSignerId(signer.getSignerId());
            requestSignerRepository.save(requestSigner);
        }

        return signatureRequestMapper.toDTO(signatureRequest);
    }

    /**
     * Send a signature request.
     *
     * @param request The request details.
     */
    @Transactional
    public void sendSignatureRequest(SendSignatureRequest request) {
        Optional<SignatureRequest> signatureRequestOpt = signatureRequestRepository.findById(request.getRequestId());
        if (signatureRequestOpt.isPresent()) {
            SignatureRequest signatureRequest = signatureRequestOpt.get();
            SignatureService signatureService = getSignatureService(signatureRequest.getProviderId());
            signatureService.sendDocumentForSigning();

            signatureRequest.setStatus("Sent");
            signatureRequestRepository.save(signatureRequest);
        }
    }

    /**
     * Retrieve the status of a signature request.
     *
     * @param requestId The request ID.
     * @return The status of the signature request as a DTO.
     */
    public SignatureRequestDTO getSignatureRequestStatus(Integer requestId) {
        Optional<SignatureRequest> signatureRequestOpt = signatureRequestRepository.findById(requestId);
        if (signatureRequestOpt.isPresent()) {
            SignatureRequest signatureRequest = signatureRequestOpt.get();
            return signatureRequestMapper.toDTO(signatureRequest);
        }
        return null;
    }

    /**
     * Retrieve the status of a specific signer for a request.
     *
     * @param requestId The request ID.
     * @param signerId The signer ID.
     * @return The status of the signer as a DTO.
     */
    public SignerDTO getSignerStatus(Integer requestId, String signerId) {
        Optional<RequestSigner> requestSignerOpt = requestSignerRepository.findByRequestIdAndSignerId(requestId, signerId);
        if (requestSignerOpt.isPresent()) {
            RequestSigner requestSigner = requestSignerOpt.get();
            Optional<Signer> signerOpt = signerRepository.findById(requestSigner.getSignerId());
            return signerOpt.map(signerMapper::toDTO).orElse(null);
        }
        return null;
    }

    /**
     * Retrieve all signature requests.
     *
     * @return A list of all signature requests as DTOs.
     */
    public List<SignatureRequestDTO> getAllSignatureRequests() {
        List<SignatureRequest> signatureRequests = signatureRequestRepository.findAll();
        return signatureRequests.stream()
                .map(signatureRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve all signers for a specified request.
     *
     * @param requestId The request ID.
     * @return A list of signers for the specified request as DTOs.
     */
    public List<SignerDTO> getSignersForRequest(Integer requestId) {
        List<RequestSigner> requestSigners = requestSignerRepository.findByRequestId(requestId);
        return requestSigners.stream()
                .map(requestSigner -> signerRepository.findById(requestSigner.getSignerId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(signerMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve all documents for a specified request.
     *
     * @param requestId The request ID.
     * @return A list of documents for the specified request as DTOs.
     */
    public List<DocumentDTO> getDocumentsForRequest(Integer requestId) {
        List<RequestDocument> requestDocuments = requestDocumentRepository.findByRequestId(requestId);
        return requestDocuments.stream()
                .map(requestDocument -> documentRepository.findById(requestDocument.getDocumentId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(documentMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Download all signed documents for a specified request.
     *
     * @param requestId The request ID.
     */
    public void downloadSignedDocuments(Integer requestId) {
        Optional<SignatureRequest> signatureRequestOpt = signatureRequestRepository.findById(requestId);
        if (signatureRequestOpt.isPresent()) {
            SignatureRequest signatureRequest = signatureRequestOpt.get();
            SignatureService signatureService = getSignatureService(signatureRequest.getProviderId());
            signatureService.retrieveSignedDocument();
        }
    }

    /**
     * Resend a signature request to a specific signer.
     *
     * @param request The request details.
     */
    public void resendSignatureRequest(ResendSignatureRequest request) {
        Optional<SignatureRequest> signatureRequestOpt = signatureRequestRepository.findById(request.getRequestId());
        if (signatureRequestOpt.isPresent()) {
            SignatureRequest signatureRequest = signatureRequestOpt.get();
            SignatureService signatureService = getSignatureService(signatureRequest.getProviderId());
            signatureService.sendDocumentForSigning();
        }
    }

    /**
     * Cancel a signature request.
     *
     * @param request The request details.
     */
    public void cancelSignatureRequest(CancelSignatureRequest request) {
        Optional<SignatureRequest> signatureRequestOpt = signatureRequestRepository.findById(request.getRequestId());
        if (signatureRequestOpt.isPresent()) {
            SignatureRequest signatureRequest = signatureRequestOpt.get();
            signatureRequest.setStatus("Cancelled");
            signatureRequestRepository.save(signatureRequest);
        }
    }

    /**
     * Add a signer to an existing request.
     *
     * @param request The request details.
     */
    public void addSignerToRequest(AddSignerRequest request) {
        Optional<SignatureRequest> signatureRequestOpt = signatureRequestRepository.findById(request.getRequestId());
        if (signatureRequestOpt.isPresent()) {
            Signer signer = signerMapper.toEntity(request.getSigner());
            signer.setStatus("Pending");
            signerRepository.save(signer);

            RequestSigner requestSigner = new RequestSigner();
            requestSigner.setRequestId(request.getRequestId());
            requestSigner.setSignerId(signer.getSignerId());
            requestSignerRepository.save(requestSigner);
        }
    }

    /**
     * Remove a signer from an existing request.
     *
     * @param request The request details.
     */
    public void removeSignerFromRequest(RemoveSignerRequest request) {
        requestSignerRepository.deleteByRequestIdAndSignerId(request.getRequestId(), request.getSignerId());
    }


    /**
     * Add a document to an existing request.
     *
     * @param request The request details.
     */
    public void addDocumentToRequest(AddDocumentRequest request) {
        Optional<SignatureRequest> signatureRequestOpt = signatureRequestRepository.findById(request.getRequestId());
        if (signatureRequestOpt.isPresent()) {
            Document document = documentMapper.toEntity(request.getDocument());
            document.setStatus("Pending");
            documentRepository.save(document);

            RequestDocument requestDocument = new RequestDocument();
            requestDocument.setRequestId(request.getRequestId());
            requestDocument.setDocumentId(document.getDocumentId());
            requestDocumentRepository.save(requestDocument);

            SignatureService signatureService = getSignatureService(signatureRequestOpt.get().getProviderId());
            signatureService.addDocumentToRequest();
        }
    }

    /**
     * Remove a document from an existing request.
     *
     * @param request The request details.
     */
    public void removeDocumentFromRequest(RemoveDocumentRequest request) {

        Optional<SignatureRequest> signatureRequestOpt = signatureRequestRepository.findById(request.getRequestId());
        if (signatureRequestOpt.isPresent()) {
       //     requestDocumentRepository.deleteByRequestIdAndDocumentId(request.getRequestId(), request.getDocumentId());

            SignatureService signatureService = getSignatureService(signatureRequestOpt.get().getProviderId());
            signatureService.removeDocumentFromRequest();
        }
    }

    /**
     * Add a signer to an existing request.
     *
     * @param request The request details.
     */
    public void addSignerToRequest(AddSignerRequest request) {
        Optional<SignatureRequest> signatureRequestOpt = signatureRequestRepository.findById(request.getRequestId());
        if (signatureRequestOpt.isPresent()) {
            Signer signer = signerMapper.toEntity(request.getSigner());
            signer.setStatus("Pending");
            signerRepository.save(signer);

            RequestSigner requestSigner = new RequestSigner();
            requestSigner.setRequestId(request.getRequestId());
            requestSigner.setSignerId(signer.getSignerId());
            requestSignerRepository.save(requestSigner);

            SignatureService signatureService = getSignatureService(signatureRequestOpt.get().getProviderId());
            signatureService.addSignerToRequest();
        }
    }

    /**
     * Remove a signer from an existing request.
     *
     * @param request The request details.
     */
    public void removeSignerFromRequest(RemoveSignerRequest request) {
        Optional<SignatureRequest> signatureRequestOpt = signatureRequestRepository.findById(request.getRequestId());
        if (signatureRequestOpt.isPresent()) {
            requestSignerRepository.deleteByRequestIdAndSignerId(request.getRequestId(), request.getSignerId());

            SignatureService signatureService = getSignatureService(signatureRequestOpt.get().getProviderId());
            signatureService.removeSignerFromRequest();
        }
    }

    /**
     * Generate an embedded signing URL for a signer.
     *
     * @param requestId The request ID.
     * @param signerId The signer ID.
     * @return The embedded signing URL.
     */
    public String generateEmbeddedSigningUrl(Integer requestId, String signerId) {

        Optional<SignatureRequest> signatureRequestOpt = signatureRequestRepository.findById(requestId);
        if (signatureRequestOpt.isPresent()) {
            SignatureRequest signatureRequest = signatureRequestOpt.get();
            Optional<RequestSigner> requestSignerOpt = requestSignerRepository.findByRequestIdAndSignerId(requestId, signerId);
            if (requestSignerOpt.isPresent()) {
                SignatureService signatureService = getSignatureService(signatureRequest.getProviderId());
                return signatureService.generateEmbeddedSigningUrl(signerId, signatureRequest.getDocumentId());
            }
        }
        return null;
    }




    }