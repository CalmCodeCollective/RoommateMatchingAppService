# Roommate Matching App Service — Local Task Plan

This file is the working task list for taking the project from its current state to a usable, production-ready roommate-matching backend. Keep it updated after each meaningful change.

## How to use this file

- `[ ]` not started
- `[-]` in progress
- `[x]` complete
- Add a short note or commit reference beside completed work when useful.
- Keep implementation tasks small enough to review and commit independently.

## Phase 0 — Baseline and project hygiene

- [x] Use Java 17 locally and get `mvn test` passing.
- [ ] Decide the base branch for future work (`develop` is currently the likely integration branch).
- [ ] Create a feature branch for each focused change.
- [ ] Add a standard local configuration strategy and document environment variables.
- [ ] Decide whether HATEOAS will remain part of the public API.

## Phase 1 — Stabilize the existing API

- [ ] Finalize plural, versioned routes under `/api/v1`.
- [x] Fix the profile controller so create, read, update, and delete operations behave consistently.
- [x] Remove unused controller dependencies and configure `ModelMapper`.
- [x] Correct profile not-found messaging and standardize error responses.
- [x] Add request/response DTOs instead of exposing JPA entities directly.
- [ ] Add Bean Validation for required fields and allowed preference values.
- [ ] Define duplicate-account and duplicate-profile behavior.
- [x] Add integration tests for profile CRUD behavior.

## Phase 2 — Data model and persistence

- [ ] Finalize the `Account` to `Profile` relationship and ownership rules.
- [ ] Add database constraints and indexes for handles, account references, and match queries.
- [ ] Replace implicit H2 schema creation with migrations using Flyway or Liquibase.
- [ ] Add PostgreSQL configuration for local and production environments.
- [ ] Add created/updated timestamps and optimistic locking where needed.
- [ ] Add repository tests for profile filtering and candidate lookup.

## Phase 3 — Matching MVP

- [ ] Define hard constraints and soft preference dimensions with product-level agreement.
- [ ] Create a `Match` domain model or projection strategy.
- [ ] Implement a deterministic, weighted compatibility score.
- [ ] Return score explanations so results are understandable to users.
- [ ] Add unit tests for scoring, filtering, missing preferences, and ties.
- [ ] Add `GET /api/v1/profiles/{id}/matches` with pagination and stable ordering.
- [ ] Add safeguards for self-matching and blocked/inactive accounts.

## Phase 4 — Security and production readiness

- [ ] Add authentication and account ownership checks.
- [ ] Add authorization for profile updates and deletes.
- [ ] Protect sensitive fields and define data-retention/deletion behavior.
- [ ] Add structured logging, health checks, metrics, and correlation IDs.
- [ ] Add centralized configuration for secrets and environment-specific settings.
- [ ] Add CI checks for build, tests, formatting, and dependency/security scanning.
- [ ] Add container/deployment configuration and a production runbook.

## Phase 5 — Client integration and launch

- [ ] Publish the finalized OpenAPI contract.
- [ ] Build or connect the client application.
- [ ] Add end-to-end tests covering account, profile, and matching flows.
- [ ] Add seed/demo data only behind an explicit development profile.
- [ ] Run load and failure-mode tests for matching queries.
- [ ] Review privacy, accessibility, and user-safety requirements.
- [ ] Tag the first release and document rollback procedures.

## Current blockers / observations

- The checked-in Maven project targets Java 17, while the previously observed local runtime was Java 8.
- Profile CRUD work is implemented locally and verified with integration tests; it is ready for review.
- The repository has two remotes: personal fork (`origin`) and source repository (`upstream`).

## Next recommended task

Add Bean Validation and finalize the profile preference contract before adding matching logic.
