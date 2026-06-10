# SafeRent Architecture

This document explains the SafeRent system design in beginner-friendly language. It will grow as the project grows.

## Current Architecture

SafeRent currently has two local application parts:

- A minimal Spring Boot backend.
- A minimal React + TypeScript frontend.
- A PostgreSQL database service defined in Docker Compose.

The backend exposes one REST endpoint:

```http
GET /api/health
```

The frontend currently has one route:

```text
/
```

The frontend calls the backend health endpoint and displays the response on the home page.

PostgreSQL is available as a local Docker Compose service. The first application table, `users`, is managed through a Flyway migration.

## Target Architecture

SafeRent will eventually have these main parts:

- Frontend app: the user interface tenants, property managers, technicians, and admins will use. The first frontend slice now exists.
- Backend app: the API layer that receives requests, applies business rules, and talks to databases. The first backend slice now exists.
- PostgreSQL: the relational database for structured transactional data. The local container setup now exists.
- MongoDB: the document database for flexible activity and notification records.
- File storage: local storage first, then S3-compatible storage for uploaded photos and PDFs.
- Docker Compose: the local development environment that runs multiple services together.

## How The Parts Will Communicate

The frontend will call the backend using HTTP. The backend will return JSON for REST APIs and GraphQL responses for dashboard-style queries.

For the current backend health check, the frontend sends an HTTP `GET` request and the backend returns a small JSON object showing that the service is running.

Because the frontend and backend run on different local ports, the backend uses CORS to allow the frontend origin during development.

The backend will store structured records, such as users and maintenance requests, in PostgreSQL. Later, it will store flexible timeline-style records, such as ticket activity events, in MongoDB.

The backend connects to PostgreSQL with a JDBC connection string:

```text
jdbc:postgresql://localhost:5432/saferent
```

For schema management, SafeRent uses Flyway migrations. Hibernate is configured to validate the schema rather than create it automatically.

The registration API follows a controller-service-repository flow:

```text
HTTP request -> AuthController -> AuthService -> UserRepository -> PostgreSQL
```

DTOs keep API input/output separate from the database entity.

## Why This Structure Is Useful

This structure mirrors how many real products are built:

- The frontend focuses on screens and user interaction.
- The backend focuses on rules, security, and data access.
- The databases focus on storing information reliably.
- Docker helps run the project consistently on different machines.

## Monorepo Explanation

A monorepo means one repository contains multiple parts of the product. In SafeRent, the planned monorepo will contain backend code, frontend code, Docker files, and documentation together.

That helps because one project history can show how the entire application was built step by step.
