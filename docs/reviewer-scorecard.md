# Reviewer Scorecard

Use this scorecard to review the repository quickly during portfolio screening.

## Strong Signals

- Patterns are connected to backend scenarios instead of abstract examples.
- Each example is small enough to inspect and discuss.
- Tests prove behavior and make trade-offs easier to evaluate.
- The repository explains when patterns help and when they add unnecessary complexity.

## Evidence

- `src/test/java/com/joaoscioli/patterns/command/BillingCommandDispatcherTest.java`
- `src/test/java/com/joaoscioli/patterns/state/SubscriptionLifecycleTest.java`
- `src/test/java/com/joaoscioli/patterns/specification/PlanUpgradePolicyTest.java`
- `docs/pattern-tradeoffs.md`

## Next Senior Step

Add a Template Method or Strategy-plus-Policy example for billing retry flows,
then document why the chosen design fits the workflow.
