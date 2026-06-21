# Pattern Trade-offs

Design patterns are useful only when their benefits are larger than their cost.

## Common Benefits

- Reduce duplication around changing behavior.
- Make extension points explicit.
- Isolate external systems from domain code.
- Move lifecycle rules closer to the state that owns them.
- Make side effects easier to test.

## Common Costs

- More classes to navigate.
- More indirection for simple behavior.
- Names can become more abstract than the problem.
- New contributors may need extra context.
- Tests may need more setup if the pattern is overused.

## Practical Rule

Use a pattern when it makes change safer, behavior clearer, or tests more
focused.

Avoid a pattern when it only makes the code look more sophisticated.

## Interview Example

```text
I would not start with State for every status enum. I would introduce it when
transition rules become complex enough that a single conditional is harder to
read and test.
```

## Portfolio Signal

This shows that pattern knowledge is balanced with engineering judgment. Senior
engineers are expected to know when not to use an abstraction.
