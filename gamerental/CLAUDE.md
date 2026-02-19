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

1. Confirm applicable non-negotiable rules.
2. Update the active project/phase backlog for this task (if such a backlog exists).
3. Implement using the Fluxzero inside-out method from `.fluxzero/agents/rules/guidelines.md` (domain model and
   commands/handlers/tests before transport endpoints).
4. Work one feature slice at a time (typically one command, one query, one side-effect, or one endpoint), not in
   waterfall batches.
5. Add/update tests for all behavior rules in that slice (happy path + all invariant/authorization/error cases).
6. Run relevant tests for that slice.
7. Pause after each completed slice for review/checkpoint before proceeding to the next slice.
8. Implement changes.
9. Perform a final refactor/readability pass for the code written for the just-finished feature when the feature is
   non-trivial. Do not expand to unrelated project-wide cleanup unless explicitly requested.
10. Report compliance checks in final output.
