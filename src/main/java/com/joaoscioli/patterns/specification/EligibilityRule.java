package com.joaoscioli.patterns.specification;

public record EligibilityRule(
        String name,
        Specification<PlanUpgradeRequest> specification
) {
    public EligibilityRule {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        if (specification == null) {
            throw new IllegalArgumentException("specification is required");
        }
    }

    boolean isSatisfiedBy(PlanUpgradeRequest request) {
        return specification.isSatisfiedBy(request);
    }
}
