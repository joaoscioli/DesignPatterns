# Design Patterns Lab

> Portfolio status: active Java design patterns lab.

This repository is being rebuilt as a practical design patterns lab in Java.
Its purpose is to show how classic patterns can solve concrete problems without
turning examples into artificial code.

## Why This Repository Exists

Design patterns are useful in interviews when they are explained through intent,
trade-offs, and maintainable code. This lab keeps each example small, tested,
and connected to backend engineering scenarios.

Current focus:

- Java 21;
- object-oriented design;
- readable pattern examples;
- testable implementations;
- small commit discipline.

## Current Examples

### Strategy Pattern

The Strategy pattern example calculates checkout discounts.

It demonstrates:

- defining a stable behavior contract with `DiscountStrategy`;
- swapping discount rules without changing `CheckoutService`;
- validating edge cases through tests;
- keeping business rules explicit and easy to extend.

### Factory Pattern

The Factory example creates notification senders from a selected channel.

It demonstrates:

- centralizing object creation in `NotifierFactory`;
- hiding concrete notifier classes behind the `Notifier` interface;
- selecting implementations for email, SMS, and webhook notifications;
- keeping caller code independent from constructor details;
- testing both valid channel selection and invalid input.

### Builder Pattern

The Builder example creates immutable API request objects with required fields,
optional fields, and defaults.

It demonstrates:

- making object creation readable when there are multiple parameters;
- avoiding unclear constructor argument order;
- validating required fields at build time;
- applying sensible defaults for optional values;
- keeping the final object immutable.

### Adapter Pattern

The Adapter example connects a clean domain payment interface to a legacy
payment client with a different method shape and response model.

It demonstrates:

- protecting domain code from external API details;
- translating legacy responses into domain results;
- keeping integration code isolated;
- testing approved and rejected payment flows;
- making future provider replacement easier.

### Observer Pattern

The Observer example publishes subscription lifecycle events to independent
email and audit observers.

It demonstrates:

- notifying multiple subscribers without coupling the publisher to concrete actions;
- subscribing and unsubscribing observers at runtime;
- modeling simple domain events with explicit event types;
- keeping side effects isolated behind observer implementations;
- testing notification delivery and unsubscribe behavior.

### Decorator Pattern

The Decorator example enriches API responses with tracing and cache headers
without changing the basic response renderer.

It demonstrates:

- adding responsibilities through composition instead of inheritance;
- stacking decorators to combine independent behaviors;
- preserving the original response body while enriching metadata;
- validating decorator configuration;
- testing decorated and undecorated response rendering.

### Command Pattern

The Command example queues billing actions such as invoice generation and
subscription suspension behind a common executable contract.

It demonstrates:

- representing actions as objects;
- decoupling command creation from command execution;
- executing queued work in a predictable order;
- validating command input before dispatch;
- testing command results, queue cleanup, empty dispatch, and failure behavior.

### Chain of Responsibility Pattern

The Chain of Responsibility example models a subscription approval flow where
validation and approval steps are composed as independent handlers.

See: [Chain of Responsibility Pattern Design](docs/chain-of-responsibility-design.md).

### State Pattern

The State example models subscription lifecycle transitions such as active,
past due, canceled, and expired.

See: [State Pattern Design](docs/state-pattern-design.md).

### Specification Pattern

The Specification example models plan upgrade eligibility by composing business
rules for account status, unpaid invoices, plan changes, and seat limits.

See: [Specification Pattern Design](docs/specification-pattern-design.md).

## Run Tests

```bash
mvn test
```

## Interview Preparation

- [Pattern Catalog](docs/pattern-catalog.md)
- [Reviewer Scorecard](docs/reviewer-scorecard.md)
- [Technical Depth Map](docs/technical-depth-map.md)
- [Portfolio Positioning](docs/portfolio-positioning.md)
- [Hiring Manager Summary](docs/hiring-manager-summary.md)
- [30-Second Pitch](docs/30-second-pitch.md)
- [Interview Route](docs/interview-route.md)
- [Key Talking Points](docs/key-talking-points.md)
- [Pre-Interview Checklist](docs/pre-interview-checklist.md)
- [Tough Interview Questions](docs/tough-interview-questions.md)
- [Senior Review Notes](docs/senior-review-notes.md)
- [Architecture Review Checklist](docs/architecture-review-checklist.md)
- [Business Value](docs/business-value.md)
- [Technical Debt Register](docs/technical-debt-register.md)
- [Decision Log](docs/decision-log.md)
- [Production Readiness Matrix](docs/production-readiness-matrix.md)
- [Demo Evaluation Rubric](docs/demo-evaluation-rubric.md)
- [Next Review Focus](docs/next-review-focus.md)
- [Demo Script](docs/demo-script.md)
- [Test Map](docs/test-map.md)
- [Design Patterns Interview Guide](docs/interview-guide.md)
- [Interview Questions](docs/interview-questions.md)
- [Pattern Selection Guide](docs/pattern-selection.md)
- [Pattern Trade-offs](docs/pattern-tradeoffs.md)
- [Refactoring Signals](docs/refactoring-signals.md)
- [Design Pattern Anti-Patterns](docs/anti-patterns.md)
- [Next Patterns Roadmap](docs/next-patterns-roadmap.md)
- [State Pattern Design](docs/state-pattern-design.md)
- [Specification Pattern Design](docs/specification-pattern-design.md)
- [Changelog](CHANGELOG.md)

## Interview Checkpoint

A reviewer should inspect the Command, State, and Specification examples first.
Together they show how patterns are selected from business pressure instead of
being added as decoration.

## Portfolio Proof

This repository proves design vocabulary with restraint. The examples are small,
tested, and connected to backend scenarios so the discussion stays practical
instead of theoretical.

## Fast Review Path

1. Read the [30-Second Pitch](docs/30-second-pitch.md) to understand the repository signal.
2. Open the [Interview Route](docs/interview-route.md) to choose the best examples to discuss.
3. Inspect Command, State, and Specification tests to validate practical design decisions.

## Tech Stack

- Java 21
- Maven
- JUnit Jupiter

## Portfolio Role

This is a supporting repository. It helps demonstrate design vocabulary and
clean object-oriented thinking that can be referenced during interviews.
