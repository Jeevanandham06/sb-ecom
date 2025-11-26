SB-Ecom - E-Commerce Platform
🚀 Completed Features
🔐 Authentication & Security
JWT Cookie-based authentication

User Signup/Login/Logout

Role-based access (USER, SELLER, ADMIN)

Password encryption with BCrypt

Spring Security configuration

👥 User Management
User registration with roles

Profile management endpoints

Address management system

Unique email/username constraints

🛍️ Product System
Product CRUD operations

Category management

Product search & filtering

Image upload functionality

Pagination support

🛒 Cart & Order Management ✅ NEW
Shopping Cart functionality

Add/remove products from cart

Cart item management

Quantity updates

Total price calculation

Cart persistence per user

⚙️ Technical Foundation
Global exception handling

DTO layer for data transfer

Database relationships

File service for images

📡 API Endpoints
Authentication
POST /api/auth/signup - Register user

POST /api/auth/signin - Login user

POST /api/auth/signout - Logout user

GET /api/auth/user - Get current user

Public APIs
GET /api/public/categories - List categories

GET /api/public/products - List products

POST /api/public/categories - Create category

Cart APIs ✅ NEW
POST /api/carts/{cartId}/product/{productId} - Add product to cart

DELETE /api/carts/{cartId}/product/{productId} - Remove product from cart

GET /api/carts/{cartId} - Get cart details

PUT /api/carts/{cartId}/items/{itemId} - Update cart item quantity

Admin APIs (Protected)
Product management endpoints

User management endpoints

🗃️ Database Schema
User ↔ Cart (One-to-One)

Cart ↔ CartItem (One-to-Many)

CartItem ↔ Product (Many-to-One)

🛠️ Tech Stack
Backend: Spring Boot 3.x, Spring Security, JPA

Database: H2 (Development)

Authentication: JWT with Cookies

Security: BCrypt, Role-based access

🎯 Current Status & Next Features
✅ Recently Completed
Shopping Cart system with full CRUD operations

Cart-User relationship management

Cart item persistence and calculations

Cart API endpoints

🔜 Next Features
Order Management System (In Progress)

Payment Integration

Order tracking & history

Email notifications

Admin Dashboard

Inventory management

🛒 Cart Module Features
✅ Add products to cart

✅ Remove products from cart

✅ Update quantities

✅ Calculate total prices

✅ User-specific cart persistence

✅ Cart item validation

Status: ✅ Cart System Complete | Working on Order Management
Next: Payment Integration & Order Processing 💳

🔄 Recent Updates
Implemented complete shopping cart functionality

Added cart-item relationship management

Created cart service with business logic

Added cart API endpoints

Fixed repository query issues

Enhanced error handling for cart operations