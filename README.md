# StudentManagementApp

A Spring Boot 3 application for managing student records with REST APIs, JPA persistence, validation, and MySQL integration. Built with production readiness in mind, following patterns from Spring Boot, Netflix OSS, and Google engineering style guides.

---

## Table of Contents
1. [Overview](#overview)
2. [Architecture](#architecture)
3. [Features](#features)
4. [Technology Stack](#technology-stack)
5. [Database Schema](#database-schema)
6. [Setup & Installation](#setup--installation)
7. [Configuration](#configuration)
8. [Running the Application](#running-the-application)
9. [API Documentation](#api-documentation)
10. [Sample Payloads](#sample-payloads)
11. [Error Handling](#error-handling)
12. [Testing](#testing)
13. [Future Roadmap](#future-roadmap)

---

## Overview
StudentManagementApp provides CRUD APIs for managing students, including filtering by branch and year of passing, pagination, auditing, and soft delete support. It uses DTOs, service-layer abstractions, and a mapper utility to keep the domain clean and follows best practices for exception handling and validation.

## Architecture
- **Controller Layer** (`controller.StudentController`): Exposes REST endpoints, handles validation, and orchestrates service calls.
- **Service Layer** (`service.StudentService` & `service.impl.StudentServiceImpl`): Encapsulates business logic, filtering, pagination, and soft delete behavior.
- **Repository Layer** (`repository.StudentRepository`): Spring Data JPA repository with derived query methods for branch and YOP filters.
- **Domain Layer** (`entity.Student`): Represents the JPA entity with auditing, validation annotations, and Lombok.
- **DTO & Mapper** (`dto.*`, `mapper.StudentMapper`): Request/response separation and conversion utilities.
- **Exception Handling** (`exception.GlobalExceptionHandler`, `ResourceNotFoundException`): Centralized responses for validation and not-found errors.

## Features
- RESTful CRUD APIs with DTOs
- Filter by branch, year of passing, or both
- Pagination & sorting support via Spring Data
- Soft delete (toggle `active` flag) instead of hard delete
- JPA auditing for `createdAt` & `updatedAt`
- Jakarta Bean Validation and global error handling
- Spring Boot DevTools & Lombok for efficient development

## Technology Stack
- Java 17
- Spring Boot 3.4.0
- Spring Web, Spring Data JPA, Spring Validation
- MySQL (via `mysql-connector-j`)
- Lombok, DevTools, Maven Wrapper
- Maven for build automation

## Database Schema
```sql
CREATE TABLE students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    phone VARCHAR(255) NOT NULL,
    branch VARCHAR(255) NOT NULL,
    yop INT NOT NULL,
    active BIT NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL
);
```

## Setup & Installation
1. **Clone** repository or copy project into desired location.
2. Ensure **Java 17** and **Maven** (or Maven Wrapper) are available.
3. Start a MySQL instance and create database `studentmanagementdb` (auto-created via JDBC URL when permissions allow).
4. Configure environment variables (see [Configuration](#configuration)).

```bash
# inside project root
./mvnw clean install
```

## Configuration
Primary configuration lives in `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/studentmanagementdb?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
    username: root
    password: system123
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
server:
  port: 8080
management:
  endpoints:
    web:
      exposure:
        include: "*"
```

Override values via environment variables or `application.yml` profiles for production (e.g. `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`).

## Running the Application
```bash
./mvnw spring-boot:run
# or packaged jar
./mvnw clean package
java -jar target/student-management-app-0.0.1-SNAPSHOT.jar
```

## API Documentation
Base URL: `http://localhost:8080/api/students`

### 1. Create Student
- **POST /**
- **Request Body**: `StudentRequestDTO`
- **Response**: `201 Created`, returns `StudentResponseDTO`

### 2. Get Student by ID
- **GET /{id}**
- **Response**: `200 OK` with `StudentResponseDTO`

### 3. Get Students
- **GET /**
- Query Params:
  - `branch` (optional)
  - `yop` (optional)
  - `page`, `size` (pagination)
- **Response**: `200 OK` `Page<StudentResponseDTO>`

### 4. Update Student (Full)
- **PUT /{id}**
- **Body**: `StudentRequestDTO`
- **Response**: `200 OK`

### 5. Update Student (Partial)
- **PATCH /{id}**
- **Body**: subset of fields
- **Response**: `200 OK`

### 6. Soft Delete Student
- **DELETE /{id}**
- **Response**: `200 OK`, `{ "message": "Student deleted successfully" }`

## Sample Payloads
```json
POST /api/students
{
  "fullName": "Aarav Sharma",
  "email": "aarav.sharma@gmail.com",
  "phone": "9876543210",
  "branch": "CSE",
  "yop": 2023,
  "active": true
}
```

```json
PUT /api/students/1
{
  "fullName": "Aarav Vikram",
  "email": "aarav.vikram@gmail.com",
  "phone": "9876501234",
  "branch": "ECE",
  "yop": 2024,
  "active": true
}
```

```json
PATCH /api/students/2
{
  "phone": "9123400000",
  "branch": "IT"
}
```

## Error Handling
### Resource Not Found
```json
HTTP/1.1 404 Not Found
{
  "message": "Student not found with id: 999",
  "timestamp": "2025-11-18T15:10:00Z"
}
```

### Validation Error
```json
HTTP/1.1 400 Bad Request
{
  "message": "Validation failed",
  "timestamp": "2025-11-18T15:12:00Z",
  "errors": {
    "fullName": "must not be blank",
    "yop": "must not be null"
  }
}
```

## Testing
Manual verification performed via curl/Postman for filtering, pagination, and CRUD operations. Future work includes JUnit + MockMvc tests and Testcontainers for integration testing.

## Future Roadmap
- Role-based security with Spring Security + JWT
- OpenAPI/Swagger documentation
- Docker Compose for MySQL bundling
- Flyway/Liquibase migrations
- Frontend (React/Angular) integration
- Comprehensive unit/integration tests
- CI/CD pipeline (GitHub Actions)

---

For questions or contributions, open an issue or submit a pull request.
