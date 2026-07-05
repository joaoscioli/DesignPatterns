# Specification Pattern Design

The Specification pattern example models plan upgrade eligibility for a SaaS
account.

## Problem

Plan upgrades often depend on several business rules:

- the account must be active;
- the account must have no unpaid invoices;
- the target plan must be different from the current plan;
- requested seats must fit inside the target plan limit.

Putting all rules in a single conditional makes the policy harder to test and
harder to extend.

## Design

The example uses a small `Specification<T>` interface with composition methods:

```java
specification.and(other)
specification.or(other)
specification.not()
```

Each upgrade rule is represented as a specification. `PlanUpgradePolicy`
evaluates the standard rules and returns an `UpgradeDecision` with the failed
rule names.

## Why Specification Fits

Specification fits when:

- business rules are reusable;
- rules need to be combined;
- rules should be tested independently;
- a policy should report which rules failed;
- adding a new rule should not require rewriting a large conditional.

## Trade-Offs

The pattern adds abstraction. It is useful when rules are numerous or reused.
For one or two simple checks, direct conditionals are usually clearer.

## Interview Talking Points

- Specification keeps business rules explicit and composable.
- It helps avoid large `if` blocks in services.
- It supports clearer tests for eligibility logic.
- It is a good fit for backend domains such as billing, subscriptions,
  approvals, discounts, and access policies.
