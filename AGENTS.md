# AGENTS.md

Instructions for coding agents working in this repository.

## Project Shape

This repository contains example Fluxzero applications. They are both learning material and source templates for the Fluxzero CLI (`fz init`).

- `flux-basic-java`: minimal Java example for the core Fluxzero concepts and SDK usage.
- `flux-basic-kotlin`: Kotlin version of the basic example, including Kotlin-specific build and style conventions.
- `gamerental`: larger sample application that demonstrates richer domain modeling, command handling, queries, validation, web APIs, realtime behavior, and tests.

Each example is intended to be self-contained and runnable on its own. Example-specific `AGENTS.md` files and `.fluxzero/agents` guidance inside an example directory apply when working in that example.

## Build And Test

- Work from the example directory you are changing, not from the repository root.
- Each example supports both Gradle and Maven. Prefer the wrapper scripts in that example: `./gradlew` and `./mvnw`.
- Use `./gradlew test` or `./mvnw test` for focused verification, and run the app with `./gradlew runTestApp` or `./mvnw exec:java` when behavior needs to be checked manually.
- Fluxzero examples currently require Java 25 or newer.
- Keep wrapper scripts executable, especially when changing or adding templates.

## Example And Template Guidelines

- Examples should remain clear, small enough to learn from, and practical enough to copy into real projects.
- Keep every example self-contained: README, build files, tests, runnable app entry point, and any `.test` requests or supporting files should move together.
- Preserve dual Gradle/Maven support unless the task explicitly says otherwise.
- When adding or renaming packages, files, artifacts, or project metadata, update the example's `refactor.yaml` so `fz init` can customize the template correctly.
- Avoid committing build output, generated local artifacts, IDE caches, or example runtime state.
- Prefer existing Fluxzero SDK patterns and the guidance in each example's `.fluxzero/agents` directory before introducing new application structure.

## Coding Guidelines

- Treat examples as public teaching material: favor readable, idiomatic code over clever shortcuts.
- Keep domain behavior covered by focused tests, especially for commands, queries, handlers, validation, and error paths.
- Keep dependencies minimal and aligned between Maven and Gradle files.
- Make documentation changes alongside behavior changes when an example's setup, usage, or learning goal changes.
- Do not make examples depend on unpublished local SDK state unless the task is specifically about local SDK development.

## Commit Messages

- Use Conventional Commits with a clear scope for human-authored commits.
- Format: `<type>(<scope>): <short imperative summary>`.
- Good examples: `docs(root): add agent guidelines`, `fix(gamerental): validate rental period`, `test(kotlin): cover command handler`.
- Common types: `feat`, `fix`, `refactor`, `test`, `docs`, `build`, `ci`, `deps`, `perf`, and `chore`.
- Useful scopes in this repo include `root`, `flux-basic-java`, `flux-basic-kotlin`, `gamerental`, `templates`, `docs`, `ci`, and `deps`.
- For non-trivial commits, include a body that explains why the change is needed, the behavioral impact, and the tests that were run.
