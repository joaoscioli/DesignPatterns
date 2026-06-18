package com.joaoscioli.patterns.chain;

public class FraudRiskHandler extends ChainedApprovalHandler {
    public FraudRiskHandler(ApprovalHandler next) {
        super(next);
    }

    @Override
    public ApprovalResult handle(SubscriptionApprovalRequest request) {
        if (request.fraudRiskScore() >= 80) {
            return ApprovalResult.rejected("Fraud risk is too high");
        }

        return next(request);
    }
}
