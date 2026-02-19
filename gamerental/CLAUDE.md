# Project Guidelines

## Fluxzero Guidelines Required to Follow

**CRITICAL: Before starting any task, you MUST follow all guidance in `.fluxzero/agents/index.md`.**

## Mandatory Read Order (Do Not Skip)

1. `.fluxzero/agents/index.md`
2. `.fluxzero/agents/rules/guidelines.md`
3. `.fluxzero/agents/rules/runtime-interaction.md`
4. Task-specific manuals selected via the decision tree in `guidelines.md`

## Execution Protocol (Mandatory)

### New Project or Large Phase

At the start of every new project or major project phase, the agent MUST:

1. Create/update a tickable backlog markdown file with a linear list of functionalities only.
2. Keep that backlog updated while work progresses.

This backlog step may be skipped only when the user explicitly requests a different planning format.

### Non-trivial Task

For every non-trivial task, the agent MUST:

1. Confirm applicable non-negotiable rules.
2. Update the active project/phase backlog for this task (if such a backlog exists).
3. Implement changes.
4. Add/update tests for behavior changes.
5. Run relevant tests.
6. Perform a final refactor/readability pass for the code written for the just-finished feature when the feature is
   non-trivial. Do not expand to unrelated project-wide cleanup unless explicitly requested.
7. Report compliance checks in final output.
