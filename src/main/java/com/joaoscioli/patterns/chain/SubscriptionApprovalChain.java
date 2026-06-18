package com.joaoscioli.patterns.chain;

public class SubscriptionApprovalChain {
    private final ApprovalHandler firstHandler;

    public SubscriptionApprovalChain() {
        this.firstHandler = new AccountStatusHandler(
                new PlanAvailabilityHandler(
                        new PaymentMethodHandler(
                                new FraudRiskHandler(
                                        new FinalApprovalHandler()
                                )
                        )
                )
        );
    }

    public ApprovalResult approve(SubscriptionApprovalRequest request) {
        return firstHandler.handle(request);
    }
}
