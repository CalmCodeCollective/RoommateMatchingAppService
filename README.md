# Roommate Matching App Service

Backend service for helping people create roommate profiles and eventually discover compatible roommates based on living preferences.

This repository is the starting point for taking the service from a basic account CRUD API to a complete roommate-matching product. The current implementation is intentionally small: it contains account and profile resources, HATEOAS responses, an in-memory H2 database, and API documentation support.

## Project goal

The service should provide a reliable backend for:

1. Creating and managing user accounts.
2. Creating a roommate profile associated with an account.
3. Capturing compatibility preferences such as cleanliness, sleep schedule, smoking, drinking, pets, and biography.
4. Finding and ranking compatible roommate profiles.
5. Supporting a client application through a documented, validated, and secure REST API.

The matching algorithm is not implemented yet. The current code is the foundation on which that feature will be built.

## Current state

### Implemented

- Spring Boot application using Java 17.
- Spring Data JPA repositories for `Account` and `Profile`.
- Account CRUD endpoints under `/accounts`.
- Profile CRUD endpoints under `/profiles` and `/profiles/{id}`.
- Spring HATEOAS model assemblers for account and profile responses.
- Custom not-found exceptions for accounts and profiles.
- H2 runtime dependency for local persistence.
- OpenAPI/Swagger UI dependency.
- Startup seed data for two sample accounts.

### Incomplete or known issues

- Profile validation and preference constraints are still planned.
- Account/profile ownership and authorization are not yet enforced.
- No matching algorithm, search endpoint, authentication, authorization, migrations, production database, or frontend integration exists yet.
- Automated test coverage is currently limited to a context-load test.
- The local Maven environment must use Java 17; Java 8 will not compile this project.

## High-level design (HLD)

The intended system is a layered REST service:

```text
Client application
        |
        v
REST controllers + OpenAPI contract
        |
        v
Application services
  - account management
  - profile management
  - roommate matching
        |
        v
Domain model and matching rules
        |
        v
Spring Data repositories
        |
        v
Relational database
```

### Main components

- **API layer:** Controllers expose versioned HTTP endpoints, map requests to DTOs, and return consistent response objects.
- **Application layer:** Services coordinate use cases and transactions. Controllers should not contain business rules.
- **Domain layer:** Account, profile, preference, and match concepts plus the rules used to calculate compatibility.
- **Persistence layer:** JPA entities and repositories. A production database should replace the current H2 setup.
- **Cross-cutting concerns:** Validation, exception handling, logging, security, API documentation, and observability.

### Proposed future deployment

For the first production-ready version, the service can remain a single Spring Boot application backed by PostgreSQL. The application should be stateless so it can later run behind a load balancer. Authentication can be provided with JWT/OAuth2, and database schema changes should be managed with Flyway or Liquibase.

## Low-level design (LLD)

### Core entities

```text
Account 1 ---- 0..1 Profile
```

**Account**

- `id`
- `handle`
- `firstName`
- `lastName`
- future authentication/status fields

**Profile**

- `id`
- `accountId`
- `bio`
- `cleanlinessPreference`
- `sleepSchedule`
- `smokingPreference`
- `drinkingPreference`
- `petPreference`
- future location, budget, move-in, and household constraints

**Match** (planned)

- `id`
- `profileId`
- `candidateProfileId`
- `compatibilityScore`
- `explanation`
- `status`
- timestamps

### Planned package responsibilities

```text
controller/       HTTP endpoints and request/response mapping
service/          use cases, transactions, matching orchestration
model/roommate/   domain entities and repository interfaces
dto/              API request and response contracts
exception/        typed errors and global error responses
config/           OpenAPI, mapping, security, and persistence configuration
```

### API direction

The API should converge on plural, versioned resources:

```text
GET    /api/v1/accounts
POST   /api/v1/accounts
GET    /api/v1/accounts/{id}
PUT    /api/v1/accounts/{id}
DELETE /api/v1/accounts/{id}

GET    /api/v1/profiles
POST   /api/v1/profiles
GET    /api/v1/profiles/{id}
PUT    /api/v1/profiles/{id}
DELETE /api/v1/profiles/{id}

GET    /api/v1/profiles/{id}/matches
```

The exact contract should be finalized before client integration. Request DTOs should be separate from persistence entities, and validation errors should use a consistent JSON error shape.

### Matching design direction

The first matching version should be deterministic and explainable:

1. Filter candidates by hard constraints such as location, budget, move-in date, and incompatible preferences.
2. Score remaining candidates using weighted preference dimensions.
3. Normalize the score to a documented range.
4. Return the score and a short explanation of the strongest compatible factors.
5. Add unit tests for each rule and edge case before optimizing the implementation.

## Running locally

Prerequisites:

- Java 17
- Maven 3.9+ or the included Maven wrapper

Run tests:

```bash
mvn test
```

Start the application:

```bash
mvn spring-boot:run
```

Useful local URLs once the application starts:

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

The database is currently configured through Spring Boot defaults and is intended for local development only.

## Repository and Git workflow

The configured remotes are:

- `origin`: [VirajSonaje/RoommateMatchingAppService](https://github.com/VirajSonaje/RoommateMatchingAppService) — personal fork/working repository.
- `upstream`: [CalmCodeCollective/RoommateMatchingAppService](https://github.com/CalmCodeCollective/RoommateMatchingAppService) — source repository.

The current branch is `Profile`. Before making feature changes, create a focused branch from the intended base branch, keep commits small, and update [TASKS.md](TASKS.md) as work is completed.

## Documentation status

This README is the initial HLD/LLD baseline. It should be updated whenever the API, data model, deployment model, or matching rules materially change.
