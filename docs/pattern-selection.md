# Pattern Selection Guide

This guide helps decide when a design pattern is useful and when it is
unnecessary.

## Selection Rule

Use a pattern when it makes the expected change easier to handle.

Avoid a pattern when it only makes a simple solution look more sophisticated.

## Questions Before Applying A Pattern

- What is likely to change?
- Which code should stay stable?
- Does this pattern reduce coupling?
- Does it improve testability?
- Will a teammate understand it quickly?
- Is a simpler function or class enough?

## Examples

| Situation | Candidate pattern |
| --- | --- |
| Several interchangeable business rules | Strategy |
| Object creation depends on a type or channel | Factory |
| Object creation has many optional fields | Builder |
| Legacy or external API shape does not match the domain | Adapter |
| Multiple subscribers need domain events | Observer |
| Behavior should be added without inheritance | Decorator |
| Actions need to be queued or dispatched later | Command |

## Interview Talking Points

- Patterns should follow the problem, not lead it.
- Over-engineering is also a design smell.
- A pattern should make future change cheaper.
- Tests help prove the pattern is serving behavior.
