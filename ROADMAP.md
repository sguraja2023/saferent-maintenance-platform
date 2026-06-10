# SafeRent Roadmap

This roadmap keeps the project small, teachable, and interview-friendly. We only move to the next step after the current step works.

## Completed

### Step 0.1 - Repository structure and learning foundation

Created the top-level documentation files and the `docs/` folder. No backend or frontend code was added.

### Step 0.2 - Backend Spring Boot skeleton

Created the Spring Boot backend skeleton and added:

- `GET /api/health`
- JSON response:

```json
{
  "status": "UP",
  "service": "SafeRent Backend"
}
```

### Step 0.3 - Frontend skeleton

Created the React + TypeScript frontend skeleton and added:

- Vite frontend app in `frontend/`
- React Router setup
- Home page at `/`
- SafeRent browser title and favicon

### Step 0.4 - Connect frontend to backend health endpoint

Connected the React frontend to the Spring Boot backend health endpoint and added:

- Frontend health API client
- Backend health status card
- CORS allowance for the local Vite dev server
- Backend CORS test

### Step 1.1 - Add PostgreSQL using Docker Compose

Added PostgreSQL as the first database service and connected the backend to it:

- Root `compose.yaml` with a `postgres` service
- PostgreSQL database, user, password, port, health check, and named volume
- Backend datasource configuration using environment-variable-friendly defaults
- JPA and PostgreSQL dependencies
- Database connection test using `JdbcTemplate`

### Step 1.2 - Create User entity/table

Created the first database-backed model:

- `User` JPA entity
- `UserRole` enum
- `UserRepository`
- Flyway migration for the `users` table
- Repository test for saving and finding a user by email

### Step 1.3 - Create registration API

Created the first auth endpoint:

- `POST /api/auth/register`
- Request/response DTOs
- `AuthService` registration flow
- BCrypt password hashing
- Duplicate email handling
- Basic input validation
- Controller and service tests

## Current

### Step 1.4 - Create login API

Next step after confirmation: create `POST /api/auth/login` and return a JWT token.

## Upcoming

### Phase 1 - Authentication and role foundation

- Step 1.4 - Create login API with JWT.
- Step 1.5 - Secure APIs with Spring Security.
- Step 1.6 - Build frontend register page.
- Step 1.7 - Build frontend login page.

### Phase 2 - Property and unit management

- Step 2.1 - Create Property entity and APIs.
- Step 2.2 - Create Unit entity and APIs.
- Step 2.3 - Assign tenant to unit.
- Step 2.4 - Build property and unit frontend pages.

### Phase 3 - Maintenance request core

- Step 3.1 - Create MaintenanceRequest entity.
- Step 3.2 - Create maintenance request API.
- Step 3.3 - Get request by ID with authorization rules.
- Step 3.4 - List maintenance requests with filters.
- Step 3.5 - Build request frontend pages.

### Phase 4 - Status workflow and history

- Step 4.1 - Create StatusHistory entity.
- Step 4.2 - Create status update API.
- Step 4.3 - Show status history on request detail page.

### Phase 5 - Comments

- Step 5.1 - Create comments using PostgreSQL first.
- Step 5.2 - Add comments section on request detail page.
- Step 5.3 - Discuss whether comments should stay in PostgreSQL or move to MongoDB.

### Phase 6 - Technician assignment

- Step 6.1 - Add technician assignment API.
- Step 6.2 - Create technician list page.

### Phase 7 - MongoDB activity feed

- Step 7.1 - Add MongoDB to Docker Compose.
- Step 7.2 - Create TicketActivity document.
- Step 7.3 - Write activity events.
- Step 7.4 - Show activity feed on request detail page.

### Phase 8 - File uploads

- Step 8.1 - Create RequestAttachment table.
- Step 8.2 - Add local file upload.
- Step 8.3 - Display uploaded attachments.
- Step 8.4 - Refactor storage behind FileStorageService.
- Step 8.5 - Add S3-compatible storage implementation.

### Phase 9 - GraphQL dashboard

- Step 9.1 - Add GraphQL dependency and configuration.
- Step 9.2 - Create managerDashboard query.
- Step 9.3 - Build dashboard frontend using GraphQL.

### Phase 10 - Notifications

- Step 10.1 - Create notification simulation.
- Step 10.2 - Trigger notifications for major ticket events.

### Phase 11 - Testing

- Step 11.1 - Add unit tests for service layer.
- Step 11.2 - Add integration test for create maintenance request.
- Step 11.3 - Add test for role-based access.
- Step 11.4 - Add GraphQL dashboard test.

### Phase 12 - Docker and final polish

- Step 12.1 - Dockerize backend.
- Step 12.2 - Dockerize frontend.
- Step 12.3 - Create full Docker Compose environment.
- Step 12.4 - Update README with full local setup.
- Step 12.5 - Add GitHub-ready documentation.
- Step 12.6 - Create LinkedIn post draft.
