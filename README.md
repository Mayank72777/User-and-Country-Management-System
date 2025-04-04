# User and Country Management System

This is a Spring Boot-based RESTful web application that allows for managing **Users** and importing **Country** data from an external REST API.
The application uses **MySQL** as the database, **JWT** for basic authentication, **Docker** for containerization, and provides **Swagger UI** for interactive API documentation.

## Features Implemented

-  User Management
  - Register new users
  - Login with JWT-based authentication
-  Country Import
  - Fetch and store countries from [REST Countries API](https://restcountries.com/v3.1/all)
-  JWT Authentication
  - Protect endpoints using JWT
-  Swagger UI for API documentation
-  Unit Testing (Basic)
-  Dockerized Setup** using `Dockerfile` and `docker-compose.yml`
-  Actuator Health Endpoint
-  Follows basic **Spring Boot best practices

---

##  Tech Stack

- Java 17
- Spring Boot
- Spring Security + JWT
- Spring Data JPA + Hibernate
- MySQL
- Docker & Docker Compose
- Swagger (springdoc-openapi)
- Actuator (Health Check)

---

##  Run with Docker

### Prerequisites

- Docker & Docker Compose installed

### Steps

## Clone the Repository
```bash

- git clone https://github.com/Mayank72777/User-and-Country-Management-System.git
- cd User-and-Country-Management-System

```
# Build and run containers
```bash
  - docker-compose up --build
```

## This will start:

  - MySQL on port 3306
  - Spring Boot app on port 8080

## Swagger & Health Check

  - Swagger UI: http://localhost:8080/swagger-ui.html

  - Health Check: http://localhost:8080/actuator/health
