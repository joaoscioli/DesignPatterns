# Refactoring Signals

Design patterns should appear as a response to real design pressure.

## When A Pattern May Help

- A method has too many conditional branches.
- Business rules change independently from the caller.
- Object construction is hard to read or easy to misuse.
- External API details leak into domain code.
- Side effects are mixed into core business behavior.
- Lifecycle transitions are spread across unrelated classes.

## When A Pattern May Hurt

- The example is too small to need abstraction.
- The pattern hides simple behavior behind too many classes.
- The abstraction name is more important than the problem.
- Tests become harder to read after the pattern is introduced.

## How To Explain This In Interviews

Start with the code smell, then explain the pattern as the chosen refactoring.

Example:

```text
The subscription lifecycle was becoming conditional-heavy, so State made each
transition rule explicit and easier to test.
```

## Portfolio Signal

This shows that patterns are not memorized vocabulary. They are tools used when
they make code easier to change, test, or reason about.
