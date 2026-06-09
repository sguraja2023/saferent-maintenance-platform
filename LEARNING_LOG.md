# SafeRent Learning Log

This file records what was built, what was learned, key files, commands, and interview explanations after each step.

## Step 0.1 - Repository structure and learning foundation

### What was built

Created the initial project documentation structure:

- `README.md`
- `ROADMAP.md`
- `LEARNING_LOG.md`
- `docs/architecture.md`
- `docs/api-design.md`
- `docs/database-design.md`
- `docs/interview-notes.md`

No backend or frontend code was created in this step.

### What concept I learned

A monorepo is one repository that holds multiple parts of a system, such as backend code, frontend code, infrastructure files, and documentation. It helps a developer see the whole product in one place while still keeping each part organized in its own folder.

### Key files

- `README.md` explains the product, target stack, and project structure.
- `ROADMAP.md` controls the build order.
- `LEARNING_LOG.md` tracks what I learn after each step.
- `docs/architecture.md` explains the system design.
- `docs/api-design.md` will document REST and GraphQL APIs as they are created.
- `docs/database-design.md` will document PostgreSQL and MongoDB decisions.
- `docs/interview-notes.md` stores short explanations for interviews.

### Commands used

```bash
mkdir -p docs
```

### Interview explanation

I started SafeRent by setting up a monorepo-style foundation with project documentation, a roadmap, and architecture notes. This gives the project a professional structure before adding code, and it makes it easier to explain how the backend, frontend, database, and infrastructure will fit together as the app grows.

### Practice before the next step

- Explain what a monorepo is in your own words.
- Open each documentation file and describe its purpose.
- Look at the roadmap and identify what will be runnable in Step 0.2.

## Step 0.2 - Backend Spring Boot skeleton

### What was built

Created a minimal Spring Boot backend in `backend/` with one endpoint:

```http
GET /api/health
```

The endpoint returns:

```json
{
  "status": "UP",
  "service": "SafeRent Backend"
}
```

### What concept I learned

Spring Boot is a Java framework that helps create production-style web applications quickly. A controller is the class that receives HTTP requests. An endpoint is a specific URL and HTTP method combination, such as `GET /api/health`. A JSON response is structured data returned by the backend that other programs, including a frontend app, can read.

### Key files

- `backend/pom.xml` defines the backend dependencies and Java version.
- `backend/mvnw` lets the project run Maven commands without requiring Maven to be installed globally.
- `backend/src/main/java/com/saferent/SafeRentBackendApplication.java` starts the Spring Boot app.
- `backend/src/main/java/com/saferent/health/HealthController.java` defines the health endpoint.
- `backend/src/test/java/com/saferent/health/HealthControllerTest.java` verifies the health endpoint response.

### Commands used

```bash
cd backend
./mvnw test
./mvnw spring-boot:run
curl http://localhost:8080/api/health
```

### Interview explanation

I created the first runnable backend slice for SafeRent using Spring Boot. It exposes a simple `GET /api/health` endpoint that returns JSON, and I added a focused controller test with MockMvc to verify the response shape and status code.

### Practice before the next step

- Explain what happens when a browser or curl calls `/api/health`.
- Find the controller method that handles the request.
- Run the test and identify which assertions check the JSON fields.

## Step 0.3 - Frontend React and TypeScript skeleton

### What was built

Created a minimal React frontend in `frontend/` with:

- Vite as the frontend build tool.
- React and TypeScript.
- React Router with a route for `/`.
- A SafeRent home page showing starter maintenance dashboard sections.
- A SafeRent browser title and favicon.

The frontend does not call the backend yet.

### What concept I learned

A React component is a reusable function that returns UI. TypeScript adds types to JavaScript so mistakes can be caught earlier. Frontend routing lets the browser show different UI for different paths, such as `/`, `/login`, or `/requests`, without needing a full page reload for each view.

### Key files

- `frontend/package.json` defines frontend dependencies and scripts.
- `frontend/src/main.tsx` starts the React app and wraps it with `BrowserRouter`.
- `frontend/src/App.tsx` defines the route table.
- `frontend/src/pages/HomePage.tsx` defines the first page component.
- `frontend/src/App.css` contains page-specific styling.
- `frontend/src/index.css` contains global CSS reset and font defaults.

### Commands used

```bash
npm create vite@latest frontend -- --template react-ts
cd frontend
npm install --no-audit --no-fund
npm install react-router-dom --no-audit --no-fund
npm run build
npm run lint
npm run dev
```

The first install hit a local disk-space error, so the npm cache was cleaned and the install was retried:

```bash
npm cache clean --force
```

### Interview explanation

I added the first frontend slice using React, TypeScript, Vite, and React Router. The app now has a routed home page at `/`, with typed data for starter dashboard metrics and a clean app shell that will later connect to the backend.

### Practice before the next step

- Find where `BrowserRouter` is configured.
- Explain how `App.tsx` maps `/` to `HomePage`.
- Identify the TypeScript type used for the dashboard metric objects.

## Step 0.4 - Connect frontend to backend health endpoint

### What was built

Connected the React frontend to the Spring Boot backend health endpoint:

- The frontend calls `GET /api/health`.
- The home page displays loading, connected, and error states.
- The backend allows local Vite origins with CORS.
- A backend test verifies the CORS header.

