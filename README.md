# Monorepo

This is a monorepo containing an Angular frontend and Spring Boot backend application.

## Project Structure

```
monorepo/
├── apps/
│   ├── frontend/     # Angular frontend application
│   └── backend/      # Spring Boot backend application
├── package.json      # Root package.json
└── README.md         # This file
```

## Prerequisites

- Node.js (v18 or later)
- Java 17 or later
- Maven (included in the backend project as Maven Wrapper)

## Getting Started

1. Install dependencies:

   ```bash
   npm install
   ```

2. Start development servers:

   ```bash
   npm run dev
   ```

   This will start both the Angular frontend (http://localhost:4200) and Spring Boot backend (http://localhost:8080)

3. Build all applications:
   ```bash
   npm run build
   ```

## Available Scripts

- `npm run dev` - Start both frontend and backend in development mode
- `npm run frontend:dev` - Start only the Angular frontend
- `npm run backend:dev` - Start only the Spring Boot backend
- `npm run build` - Build both applications
- `npm run test` - Run tests for both applications
- `npm run frontend:test` - Run only frontend tests
- `npm run backend:test` - Run only backend tests

## Frontend (Angular)

The frontend application is located in `apps/frontend/`. It's a standard Angular application that can be developed using Angular CLI commands.

## Backend (Spring Boot)

The backend application is located in `apps/backend/`. It's a Spring Boot application that can be developed using Maven commands.
