# Security Policy

This repository is a design pattern study lab. It does not process production
data, but examples should still avoid unsafe defaults.

## Supported Scope

Security review focuses on:

- examples that demonstrate unsafe object creation or data access patterns;
- accidental secrets committed to examples or documentation;
- dependencies used by runnable examples;
- misleading guidance that could encourage insecure design.

## Reporting A Security Concern

Open a concise issue describing the pattern, example, or document that needs
attention. Do not include real credentials or private data.

## Development Practices

- Keep examples small and explicit.
- Avoid real secrets in sample code.
- Prefer secure defaults when a pattern touches object creation or access.
