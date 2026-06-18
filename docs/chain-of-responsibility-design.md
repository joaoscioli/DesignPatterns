# Chain of Responsibility Pattern Design

This document plans a future Chain of Responsibility example for the lab.

## Intent

Chain of Responsibility passes a request through a sequence of handlers until
one handler processes it or the chain reaches the end.

The pattern is useful when several validation, approval, or decision steps must
run in order without hardcoding all logic inside one large method.

## Portfolio Scenario

Use the pattern to model a subscription approval flow.

Before a subscription can be activated, the system checks:

- customer account status;
- selected plan availability;
- payment method validity;
- fraud risk score;
- final approval.

Each step can approve the request and pass it forward, reject it with a reason,
or stop the chain when a mandatory condition fails.

## Proposed Classes

- `SubscriptionApprovalRequest`
- `ApprovalResult`
- `ApprovalHandler`
- `AccountStatusHandler`
- `PlanAvailabilityHandler`
- `PaymentMethodHandler`
- `FraudRiskHandler`
- `SubscriptionApprovalChain`

## Design Rules

- Each handler should have one responsibility.
- The chain order should be explicit.
- Rejection reasons should be easy to assert in tests.
- Handlers should not know about concrete handlers after them.
- Adding a new approval rule should not require rewriting the existing rules.

## Example Flow

```text
request
  -> account status handler
  -> plan availability handler
  -> payment method handler
  -> fraud risk handler
  -> approved
```

If the payment method is invalid, the chain stops at the payment handler and
returns a rejected result.

## Tests To Add

- approved request passes through the full chain;
- inactive customer is rejected early;
- unavailable plan stops the chain;
- invalid payment method stops the chain;
- high fraud risk returns a clear rejection reason;
- handlers can be composed in a predictable order.

## Interview Talking Point

This pattern is valuable when the interviewer asks how to avoid a service method
full of nested `if` statements. The senior answer is not only naming the
pattern, but explaining when the extra abstraction is worth the cost.
