# Fast Food Restaurant Management System

## Overview
This project is a **Fast Food Restaurant Management System** built using **Spring Boot** for the backend and **HTML/CSS/JavaScript** for the frontend. It provides a platform for managing food categories, menu items, user roles, and shopping baskets. The system supports two types of users: **Admin** and **Regular Users**. Admins can manage categories, foods, and user roles, while regular users can browse the menu, add items to their basket, and place orders.

---

## Features

### Admin Features
1. **Category Management**:
   - Create, update, and delete food categories.
   - View all categories and their associated foods.
2. **Food Management**:
   - Add, update, and delete food items.
   - Upload food images.
3. **User Management**:
   - View all users.
   - Assign or remove roles (e.g., ADMIN, USER) for users.
4. **Dashboard**:
   - Access a centralized dashboard for managing the restaurant.

### User Features
1. **Browse Menu**:
   - View food items by category.
   - See food details (name, price, description, and image).
2. **Shopping Basket**:
   - Add or remove food items from the basket.
   - Adjust quantities of items in the basket.
   - Checkout and clear the basket.
3. **User Profile**:
   - View user details (email, roles).

### Authentication and Authorization
1. **User Registration**:
   - Register with email, password, and full name.
   - Email verification with a 4-digit code.
2. **User Login**:
   - Login with email and password.
   - JWT-based authentication for secure access.
3. **Role-Based Access Control**:
   - Admins have access to all features.
   - Regular users can only browse the menu and manage their basket.

---

## Technologies Used

### Backend
- **Spring Boot**: Core framework for building the REST API.
- **Spring Security**: For authentication and authorization.
- **Spring Data JPA**: For database interactions.
- **PostgreSQL**: Relational database for storing data.
- **JWT (JSON Web Tokens)**: For secure user authentication.
- **JavaMailSender**: For sending email verification codes.
- **Lombok**: For reducing boilerplate code.
- **Multipart File Upload**: For handling food image uploads.

### Frontend
- **HTML/CSS**: For structuring and styling the web pages.
- **JavaScript**: For dynamic interactions and API calls.
- **Bootstrap**: For responsive and modern UI components.
- **Fetch API**: For making HTTP requests to the backend.

### Tools
- **PostgreSQL**: Database management.
- **Postman**: For testing API endpoints.
- **Maven**: For dependency management.
- **Git**: For version control.

---

## Project Structure

### Backend
- **Controllers**: Handle HTTP requests and responses.
- **Services**: Contain business logic.
- **Repositories**: Interact with the database.
- **Entities**: Represent database tables (e.g., User, Food, Category, Basket).
- **Security**: Configuration for JWT authentication and role-based access.
- **Payloads**: Data transfer objects (DTOs) for API requests and responses.

### Frontend
- **HTML Pages**:
  - `index.html`: Main landing page.
  - `login.html`: User login page.
  - `register.html`: User registration page.
  - `adminMenuPage.html`: Admin dashboard.
  - `userMenuPage.html`: User menu page.
  - `categoryFoods.html`: Page to view foods in a category.
  - `verify-email.html`: Email verification page.
- **JavaScript Files**:
  - `adminLoginData.js`: Handles admin login and role checks.
  - `userLoginData.js`: Handles user login and role checks.
  - `logout.js`: Handles user logout.

---

## Setup Instructions

### Prerequisites
1. **Java Development Kit (JDK)**: Version 17 or higher.
2. **PostgreSQL**: Installed and running.
3. **Maven**: For building the project.
4. **Node.js**: Optional, for running a local server for the frontend.

### Backend Setup
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/JohnUfo/fast-food-rest.git
   cd fast-food-restaurant-management
   ```
2. **Configure Database**:
   - Update the `application.properties` file with your PostgreSQL credentials:
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/fast_food
     spring.datasource.username=your-username
     spring.datasource.password=your-password
     ```
3. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```
   The backend will start at `http://localhost:8080`.

### Frontend Setup
1. **Serve the Frontend**:
   - Use a local server (e.g., Live Server in VS Code) to serve the HTML files.
   - Open `index.html` to access the application.

2. **Configure API Endpoint**:
   - Ensure the frontend JavaScript files point to the correct backend URL (e.g., `http://localhost:8080`).

---

## API Endpoints

### Authentication
- **POST `/register`**: Register a new user.
- **POST `/login`**: Authenticate a user and return a JWT token.
- **POST `/verify-email`**: Verify user email with a code.

### Categories
- **GET `/categories`**: Get all categories.
- **POST `/categories`**: Create a new category (Admin only).
- **PUT `/categories/{id}`**: Update a category (Admin only).
- **DELETE `/categories/{id}`**: Delete a category (Admin only).

### Foods
- **GET `/categories/{categoryId}/foods`**: Get all foods in a category.
- **POST `/categories/{categoryId}/foods`**: Add a new food (Admin only).
- **PUT `/categories/foods/{foodId}`**: Update a food (Admin only).
- **DELETE `/categories/foods/{foodId}`**: Delete a food (Admin only).

### Basket
- **GET `/basket`**: Get the user's basket.
- **POST `/basket/add`**: Add a food item to the basket.
- **DELETE `/basket/{foodId}`**: Remove a food item from the basket.
- **DELETE `/basket/checkout`**: Clear the basket (checkout).

### Users
- **GET `/api/users`**: Get all users (Admin only).
- **PUT `/api/users/{userId}/roles`**: Update user roles (Admin only).
- **DELETE `/api/users/{userId}/roles`**: Remove roles from a user (Admin only).

---
