# Interview Questions

Use these questions to prepare a practical design-pattern discussion.

## How do you decide when to use a pattern?

A pattern should answer a real change pressure, such as replacing an algorithm,
composing business rules, modeling lifecycle transitions, or decoupling command
creation from execution.

## Which examples should be discussed first?

Command, State, and Specification are the strongest examples because they map
directly to backend workflows, lifecycle behavior, and business-rule
composition.

## How do you avoid overengineering?

Keep the pattern small, test the behavior, and be able to explain what would be
harder without the pattern. If the explanation is only "best practice", the
pattern is probably not justified.

## What is the senior-level signal?

The senior signal is not naming patterns. It is recognizing trade-offs,
choosing restraint, and using tests to prove that the design remains flexible
without hiding simple behavior.
