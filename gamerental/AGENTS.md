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

1. Create/update a tickable backlog markdown file with a linear list of distinct feature slices:
   one line per command, query, side-effect handler, and endpoint (including test coverage expectations per slice).
2. Keep that backlog updated while work progresses.
3. Always present the backlog for user review/approval before starting any implementation.

This backlog step may be skipped only when the user explicitly requests a different planning format.

### Non-trivial Task

For every non-trivial task, the agent MUST:

1. Select the next uncompleted backlog item and work only that feature slice (typically one command, one query, one
   side-effect, or one endpoint), not waterfall batches.
2. Refresh on applicable non-negotiable rules from `.fluxzero/agents/rules/guidelines.md` for the selected slice.
3. Implement the selected slice.
4. Add/update tests for all behavior rules in that slice (happy path + all invariant/authorization/error cases).
5. Run tests and ensure both modified tests and the full relevant suite pass.
6. Perform a final refactor/readability and guideline-conformance pass for both code and tests in the just-finished
   feature.
7. Pause for review/checkpoint.
8. Implement changes from review if needed.
9. Report compliance checks in final output.
10. Update the active project/phase backlog after finishing/reporting the slice.
11. Optionally commit if the user prefers; when unclear, ask the user whether to commit.
