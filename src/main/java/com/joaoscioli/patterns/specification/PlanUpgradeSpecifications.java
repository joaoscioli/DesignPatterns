package com.joaoscioli.patterns.specification;

public final class PlanUpgradeSpecifications {

    private PlanUpgradeSpecifications() {
    }

    public static Specification<PlanUpgradeRequest> accountIsActive() {
        return PlanUpgradeRequest::accountActive;
    }

    public static Specification<PlanUpgradeRequest> hasNoUnpaidInvoices() {
        return request -> request.unpaidInvoices() == 0;
    }

    public static Specification<PlanUpgradeRequest> targetPlanIsDifferent() {
        return request -> request.currentPlan() != request.targetPlan();
    }

    public static Specification<PlanUpgradeRequest> requestedSeatsFitTargetPlan() {
        return request -> request.requestedSeats() <= request.targetPlan().maxSeats();
    }
}
