# SafeRent - Tenant Maintenance and Rental Issue Tracker

SafeRent is a full-stack learning project for tracking rental maintenance issues. Tenants will eventually create maintenance requests, upload issue photos, follow status updates, and communicate with property managers or technicians. Property managers will eventually manage properties, assign technicians, and view dashboard metrics.

This project is being built in small vertical slices. Each slice should be easy to understand, run, test, and explain in an interview.

## Current Status

Step 0.4 is complete: the frontend calls the backend health endpoint and displays the backend status.

- Backend: `GET /api/health`
- Frontend: React + TypeScript home page at `/` with backend health status
- Docker setup starts when the database is introduced.

## Target Tech Stack

Backend:

- Java 17
- Spring Boot 3
- Spring Security
- JWT authentication
- REST APIs
- GraphQL
- PostgreSQL
- MongoDB
- Local file storage first, then S3-compatible storage
- JUnit and Mockito tests

Frontend:

- React or Next.js
- TypeScript
- Tailwind CSS
- Fetch or Axios
- Role-based UI
- Dashboard pages

DevOps:

- Docker
- Docker Compose
- PostgreSQL container
- MongoDB container
- Backend container
- Frontend container

## Planned Repository Structure

```text
saferent-maintenance-platform/
  backend/                 Spring Boot backend
  frontend/                React + TypeScript frontend
  docker/                  planned for Docker-related steps
  docs/
    architecture.md
    api-design.md
    database-design.md
    interview-notes.md
  README.md
  ROADMAP.md
  LEARNING_LOG.md
```

## Documentation

- [ROADMAP.md](ROADMAP.md) tracks completed, current, and upcoming build steps.
- [LEARNING_LOG.md](LEARNING_LOG.md) records what was learned after each step.
- [docs/architecture.md](docs/architecture.md) explains how the system is designed.
- [docs/api-design.md](docs/api-design.md) documents APIs as they are added.
- [docs/database-design.md](docs/database-design.md) documents database choices as they are added.
- [docs/interview-notes.md](docs/interview-notes.md) keeps short interview-ready explanations.

## How To Run

Backend:

From the repository root:

```bash
cd backend
./mvnw spring-boot:run
```

Then open another terminal and run:

```bash
curl http://localhost:8080/api/health
```

Expected response:

```json
{"status":"UP","service":"SafeRent Backend"}
```

Frontend:

From the repository root:

```bash
cd frontend
npm install
npm run dev
```

Then open the local URL printed by Vite, usually:

```text
http://localhost:5173/
```

The frontend uses `VITE_API_BASE_URL` to decide which backend to call. For local development it defaults to:

```text
http://localhost:8080
```

You can copy `frontend/.env.example` to `frontend/.env` if you want to customize it later.

## How To Test

Backend:

From the repository root:

```bash
cd backend
./mvnw test
```

Frontend:

From the repository root:

```bash
cd frontend
npm run build
npm run lint
```

## Learning Approach

SafeRent is built as a monorepo: one repository will contain the backend, frontend, infrastructure, and documentation. This makes it easier to understand how the whole product fits together while still keeping each part in its own folder.
