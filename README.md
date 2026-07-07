# Real-Time Financial Ledger System

## Overview

The Real-Time Financial Ledger System is a Spring Boot based backend application for managing financial accounts and transactions. It supports account management, fund transfers, reporting, audit logging, caching, messaging, authentication, and containerized deployment.

---

# Technologies Used

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- Redis
- Apache Kafka
- JWT Authentication
- Swagger (OpenAPI)
- Docker
- Maven
- JUnit 5
- Mockito

---

# Features

## User Management

- User Registration
- User Login
- JWT Authentication
- Role-Based Authorization

---

## Account Management

- Create Account
- View Account
- Update Account
- Delete Account
- Account Balance

---

## Transaction Module

- Credit Amount
- Debit Amount
- Fund Transfer
- Unique Transaction ID Generation
- Transaction History

---

## Audit Logging

- Records every financial operation
- Uses Spring AOP
- Stores audit details in database

---

## Reporting APIs

- Account Statement
- Credit Transactions
- Debit Transactions
- Transactions Between Dates

---

## Redis Caching

- Cache Account Details
- Cache Account Balance
- Improves Performance
- Reduces Database Calls

---

## Kafka Integration

- Kafka Producer
- Kafka Consumer
- Publishes Transaction Events
- Consumes Transaction Events

---

## Swagger Documentation

- API Documentation
- API Testing from Browser

Swagger URL

```
http://localhost:8080/swagger-ui/index.html
```

---

## Docker Support

Build Image

```
docker build -t financial-ledger .
```

Run Container

```
docker run -p 8080:8080 financial-ledger
```

---

# Project Structure

```
src
 ├── controller
 ├── service
 ├── repository
 ├── entity
 ├── dto
 ├── security
 ├── kafka
 ├── audit
 ├── config
 ├── exception
 └── Radis
```

---

# API Endpoints

## Authentication

POST

```
/api/auth/register
```

POST

```
/api/auth/login
```

---

## Accounts

POST

```
/api/accounts
```

GET

```
/api/accounts
```

GET

```
/api/accounts/{id}
```

PUT

```
/api/accounts/{id}
```

DELETE

```
/api/accounts/{id}
```

---

## Transactions

POST

```
/api/transactions/credit
```

POST

```
/api/transactions/debit
```

POST

```
/api/transactions/transfer
```

---

## Reports

GET

```
/api/reports/account/{id}
```

GET

```
/api/reports/credit
```

GET

```
/api/reports/debit
```

GET

```
/api/reports/date
```

---

# Redis

Default Port

```
6379
```

Check Cache

```
redis-cli
```

```
KEYS *
```

---

# Kafka

Default Port

```
9092
```

Topic

```
ledger-transactions
```

---

# Database

MySQL

Database Name

```
ledgerdb
```

---

# Testing

- JUnit 5
- Mockito
- MockMvc
- Postman Testing

---

# Security

- JWT Authentication
- Password Encryption
- Role-Based Access
- Protected APIs

---

# Author

Mahesh Kapilavai