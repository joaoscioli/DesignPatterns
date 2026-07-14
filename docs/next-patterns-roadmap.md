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

1. Template Method for billing retry workflows.
2. Proxy for access control or caching boundaries.
3. Composite for composed pricing rules.

Already covered:

- State pattern for subscription lifecycle.
- Chain of Responsibility for request validation.
- Specification for business-rule composition.

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
- Template Method is useful when workflow order must stay fixed but individual
  steps vary by provider or policy.
