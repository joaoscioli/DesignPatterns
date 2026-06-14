# Design Pattern Anti-Patterns

Patterns can improve design, but they can also make code harder to maintain
when used without a real problem.

## Common Anti-Patterns

### Pattern First Design

Starting with a pattern before understanding the problem often creates
unnecessary abstraction.

Better approach:

- describe the change pressure first;
- identify what should vary;
- choose the simplest structure that handles that variation.

### Factory For Simple Constructors

A factory is not always needed when a constructor is clear and stable.

Use a factory when creation logic has real branching, validation, or dependency
selection.

### Strategy For Two Stable Branches

Strategy can be useful when behavior changes often. For two stable branches,
a simple conditional may be easier to read.

### Observer Without Visibility

Observer can hide side effects if subscribers are hard to find.

Mitigation:

- keep event names explicit;
- test subscriber behavior;
- document important side effects.

### Decorator Stack Confusion

Too many decorators can make behavior order hard to reason about.

Mitigation:

- keep decorators small;
- test combined behavior;
- avoid deep chains without clear naming.

## Interview Talking Points

- Patterns are not proof of seniority by themselves.
- A senior engineer can explain when not to use a pattern.
- Simpler code is often the better design.
- Abstractions should pay rent through flexibility, readability, or testability.
