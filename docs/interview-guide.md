# Design Patterns Interview Guide

This guide explains how to discuss the examples in this repository during
technical interviews.

## How To Explain A Pattern

A strong answer should cover:

- the problem the pattern solves;
- why the current example uses it;
- what trade-off it introduces;
- when the pattern would be unnecessary;
- how tests prove the behavior.

Avoid memorized definitions. Companies usually want to know whether you can use
patterns to simplify real code.

## Current Examples

| Pattern | Interview angle |
| --- | --- |
| Strategy | Swapping business rules without changing the service. |
| Factory | Centralizing object creation behind an interface. |
| Builder | Making object creation readable and validated. |
| Adapter | Protecting domain code from legacy integration details. |
| Observer | Publishing events to independent subscribers. |
| Decorator | Adding behavior through composition. |
| Command | Representing queued actions as executable objects. |

## Senior-Level Discussion Points

- Patterns are tools, not goals.
- A simple method is better than a pattern when the problem is simple.
- A pattern earns its place when it reduces coupling or clarifies change.
- Tests should describe behavior, not implementation trivia.
- The best pattern choice depends on the direction of expected change.

## Questions To Practice

- Why use Strategy instead of `if/else`?
- When does Factory become unnecessary?
- What problem does Adapter solve in external integrations?
- How can Observer create debugging complexity?
- Why can Decorator be preferable to inheritance?
- How does Command relate to background jobs or queues?
