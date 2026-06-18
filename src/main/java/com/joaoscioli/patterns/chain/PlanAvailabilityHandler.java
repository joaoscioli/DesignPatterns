package com.joaoscioli.patterns.chain;

public class PlanAvailabilityHandler extends ChainedApprovalHandler {
    public PlanAvailabilityHandler(ApprovalHandler next) {
        super(next);
    }

    @Override
    public ApprovalResult handle(SubscriptionApprovalRequest request) {
        if (!request.availablePlan()) {
            return ApprovalResult.rejected("Subscription plan is unavailable");
        }

        return next(request);
    }
}
