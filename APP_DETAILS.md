### Scenarios Based on Signers and Documents

1. **Single Document, Single Signer, Single Signature:**
    - One document.
    - One signer.
    - The signer needs to sign only once.

2. **Single Document, Single Signer, Multiple Signatures:**
    - One document.
    - One signer.
    - The signer needs to sign in multiple places within the document.

3. **Single Document, Multiple Signers, Single Signature Each:**
    - One document.
    - Multiple signers.
    - Each signer signs once in designated places.

4. **Single Document, Multiple Signers, Multiple Signatures Each:**
    - One document.
    - Multiple signers.
    - Each signer needs to sign in multiple places within the document.

5. **Multiple Documents, Single Signer, Single Signature Each Document:**
    - Multiple documents.
    - One signer.
    - The signer needs to sign each document once.

6. **Multiple Documents, Single Signer, Multiple Signatures Each Document:**
    - Multiple documents.
    - One signer.
    - The signer needs to sign in multiple places within each document.

7. **Multiple Documents, Multiple Signers, Single Signature Each Document:**
    - Multiple documents.
    - Multiple signers.
    - Each signer needs to sign once per document.

8. **Multiple Documents, Multiple Signers, Multiple Signatures Each Document:**
    - Multiple documents.
    - Multiple signers.
    - Each signer needs to sign in multiple places within each document.

### Additional Operations Based on Scenarios

To manage these scenarios, here are the essential operations and API functionalities you would need:

#### 1. **Create Document Signature Request**
- **API**: `POST /signature-request/create`
- **Details**: Create a new signature request for one or more documents with specified signers and required signature positions.

#### 2. **Send Document Signature Request**
- **API**: `POST /signature-request/send`
- **Details**: Send the signature request to all specified signers.

#### 3. **Retrieve Document Signature Request Status**
- **API**: `GET /signature-request/status/{requestId}`
- **Details**: Retrieve the current status of a signature request, including which signers have signed and which are pending.

#### 4. **Void Document Signature Request**
- **API**: `POST /signature-request/void`
- **Details**: Void an existing signature request, making it inactive.

#### 5. **Resend Document Signature Request**
- **API**: `POST /signature-request/resend`
- **Details**: Resend the signature request to signers who have not yet signed.

#### 6. **Cancel Document Signature Request**
- **API**: `POST /signature-request/cancel`
- **Details**: Cancel an existing signature request, similar to voiding but with a potential option to reactivate.

#### 7. **Download Signed Documents**
- **API**: `GET /signature-request/download/{requestId}`
- **Details**: Download the fully signed document(s) once all signatures are collected.

#### 8. **List All Document Signature Requests**
- **API**: `GET /signature-request/list`
- **Details**: Retrieve a list of all signature requests, optionally filtered by status, date range, etc.

#### 9. **Add Signer to Existing Request**
- **API**: `POST /signature-request/add-signer`
- **Details**: Add a new signer to an existing signature request.

#### 10. **Remove Signer from Existing Request**
- **API**: `POST /signature-request/remove-signer`
- **Details**: Remove an existing signer from a signature request.

#### 11. **Update Signer Information**
- **API**: `POST /signature-request/update-signer`
- **Details**: Update the information (e.g., email, name) of a signer in an existing request.

#### 12. **Add Document to Existing Request**
- **API**: `POST /signature-request/add-document`
- **Details**: Add an additional document to an existing signature request.

#### 13. **Remove Document from Existing Request**
- **API**: `POST /signature-request/remove-document`
- **Details**: Remove a document from an existing signature request.

### Example Workflow

Here is an example workflow to illustrate how these scenarios and operations could be utilized:

1. **Create a signature request** for multiple documents requiring multiple signatures from multiple signers:
   ```http
   POST /signature-request/create
   {
       "documents": [
           {"documentId": "doc1", "name": "Contract.pdf"},
           {"documentId": "doc2", "name": "NDA.pdf"}
       ],
       "signers": [
           {"signerId": "signer1", "name": "Alice", "email": "alice@example.com"},
           {"signerId": "signer2", "name": "Bob", "email": "bob@example.com"}
       ],
       "signaturePositions": [
           {"documentId": "doc1", "signerId": "signer1", "page": 1, "x": 100, "y": 200},
           {"documentId": "doc1", "signerId": "signer2", "page": 1, "x": 150, "y": 250},
           {"documentId": "doc2", "signerId": "signer1", "page": 2, "x": 200, "y": 300},
           {"documentId": "doc2", "signerId": "signer2", "page": 2, "x": 250, "y": 350}
       ]
   }
   ```

2. **Send the signature request** to all specified signers:
   ```http
   POST /signature-request/send
   {
       "requestId": "request1"
   }
   ```

3. **Retrieve the status** of the signature request:
   ```http
   GET /signature-request/status/request1
   ```

4. **Resend the request** to a specific signer who hasn’t signed yet:
   ```http
   POST /signature-request/resend
   {
       "requestId": "request1",
       "signerId": "signer2"
   }
   ```

5. **Download the signed documents** once all signers have completed the signing process:
   ```http
   GET /signature-request/download/request1
   ```
