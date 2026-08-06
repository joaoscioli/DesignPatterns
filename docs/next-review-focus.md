# Next Review Focus

This file defines the next focused improvement for a technical reviewer to watch.

## Current Focus

Add a Template Method example for billing retry workflows.

## Why It Matters

The repository already shows several pattern categories. A retry workflow would
make the next example feel closer to production backend behavior, where the
steps are stable but individual retry policies can vary.

## Expected Evidence

- A small domain scenario with repeated workflow steps.
- Tests for successful retry, exhausted retry, and invalid input.
- Documentation explaining why Template Method fits better than a simple helper.
