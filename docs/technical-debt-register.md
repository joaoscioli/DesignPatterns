# Technical Debt Register

This register makes project trade-offs visible instead of hiding unfinished work.

## Current Debt

| Area | Debt | Impact | Next Action |
| --- | --- | --- | --- |
| Coverage | Some patterns are documented before full code examples. | Reviewers may see uneven depth across patterns. | Add one executable example per priority pattern. |
| Comparisons | Not every pattern has a simpler alternative shown. | Trade-offs may be less concrete. | Add before-and-after refactoring examples. |
| Realism | Examples are intentionally small. | Some production concerns are implicit. | Add one backend workflow case study. |

## Review Rule

Debt is acceptable when it is explicit, bounded, and connected to a follow-up
decision. Hidden debt is what damages engineering trust.
