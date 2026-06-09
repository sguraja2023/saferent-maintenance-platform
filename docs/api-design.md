# SafeRent API Design

This document will track REST and GraphQL APIs as they are added.

## Current REST APIs

Step 1.2 did not add new API endpoints. It added the `User` entity, `users` table, and repository foundation for the upcoming registration API.

### Health Check

```http
GET /api/health
```

Expected response:

```json
{
  "status": "UP",
  "service": "SafeRent Backend"
}
```

Purpose: confirms that the backend application is running and can return JSON over HTTP.

Frontend usage:

- `frontend/src/api/health.ts` calls this endpoint with `fetch`.
- `VITE_API_BASE_URL` controls the backend base URL.
- The local default backend URL is `http://localhost:8080`.

Local CORS:

- The backend allows `http://localhost:5173` and `http://127.0.0.1:5173` for this endpoint so the Vite dev server can call it from the browser.

## API Design Principles

SafeRent APIs will follow these principles:

- Use REST for resource-based workflows, such as users, properties, units, and maintenance requests.
- Use GraphQL later for dashboard queries where the frontend needs a custom shape of data.
- Use DTOs so API request and response shapes are separate from database entities.
- Never return password hashes in API responses.
- Add validation gradually as each feature becomes real.
