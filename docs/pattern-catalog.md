# Pattern Catalog

This catalog helps reviewers understand what each example demonstrates.

| Pattern | Portfolio scenario | Main signal |
| --- | --- | --- |
| Strategy | Checkout discount calculation | Replace business rules without changing the caller |
| Factory | Notification sender creation | Centralize object creation behind an interface |
| Builder | API request construction | Create immutable objects with readable optional fields |
| Adapter | Legacy payment client integration | Protect domain code from external API shape |
| Observer | Subscription lifecycle notifications | Decouple event publishers from side effects |
| Decorator | API response enrichment | Add behavior through composition |
| Command | Billing action dispatch | Represent work as executable objects |
| Chain of Responsibility | Subscription approval flow | Compose ordered validation and approval steps |
| State | Subscription lifecycle transitions | Keep state-specific behavior explicit |
| Specification | Plan upgrade eligibility | Compose reusable business rules |

## How To Read This Repository

Start with one business problem, then inspect the related package and tests.

The examples are intentionally small. The goal is not to create framework-heavy
code, but to show when a pattern improves clarity and when it would be
unnecessary abstraction.

## Interview Talking Point

A strong answer does not only name a pattern. It explains the pressure that
made the pattern useful, the trade-off introduced by the abstraction, and how
tests prove the behavior.

For the Command example, also discuss failure behavior. The dispatcher keeps
queued commands when execution fails, which makes the retry decision explicit
instead of silently losing work.
