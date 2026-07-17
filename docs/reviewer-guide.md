# Reviewer Guide

This guide helps a technical reviewer scan the repository quickly and understand
what engineering signals to look for.

## What To Review First

1. `README.md` for repository purpose and learning path.
2. `docs/pattern-catalog.md` for the current pattern coverage.
3. `docs/pattern-selection.md` for when each pattern is useful.
4. `docs/pattern-tradeoffs.md` for costs and misuse cases.
5. `src/test/java` for behavior-focused examples.
6. Command tests for failure and empty-queue behavior.

## Strong Signals

- Patterns are connected to business-style examples, not toy-only snippets.
- Tests show expected behavior and protect the pattern intent.
- Documentation explains trade-offs, refactoring signals, and anti-patterns.
- New patterns are added with code, tests, and a short design note.
- Edge cases are tested so the examples read like production-oriented design,
  not only pattern demonstrations.

## Interview Talking Points

- Explain why Strategy, State, Specification, and Chain of Responsibility solve
  different kinds of change.
- Discuss when a pattern is overengineering.
- Show how tests make pattern behavior easier to review than diagrams alone.
- Explain how failure behavior affects whether a pattern is safe to use.

## Next Practical Step

Add Template Method with a billing retry workflow. It would show a fixed
business process with provider-specific variation, which is common in payment
and integration code.
