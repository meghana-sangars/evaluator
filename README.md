# Expression Evaluator REST API

## Overview

This application provides a REST API to evaluate mathematical expressions passed as strings.
Each request and its result are stored in an in-memory database.
An additional endpoint allows fetching expressions by their result.

## Design Considerations

* **Precision Handling**
  Uses `BigDecimal` instead of floating-point types to ensure accurate arithmetic and avoid rounding errors, which is important in financial contexts.

* **Deterministic Calculations**
  A consistent `MathContext` is used to control precision and rounding behavior.

* **Data Persistence**
  Both the input expression and computed result are stored for traceability and auditability.

* **Separation of Concerns**
  Clear layering (Controller, Service, Repository) to keep business logic isolated and maintainable.

* **Error Handling**
  Centralized exception handling ensures meaningful responses for invalid inputs.


## Prerequisites

* Java 17+ installed
* Maven installed

## How to Run

1. Extract the zip file

2. Navigate to the project directory

```bash
cd evaluator
```

3. Build the project

```bash
mvn clean install
```

4. Run the application

```bash
mvn spring-boot:run
```

5. The application will start at

```
http://localhost:8080
```

## API Endpoints

### 1. Evaluate Expression

POST /api/expressions/evaluate

Request:

```json
{
  "expression": "3+4*6 - 12"
}
```

Response:

```json
{
  "result": 15
}
```

### 2. Get Expressions by Result

GET /api/expressions?result=15

### 3. Get all expressions and their results

GET /api/expressions/

## Testing with curl

### Evaluate Expression

```bash
curl -X POST http://localhost:8080/api/expressions/evaluate \
-H "Content-Type: application/json" \
-d '{"expression":"3+4*6-12"}'
```


### Invalid Expression

```bash
curl -X POST http://localhost:8080/api/expressions/evaluate \
-H "Content-Type: application/json" \
-d '{"expression":"3+*5"}'
```

### Get Expressions by Result

```bash
curl "http://localhost:8080/api/expressions/search?result=15"
```

## Notes

* Uses in-memory H2 database (no setup required)
* Tables are created automatically on startup
* Supports +, -, *, /, parentheses
* Returns HTTP 400 for invalid expressions or division by zero


## Incremental future enhancements

* Logic : Support Unary minus operator
* Support pagination of results
* Extend to further mathematical operators sqrt , log etc

