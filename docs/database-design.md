# SafeRent Database Design

This document will track database decisions as SafeRent grows.

## Current Database State

No database exists yet in Step 0.4. The backend and frontend can run without a database because the current features do not need persistent data.

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

We will start with PostgreSQL first because authentication, properties, units, and requests are core transactional data.
