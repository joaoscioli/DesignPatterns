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
- testing command results and queue cleanup.

## Run Tests

```bash
mvn test
```

## Interview Preparation

- [Design Patterns Interview Guide](docs/interview-guide.md)

## Tech Stack

- Java 21
- Maven
- JUnit Jupiter

## Portfolio Role

This is a supporting repository. It helps demonstrate design vocabulary and
clean object-oriented thinking that can be referenced during interviews.
