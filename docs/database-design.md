# SafeRent Database Design

This document will track database decisions as SafeRent grows.

## Current Database State

PostgreSQL is configured through Docker Compose, and Step 1.2 adds the first application table.

Local defaults:

- Database: `saferent`
- User: `saferent`
- Host port: `5432`
- JDBC URL: `jdbc:postgresql://localhost:5432/saferent`
- Docker volume: `saferent_postgres_data`

Current application tables:

### `users`

Stores application users for authentication and role-based access.

Columns:

- `id`: primary key.
- `full_name`: user's display name.
- `email`: unique login email.
- `password_hash`: hashed password value, never the plain password.
- `role`: user role, stored as text.
- `created_at`: timestamp for when the user row was created.

Flyway migration:

- `V1__create_users_table.sql`

## Planned PostgreSQL Usage

PostgreSQL will store structured transactional data. This means data where relationships and consistency matter.

Planned PostgreSQL tables include:

- `users`
- `properties`
- `units`
- `maintenance_requests`
- `technicians`
- `request_attachments`
- `status_history`
- `comments`

## Planned MongoDB Usage

MongoDB will be introduced later for flexible activity-style data.

Planned MongoDB collections may include:

- `ticket_activity`
- `notification_audit`

## Why Use Both?

PostgreSQL is a strong fit for records with clear relationships, such as a tenant assigned to a unit or a maintenance request connected to a property.

MongoDB is useful for flexible event-style records where the shape may change over time, such as activity feed metadata.

We started with PostgreSQL first because authentication, properties, units, and requests are core transactional data.
