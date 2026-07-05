package com.joaoscioli.patterns.specification;

public record PlanUpgradeRequest(
        Plan currentPlan,
        Plan targetPlan,
        boolean accountActive,
        int unpaidInvoices,
        int requestedSeats
) {
    public PlanUpgradeRequest {
        if (currentPlan == null) {
            throw new IllegalArgumentException("currentPlan is required");
        }
        if (targetPlan == null) {
            throw new IllegalArgumentException("targetPlan is required");
        }
        if (unpaidInvoices < 0) {
            throw new IllegalArgumentException("unpaidInvoices must not be negative");
        }
        if (requestedSeats <= 0) {
            throw new IllegalArgumentException("requestedSeats must be greater than zero");
        }
    }
}
