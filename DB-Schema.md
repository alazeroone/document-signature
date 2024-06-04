Designing a schema for persisting transaction values related to document signature requests in PostgreSQL involves creating tables that capture the details of documents, signers, signature requests, and the state of each signature. Here’s a proposed schema to achieve this:

### Tables and Relationships

1. **documents**: Stores information about the documents.
2. **signers**: Stores information about the signers.
3. **signature_requests**: Stores information about each signature request.
4. **signature_positions**: Stores information about where signatures are needed on each document.
5. **signatures**: Tracks the actual signatures made by the signers.
6. **request_status**: Tracks the status of each signature request.

### Table Definitions

#### 1. `documents`
```sql
CREATE TABLE documents (
    document_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    blob_url TEXT NOT NULL,  -- URL to the document stored in Azure Blob Storage
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### 2. `signers`
```sql
CREATE TABLE signers (
    signer_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### 3. `signature_requests`
```sql
CREATE TABLE signature_requests (
    request_id SERIAL PRIMARY KEY,
    created_by INTEGER REFERENCES signers(signer_id),
    status VARCHAR(50) DEFAULT 'created',  -- status can be 'created', 'sent', 'completed', 'voided'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### 4. `signature_positions`
```sql
CREATE TABLE signature_positions (
    position_id SERIAL PRIMARY KEY,
    document_id INTEGER REFERENCES documents(document_id),
    signer_id INTEGER REFERENCES signers(signer_id),
    page INTEGER NOT NULL,
    x_coordinate INTEGER NOT NULL,
    y_coordinate INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### 5. `signatures`
```sql
CREATE TABLE signatures (
    signature_id SERIAL PRIMARY KEY,
    request_id INTEGER REFERENCES signature_requests(request_id),
    signer_id INTEGER REFERENCES signers(signer_id),
    document_id INTEGER REFERENCES documents(document_id),
    signed BOOLEAN DEFAULT FALSE,
    signed_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### 6. `request_status`
```sql
CREATE TABLE request_status (
    status_id SERIAL PRIMARY KEY,
    request_id INTEGER REFERENCES signature_requests(request_id),
    status VARCHAR(50) NOT NULL,  -- status can be 'created', 'sent', 'completed', 'voided'
    status_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Relationships

- A **document** can be part of multiple **signature requests**.
- A **signer** can participate in multiple **signature requests**.
- Each **signature request** can have multiple **signature positions** that specify where and who needs to sign.
- **Signatures** track the actual act of signing, linking a **signer** and a **document** within a **signature request**.
- **Request statuses** track the historical changes in the status of a **signature request**.

### Sample Data Insertion

Here's an example of how you might insert data into these tables:

#### Insert Documents
```sql
INSERT INTO documents (name, blob_url)
VALUES
('Contract.pdf', 'https://example.blob.core.windows.net/documents/contract.pdf'),
('NDA.pdf', 'https://example.blob.core.windows.net/documents/nda.pdf');
```

#### Insert Signers
```sql
INSERT INTO signers (name, email)
VALUES
('Alice', 'alice@example.com'),
('Bob', 'bob@example.com');
```

#### Create a Signature Request
```sql
INSERT INTO signature_requests (created_by, status)
VALUES
(1, 'created');
```

#### Define Signature Positions
```sql
INSERT INTO signature_positions (document_id, signer_id, page, x_coordinate, y_coordinate)
VALUES
(1, 1, 1, 100, 200),
(1, 2, 1, 150, 250),
(2, 1, 2, 200, 300),
(2, 2, 2, 250, 350);
```

#### Track Signatures
```sql
INSERT INTO signatures (request_id, signer_id, document_id, signed)
VALUES
(1, 1, 1, FALSE),
(1, 2, 1, FALSE),
(1, 1, 2, FALSE),
(1, 2, 2, FALSE);
```

#### Update Request Status
```sql
INSERT INTO request_status (request_id, status)
VALUES
(1, 'created');
```

### Design Considerations

1. **Normalization**: The schema is normalized to reduce data redundancy and improve data integrity.
2. **Indexes**: Consider adding indexes on foreign keys and frequently queried columns to improve query performance.
3. **History Tracking**: The `request_status` table allows tracking the history of status changes for each request.
4. **Scalability**: By storing documents in Azure Blob Storage and referencing them via URLs, you can scale storage independently of your database.

This schema provides a solid foundation for managing document signature requests and can be extended as needed for additional functionality.

Certainly! Below is the UML diagram representing the entity relationships for the schema described:

### UML Diagram for Document Signature Request System

```plaintext
+--------------------+
|     documents      |
+--------------------+
| - document_id : PK |
| - name : String    |
| - blob_url : String|
| - created_at : Date|
| - updated_at : Date|
+--------------------+

+-------------------+
|      signers      |
+-------------------+
| - signer_id : PK  |
| - name : String   |
| - email : String  |
| - created_at : Date|
| - updated_at : Date|
+-------------------+

+----------------------------+
|   signature_requests       |
+----------------------------+
| - request_id : PK          |
| - created_by : FK          |
| - status : String          |
| - created_at : Date        |
| - updated_at : Date        |
+----------------------------+

+----------------------------+
|  signature_positions       |
+----------------------------+
| - position_id : PK         |
| - document_id : FK         |
| - signer_id : FK           |
| - page : Integer           |
| - x_coordinate : Integer   |
| - y_coordinate : Integer   |
| - created_at : Date        |
| - updated_at : Date        |
+----------------------------+

+---------------------------+
|       signatures          |
+---------------------------+
| - signature_id : PK       |
| - request_id : FK         |
| - signer_id : FK          |
| - document_id : FK        |
| - signed : Boolean        |
| - signed_at : Date        |
| - created_at : Date       |
| - updated_at : Date       |
+---------------------------+

+---------------------------+
|     request_status        |
+---------------------------+
| - status_id : PK          |
| - request_id : FK         |
| - status : String         |
| - status_date : Date      |
| - created_at : Date       |
+---------------------------+
```

### Relationships:
1. **documents**:
    - `document_id` is the primary key.
    - Referenced by `signature_positions` and `signatures`.

2. **signers**:
    - `signer_id` is the primary key.
    - Referenced by `signature_requests`, `signature_positions`, and `signatures`.

3. **signature_requests**:
    - `request_id` is the primary key.
    - `created_by` references `signers(signer_id)`.
    - Referenced by `signatures` and `request_status`.

4. **signature_positions**:
    - `position_id` is the primary key.
    - `document_id` references `documents(document_id)`.
    - `signer_id` references `signers(signer_id)`.

5. **signatures**:
    - `signature_id` is the primary key.
    - `request_id` references `signature_requests(request_id)`.
    - `signer_id` references `signers(signer_id)`.
    - `document_id` references `documents(document_id)`.

6. **request_status**:
    - `status_id` is the primary key.
    - `request_id` references `signature_requests(request_id)`.

### Diagram Representation

Here's a visual representation of the entity relationships:

```plaintext
+------------------+        +------------------+
|    documents     |        |     signers      |
|------------------|        |------------------|
| - document_id PK |        | - signer_id PK   |
| - name           |        | - name           |
| - blob_url       |        | - email          |
| - created_at     |        | - created_at     |
| - updated_at     |        | - updated_at     |
+------------------+        +------------------+
        |                           |
        |                           |
        |                           |
        +--------------------+      |
                             |      |
                             v      v
                   +-----------------------+
                   |   signature_requests  |
                   |-----------------------|
                   | - request_id PK       |
                   | - created_by FK       +------+
                   | - status              |      |
                   | - created_at          |      |
                   | - updated_at          |      |
                   +-----------------------+      |
                             |                     |
                             v                     |
                   +-----------------------+       |
                   |   signature_positions |       |
                   |-----------------------|       |
                   | - position_id PK      |       |
                   | - document_id FK      |       |
                   | - signer_id FK        |       |
                   | - page                |       |
                   | - x_coordinate        |       |
                   | - y_coordinate        |       |
                   | - created_at          |       |
                   | - updated_at          |       |
                   +-----------------------+       |
                             |                     |
                             v                     v
                   +-----------------------+  +----------------+
                   |      signatures       |  |  request_status|
                   |-----------------------|  |----------------|
                   | - signature_id PK     |  | - status_id PK |
                   | - request_id FK       |  | - request_id FK|
                   | - signer_id FK        |  | - status       |
                   | - document_id FK      |  | - status_date  |
                   | - signed              |  | - created_at   |
                   | - signed_at           |  +----------------+
                   | - created_at          |
                   | - updated_at          |
                   +-----------------------+
```

This UML diagram visually depicts the relationships between the tables, showing how each entity interacts with others through foreign key references. This design will help you in creating and managing document signature requests efficiently.