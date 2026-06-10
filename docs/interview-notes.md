# SafeRent Interview Notes

This file keeps short, natural explanations that can be used in interviews.

## Project Overview

SafeRent is a full-stack rental maintenance tracking platform. Tenants can report maintenance issues, property managers can manage and assign requests, technicians can update progress, and managers can view operational dashboard metrics.

## Step 0.1 Explanation

I started the project by creating a clean monorepo foundation with documentation, a roadmap, and architecture notes before writing application code. This helps keep the project organized and makes the build process easy to explain step by step.

## Step 0.2 Explanation

I created the first runnable backend slice using Spring Boot. The backend has a `GET /api/health` endpoint that returns a small JSON response, and I added a MockMvc test to verify the endpoint works without needing to manually start the server.

## Step 0.3 Explanation

I created the first frontend slice using React, TypeScript, Vite, and React Router. The app has a routed home page at `/`, and the UI is structured as small components so future pages like login, requests, and dashboard views can be added cleanly.

## Step 0.4 Explanation

I connected the React frontend to the Spring Boot backend health endpoint. The frontend calls `GET /api/health`, displays the backend service status, and the backend allows the local frontend origin with CORS so the browser request works during development.

## Step 1.1 Explanation

I added PostgreSQL to the project using Docker Compose and configured Spring Boot to connect with environment-based datasource settings. This gives the backend a real relational database foundation before adding entities like users, properties, units, and maintenance requests.

## Step 1.2 Explanation

I created the first database-backed model by adding a `User` JPA entity, a `UserRole` enum, a Spring Data repository, and a Flyway migration for the `users` table. This gives the authentication flow a clean persistence foundation before adding registration and login APIs.

## Step 1.3 Explanation

I added the registration API with DTOs, a service layer, BCrypt password hashing, duplicate-email handling, and basic validation. The endpoint saves users through Spring Data JPA and returns safe user details without exposing password hashes.

## Monorepo Explanation

A monorepo is a single repository that contains multiple parts of an application. For SafeRent, that means the backend, frontend, Docker setup, and documentation will live together while still being separated into clear folders.

## Why Documentation First?

Starting with documentation gives the project direction. The README explains the goal, the roadmap controls scope, and the architecture notes describe how the pieces will connect as the system grows.
