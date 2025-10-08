# E-Commerce Application

## Project: SB-Ecom (Spring Boot E-Commerce)

## Completed Features

### 1. Project Setup & Configuration
- Spring Boot 3.x application setup
- Basic project structure
- Maven/Gradle dependencies configured

### 2. Database Layer
- Entity classes (Product, Category, User, Order, etc.)
- JPA Repositories
- Database relationships configured

### 3. Exception Handling - COMPLETED
- Global Exception Handler with `@RestControllerAdvice`
- Custom exceptions package: `com.ecommerce.sb_ecom.exceptions`
- Proper HTTP status code mapping
- Structured error responses

### 4. DTO Layer - COMPLETED
- Data Transfer Objects implementation
- Separation of persistence and presentation layers
- Mapping between Entities and DTOs
- Request/Response DTOs for all entities

### 5. Pagination - COMPLETED
- Spring Data JPA Pagination support
- Pageable interface implementation
- Custom PageResponse DTO
- API endpoints with pagination parameters (`page`, `size`, `sort`)

## API Endpoints

### Product API
- `GET /api/products` - Get all products (with pagination)
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product

### Category API
- `GET /api/categories` - Get all categories (with pagination)
- `GET /api/categories/{id}` - Get category by ID
- `POST /api/categories` - Create new category
- `PUT /api/categories/{id}` - Update category
- `DELETE /api/categories/{id}` - Delete category

## Technical Implementation

## Project Structure
sb-ecom/
├── src/main/java/com/ecommerce/sb_ecom/
│   ├── controllers/
│   ├── services/
│   ├── repositories/
│   ├── entities/
│   ├── dtos/
│   ├── exceptions/
│   └── config/
├── src/main/resources/
│   ├── application.properties
│   └── data.sql
└── pom.xml

