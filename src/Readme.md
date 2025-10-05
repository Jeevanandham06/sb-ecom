# Spring Boot E-Commerce API

## Project Overview
Built a complete Spring Boot REST API for category management with proper architecture, validation, and exception handling.

## Architecture Implemented


## Major Accomplishments

### 1. Centralized API Structure
- RESTful endpoints organized under `/api` base path
- Clear separation between public and admin routes
- Consistent response formats across all endpoints

### 2. Comprehensive Exception Handling
- Global Exception Handler (`@RestControllerAdvice`)
- Custom exceptions: `ResourceNotFoundException`, `APIException`
- Proper HTTP status codes: 200, 201, 400, 404, 500
- User-friendly error messages

### 3. Validation & Business Rules
- Duplicate category name prevention
- Empty categories check with meaningful messages
- Resource existence validation for update/delete operations

### 4. Clean Code Practices
- Service-Repository pattern implementation
- Proper dependency injection with constructor injection
- Consistent logging for debugging
- Meaningful method names and structured code

## Technical Stack
- Spring Boot 3.5.4 with Java 21
- Spring Data JPA for database operations
- H2 In-Memory Database for development
- Maven for dependency management
- RESTful Web Services

## API Endpoints

### Category Management
| Method | Endpoint | Description | Status Codes |
|--------|----------|-------------|-------------|
| GET | `/api/public/categories` | Get all categories | 200, 400 |
| POST | `/api/public/categories` | Create new category | 201, 400 |
| PUT | `/api/public/categories/{id}` | Update category | 200, 404 |
| DELETE | `/api/admin/categories/{id}` | Delete category | 200, 404 |

## Getting Started

1. Clone the repository
2. Run `mvn spring-boot:run`
3. Access API at `http://localhost:8081`
4. H2 Console: `http://localhost:8081/h2-console`

## Response Examples

**Success Response:**
```json
{
    "message": "Category created successfully",
    "categoryId": 1,
    "categoryName": "Electronics"
}