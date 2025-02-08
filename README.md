# E-commerce Simulator Project

## Overview

This is an e-commerce simulator project built using a microservices architecture. The project consists of 9 microservices: user, order, payment, core (includes product, category, cart), gateway, discovery, config, and auth. The stack includes Spring Boot, Kafka, Spring Security, Spring Data JPA, Spring Cloud, Eureka, JWT, and Swagger.

## Microservices

1. **User Service**
    - Manages user-related operations such as user registration, authentication, and profile management.
    - Technologies: Spring Boot, Spring Security, JWT, Spring Data JPA, Swagger.

2. **Order Service**
    - Handles order management including creation, updating, and retrieval of orders.
    - Technologies: Spring Boot, Spring Data JPA, Swagger, Kafka.

3. **Payment Service**
    - Manages payment processing and payment-related operations.
    - Technologies: Spring Boot, Spring Data JPA, Kafka, Swagger.

4. **Core Service**
    - Includes product, category, and cart management functionalities.
    - Technologies: Spring Boot, Spring Data JPA, Swagger.

5. **Gateway Service**
    - Acts as an API gateway to route requests to the appropriate microservices.
    - Technologies: Spring Cloud Gateway.

6. **Discovery Service**
    - Provides service discovery using Eureka.
    - Technologies: Spring Cloud Netflix Eureka.

7. **Config Service**
    - Manages externalized configuration using Spring Cloud Config.
    - Technologies: Spring Cloud Config, Spring Cloud Netflix Eureka.

8. **Auth Service**
    - Handles authentication and authorization, including login, registration, and token verification.
    - Technologies: Spring Boot, Spring Security, JWT, Spring Data JPA, Swagger.

9. **Cart Service**
    - Manages shopping cart operations including adding, updating, and removing items from the cart.
    - Technologies: Spring Boot, Spring Data JPA, Swagger.

## Architecture

The project follows a microservices architecture where each service is responsible for a specific domain. The services communicate with each other using REST APIs and Kafka for asynchronous messaging.

## Technologies

- **Spring Boot**: For building microservices.
- **Spring Security**: For securing the services.
- **JWT (JSON Web Token)**: For authentication and authorization.
- **Spring Data JPA**: For data persistence.
- **Spring Cloud**: For building cloud-native applications.
- **Eureka**: For service discovery.
- **Kafka**: For asynchronous messaging.
- **Swagger**: For API documentation.

## Getting Started

### Prerequisites

- Java 21
- Maven 3.6+
- Docker (optional, for containerized deployment)
- Kafka (if not using Docker)

### Running the Services

   ```sh
   
   git clone https://github.com/artemelyashevich/ecomerce-api.git
   docker-compose up --build
   ```