### What concept I learned

Frontend and backend apps communicate over HTTP. The frontend sends a request with `fetch`, the backend returns JSON, and the frontend turns that response into UI state. Because the frontend and backend run on different local ports, the browser treats them as different origins, so the backend must explicitly allow the frontend origin with CORS.

### Key files

- `frontend/src/api/health.ts` contains the typed API call.
- `frontend/src/components/BackendHealthCard.tsx` renders loading, success, and error UI states.
- `frontend/src/pages/HomePage.tsx` places the health card on the home page.
- `frontend/.env.example` documents the local backend URL.
- `backend/src/main/java/com/saferent/health/HealthController.java` allows local frontend origins.
- `backend/src/test/java/com/saferent/health/HealthControllerTest.java` verifies the JSON response and CORS behavior.

### Commands used

```bash
cd backend
./mvnw test
./mvnw spring-boot:run
```

```bash
cd frontend
npm run build
npm run lint
npm run dev
```

### Interview explanation

I connected the React frontend to the Spring Boot backend by adding a typed health API client and rendering the backend status on the home page. I also added CORS support for the local frontend dev server and tested that the backend allows the frontend origin.

### Practice before the next step

- Explain why `localhost:5173` and `localhost:8080` are different origins.
- Find the `fetch` call and describe what happens when it succeeds.
- Stop the backend and refresh the frontend to see the error state.

## Step 1.1 - PostgreSQL with Docker Compose

### What was built

Added PostgreSQL as the first database service:

- Root `compose.yaml` runs a `postgres` container.
- PostgreSQL uses the local database name `saferent`.
- Backend now has JPA and PostgreSQL driver dependencies.
- Backend datasource settings are read from environment variables with local development defaults.
- Added a database connection test that runs `select version()` against PostgreSQL.

No application tables were created yet. The first table comes in Step 1.2.

### What concept I learned

PostgreSQL is a relational database used for structured data like users, properties, units, and maintenance requests. Docker Compose lets us define local services in one file so we can start PostgreSQL consistently. A database connection string tells the backend where the database is, what database name to use, and how to authenticate.

### Key files

- `compose.yaml` defines the PostgreSQL container.
- `.env.example` documents local database environment variables.
- `docker/README.md` explains the local Docker setup.
- `backend/pom.xml` adds Spring Data JPA and the PostgreSQL driver.
- `backend/src/main/resources/application.properties` configures the datasource.
- `backend/src/test/java/com/saferent/database/DatabaseConnectionTest.java` verifies backend-to-database connectivity.

### Commands used

```bash
docker compose up -d postgres
docker compose ps
```

```bash
cd backend
./mvnw test
```

During implementation, Docker Desktop did not become responsive in this environment, so the Compose file was validated and the backend was packaged without running tests:

```bash
docker compose config
cd backend
./mvnw -DskipTests package
```

### Interview explanation

I added PostgreSQL to SafeRent using Docker Compose and configured the Spring Boot backend to connect through environment-based datasource settings. I also added a database connection test with `JdbcTemplate` so the project can verify that the backend is actually talking to PostgreSQL.

### Practice before the next step

- Explain what a connection string is.
- Start PostgreSQL with Docker Compose and inspect `docker compose ps`.
- Run the backend tests after PostgreSQL is running.

## Step 1.2 - User entity and users table

### What was built

Created the first database-backed domain model:

- `User` JPA entity.
- `UserRole` enum with `TENANT`, `PROPERTY_MANAGER`, `TECHNICIAN`, and `ADMIN`.
- `UserRepository` for database access.
- Flyway migration that creates the `users` table.
- Repository test that saves a user and finds it by email.

### What concept I learned

An entity is a Java class that represents a database table. A table stores rows of data, and each column stores one piece of a row, such as `email` or `created_at`. A repository is a Spring Data interface that gives us common database operations without writing SQL for every query. A migration is a versioned SQL file that changes the database schema in a controlled way.

### Key files

- `backend/src/main/java/com/saferent/user/User.java` maps Java fields to the `users` table.
- `backend/src/main/java/com/saferent/user/UserRole.java` defines allowed user roles.
- `backend/src/main/java/com/saferent/user/UserRepository.java` provides database access.
- `backend/src/main/resources/db/migration/V1__create_users_table.sql` creates the table.
- `backend/src/test/java/com/saferent/user/UserRepositoryTest.java` verifies repository behavior.
- `backend/pom.xml` adds Flyway dependencies.
- `backend/src/main/resources/application.properties` tells Hibernate to validate the schema created by Flyway.

### Commands used

```bash
cd backend
./mvnw -DskipTests package
```

Full test command after PostgreSQL is running:

```bash
docker compose up -d postgres
cd backend
./mvnw test
```

### Interview explanation

I created the first database-backed model for SafeRent by adding a `User` JPA entity, a `UserRole` enum, a Spring Data repository, and a Flyway migration for the `users` table. Flyway owns the schema change, while Hibernate validates that the Java entity matches the database table.

### Practice before the next step

- Explain the difference between a Java entity and a database table.
- Open the migration file and match each SQL column to a Java field.
- Run the repository test after PostgreSQL is running.
