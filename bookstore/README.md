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

## Author

**Christian SANDJONG MOTIO**  
Java Developer  
Candidate for BNP Paribas Fortis CCE-E Position

## Development Process

This project was developed following strict TDD principles with regular commits showing the Red-Green-Refactor cycle throughout the implementation.