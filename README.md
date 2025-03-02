# E-Commerce Microservices

## 📌 Overview
This project is a **microservices-based e-commerce system** built using **Spring Boot** and **Docker**. It consists of multiple services that handle different aspects of an online shopping platform, such as **order management, inventory, payments, and notifications**.

## 🏗️ Architecture
The system follows a **microservices architecture** where each service is independently developed, deployed, and scaled. The communication between services is handled via **Kafka** for event-driven processing.

### 🔹 Microservices:
1. **Order Service** - Manages order placement and retrieval.
2. **Inventory Service** - Tracks stock levels and product availability.
3. **Payment Service** - Handles payment processing.
4. **Notification Service** - Sends email and SMS notifications to users.

### 🔹 Tech Stack:
- **Backend**: Java, Spring Boot
- **Database**: PostgreSQL
- **Caching**: Redis
- **Messaging**: Kafka
- **Containerization**: Docker & Docker Compose
- **Security**: JWT Authentication, Spring Security
