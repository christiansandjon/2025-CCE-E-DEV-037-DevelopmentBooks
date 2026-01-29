# Development Books Price Calculator

TDD Kata for BNP Paribas Fortis

## About

This project implements a price calculator for software development books with discount rules based on book diversity. Developed using Test Driven Development (TDD) methodology following Uncle Bob's three laws.

## The Problem

Calculate the optimal price for a shopping basket of 5 available books:
1. Clean Code (Robert Martin, 2008)
2. The Clean Coder (Robert Martin, 2011)
3. Clean Architecture (Robert Martin, 2017)
4. Test Driven Development by Example (Kent Beck, 2003)
5. Working Effectively With Legacy Code (Michael C. Feathers, 2004)

### Pricing Rules
- **Base price**: 50 EUR per book
- **Discount on sets of different books**:
    - 2 different books: 5% discount
    - 3 different books: 10% discount
    - 4 different books: 20% discount
    - 5 different books: 25% discount

### Example
**Basket**: 2× Clean Code, 2× Clean Coder, 2× Clean Architecture, 1× TDD, 1× Legacy Code

**Optimal grouping**:
- Group 1: 4 different books → 4 × 50 × 0.80 = 160 EUR
- Group 2: 4 different books → 4 × 50 × 0.80 = 160 EUR
- **Total: 320 EUR**

## Technical Stack

- Java 17
- Spring Boot 3.5.4
- Maven
- JUnit 5

## Build & Run

### Compile
```bash
mvn clean compile
```

### Run Tests
```bash
mvn test
```

### Run Application
```bash
mvn spring-boot:run
```

## Implementation Approach

The solution uses a **greedy algorithm with optimization**:

1. Build groups by taking one of each available book type
2. Detect the 5+3 pattern (which is suboptimal)
3. Transform 5+3 groups into 4+4 groups for better pricing
4. Calculate the total with applicable discounts

This approach balances simplicity and optimality for the given problem constraints.

## Test Coverage

The implementation includes 8 comprehensive tests covering:
- Empty basket
- Single book
- Multiple identical books
- Different discount tiers (2, 3, 4, 5 books)
- Mixed cases (identical + different books)
- Complex optimization case (8 books)

All tests pass

## API Endpoints

The application exposes REST API endpoints that can be tested with Postman, Insomnia, or curl.

### Start the Application
```bash
mvn spring-boot:run
```

The API will be available at: **http://localhost:8080**

### Available Endpoints

#### 1. Health Check
```
GET http://localhost:8080/api/bookstore/health
```

Returns: `"Bookstore API is running"`

#### 2. Get Available Books
```
GET http://localhost:8080/api/bookstore/books
```

Returns the list of all available books:
```json
["CLEAN_CODE", "THE_CLEAN_CODER", "CLEAN_ARCHITECTURE", "TEST_DRIVEN_DEVELOPMENT", "WORKING_EFFECTIVELY_WITH_LEGACY_CODE"]
```

#### 3. Calculate Price
```
POST http://localhost:8080/api/bookstore/calculate
Content-Type: application/json
```

**Request Body:** JSON array of book names

**Response:** Price with details

### Examples for Postman/Insomnia

**Example 1: Two different books (5% discount) = 95 EUR**
```json
["CLEAN_CODE", "THE_CLEAN_CODER"]
```

Expected response:
```json
{
  "price": 95.0,
  "message": "Price calculated successfully",
  "bookCounts": {
    "CLEAN_CODE": 1,
    "THE_CLEAN_CODER": 1
  }
}
```

**Example 2: Complex case - 8 books (optimal grouping) = 320 EUR**
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

Expected response:
```json
{
  "price": 320.0,
  "message": "Price calculated successfully",
  "bookCounts": {...}
}
```

**Example 3: Five different books (25% discount) = 187.5 EUR**
```json
[
  "CLEAN_CODE",
  "THE_CLEAN_CODER",
  "CLEAN_ARCHITECTURE",
  "TEST_DRIVEN_DEVELOPMENT",
  "WORKING_EFFECTIVELY_WITH_LEGACY_CODE"
]
```

### Error Handling

If an invalid book name is provided:
```json
["INVALID_BOOK"]
```

Response:
```json
{
  "price": 0.0,
  "message": "Invalid book name: INVALID_BOOK",
  "bookCounts": null
}
```

### Test Coverage

- **Unit Tests** (8 tests): `PriceCalculatorTest`
    - Tests the core business logic for price calculation
    - Covers all discount scenarios (0%, 5%, 10%, 20%, 25%)
    - Tests the optimization algorithm (5+3 → 4+4)

- **Integration Tests** (11 tests): `BookstoreControllerTest`
    - Tests REST API endpoints
    - Validates request/response JSON serialization
    - Tests error handling for invalid inputs

**Total: 19 tests** - All passing ✅

## Author

**Christian SANDJONG MOTIO**  
Java Developer  
Candidate for BNP Paribas Fortis CCE-E Position

## Development Process

This project was developed following strict TDD principles with regular commits showing the Red-Green-Refactor cycle throughout the implementation.