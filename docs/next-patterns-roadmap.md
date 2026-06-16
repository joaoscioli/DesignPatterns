# Next Patterns Roadmap

This roadmap defines which design patterns can be added next and why they are
useful for backend interviews.

## Candidate Patterns

| Pattern | Why it matters |
| --- | --- |
| Template Method | Useful for shared workflow steps with controlled variation. |
| Chain of Responsibility | Useful for validation pipelines and request handling. |
| State | Useful for lifecycle transitions like subscriptions or orders. |
| Specification | Useful for composable business rules and filtering. |
| Proxy | Useful for access control, caching, and remote boundaries. |

## Priority

1. State pattern for subscription lifecycle.
2. Chain of Responsibility for request validation.
3. Specification for business-rule composition.

## Selection Criteria

New examples should:

- solve a realistic backend problem;
- include tests;
- be small enough to review quickly;
- include README documentation;
- avoid abstract examples with no domain context.

## Interview Talking Points

- Pattern choice should follow the business problem.
- Lifecycle-heavy domains often benefit from State.
- Validation workflows can be modeled as chains.
- Specifications help when rules need composition.
