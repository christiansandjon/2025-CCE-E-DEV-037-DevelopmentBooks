## API Endpoints

The application exposes REST endpoints for testing with Postman or Insomnia.

### Start the Application
```bash
mvn spring-boot:run
```

The API will be available at: `http://localhost:8080`

### Available Endpoints

#### 1. Health Check
```
GET http://localhost:8080/api/bookstore/health
```

**Response:**
```
Bookstore API is running
```

#### 2. Get Available Books
```
GET http://localhost:8080/api/bookstore/books
```

**Response:**
```json
[
  "CLEAN_CODE",
  "THE_CLEAN_CODER",
  "CLEAN_ARCHITECTURE",
  "TEST_DRIVEN_DEVELOPMENT",
  "WORKING_EFFECTIVELY_WITH_LEGACY_CODE"
]
```

#### 3. Calculate Price
```
POST http://localhost:8080/api/bookstore/calculate
Content-Type: application/json
```

**Request Body:**
```json
[
  "CLEAN_CODE",
  "CLEAN_CODE",
  "THE_CLEAN_CODER",
  "CLEAN_ARCHITECTURE",
  "CLEAN_ARCHITECTURE",
  "CLEAN_ARCHITECTURE",
  "TEST_DRIVEN_DEVELOPMENT",
  "WORKING_EFFECTIVELY_WITH_LEGACY_CODE"
]
```

**Response:**
```json
{
  "price": 320.0,
  "message": "Price calculated successfully",
  "bookCounts": {
    "CLEAN_CODE": 2,
    "THE_CLEAN_CODER": 2,
    "CLEAN_ARCHITECTURE": 2,
    "TEST_DRIVEN_DEVELOPMENT": 1,
    "WORKING_EFFECTIVELY_WITH_LEGACY_CODE": 1
  }
}
```

### Example Postman Requests

**Example 1: Empty basket**
```json
[]
```
Response: `{"price": 0.0, ...}`

**Example 2: Two different books**
```json
["CLEAN_CODE", "THE_CLEAN_CODER"]
```
Response: `{"price": 95.0, ...}`

**Example 3: Complex case (8 books)**
```json
[
  "CLEAN_CODE",
  "CLEAN_CODE",
  "THE_CLEAN_CODER",
  "THE_CLEAN_CODER",
  "CLEAN_ARCHITECTURE",
  "CLEAN_ARCHITECTURE",
  "TEST_DRIVEN_DEVELOPMENT",
  "WORKING_EFFECTIVELY_WITH_LEGACY_CODE"
]
```
Response: `{"price": 320.0, ...}`