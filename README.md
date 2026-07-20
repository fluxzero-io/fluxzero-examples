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

Generated projects do not contain local Fluxzero SDK manuals. Their small agent instruction files direct Codex,
Claude Code, Cursor, Gemini CLI, or GitHub Copilot to the matching package in
[`fluxzero-agent-plugins`](https://github.com/fluxzero-io/fluxzero-agent-plugins), which supplies the shared
application-building skill, current MCP documentation, and the automated local development feedback server.

`AGENTS.md` contains the shared rule used by Codex, Cursor, and GitHub Copilot. `CLAUDE.md` and `GEMINI.md` import that
rule and add only the bootstrap command and reload boundary required by their own clients. The plugin (or Gemini
extension) remains an agent-level installation and is not copied into generated projects, so each Fluxzero MCP server is
registered only once.

Generated projects include tracked `.fluxzero/dev.yaml` defaults. Run `fz dev` for an interactive environment; installed
agent plugins use `fz mcp --ensure-dev` to start or reuse that same environment automatically. It owns watching,
compilation, application replacement, and affected-test execution so coding agents can consume structured feedback
without launching duplicate processes.

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
