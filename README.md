# Fluxzero Project Templates

This repository contains the starter projects distributed with the
[Fluxzero CLI](https://github.com/fluxzero-io/fluxzero-cli):

- `flux-basic-java`: Java starter with Maven and Gradle support.
- `flux-basic-kotlin`: Kotlin starter with Maven and Gradle support.

The templates provide a working starting point, not a prescribed application design. After generation, adapt the
domain model, authentication, endpoints, dependencies, tests, and optional frontend to the actual product requirements.

## Generate a project

Install the latest Fluxzero CLI and select a template:

```bash
curl -sSL https://github.com/fluxzero-io/fluxzero-cli/releases/latest/download/install.sh | sh
fz init --template flux-basic-java --name my-app --package com.example.myapp --build maven
```

Use `flux-basic-kotlin` for a Kotlin project and choose either `maven` or `gradle` for `--build`.

Generated projects do not contain local Fluxzero SDK manuals. Their small agent instruction file directs coding agents
to the installed Fluxzero plugin, which supplies the application-building workflow and current MCP documentation.

## Validate templates

Each template is independently buildable with both wrappers:

```bash
cd flux-basic-java
./mvnw test
./gradlew test
```

Create the release archive locally with:

```bash
./.github/scripts/package-templates.sh
```

## License

Licensed under the Apache License 2.0. See [LICENSE](LICENSE).
