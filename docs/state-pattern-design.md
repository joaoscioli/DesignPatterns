# State Pattern Design

This document designs the next practical example for the repository: a State
pattern implementation for subscription lifecycle transitions.

## Problem

A subscription can move through several states:

- `ACTIVE`;
- `PAST_DUE`;
- `CANCELED`;
- `EXPIRED`.

Each state allows different transitions. Putting every rule in one large
conditional can make the lifecycle harder to extend and test.

## Candidate State Transitions

| Current state | Event | Next state |
| --- | --- | --- |
| ACTIVE | payment failed | PAST_DUE |
| ACTIVE | canceled | CANCELED |
| PAST_DUE | payment recovered | ACTIVE |
| PAST_DUE | canceled | CANCELED |
| PAST_DUE | grace period ended | EXPIRED |
| CANCELED | any transition | rejected |
| EXPIRED | any transition | rejected |

## Design Direction

Use one state interface:

```java
interface SubscriptionState {
    SubscriptionStatus status();
    SubscriptionStatus onPaymentFailed();
    SubscriptionStatus onPaymentRecovered();
    SubscriptionStatus onCanceled();
    SubscriptionStatus onGracePeriodEnded();
}
```

Each state object owns the transitions it allows.

## Why State Fits

State fits when:

- behavior depends on current lifecycle status;
- transitions need explicit rules;
- invalid transitions should be tested;
- new states may appear later.

## Interview Talking Points

- State avoids spreading lifecycle rules across many conditionals.
- Invalid transitions become explicit behavior.
- Tests can focus on one state at a time.
- State is useful when status changes affect allowed actions.
