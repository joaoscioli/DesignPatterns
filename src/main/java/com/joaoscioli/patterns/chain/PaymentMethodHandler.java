package com.joaoscioli.patterns.chain;

public class PaymentMethodHandler extends ChainedApprovalHandler {
    public PaymentMethodHandler(ApprovalHandler next) {
        super(next);
    }

    @Override
    public ApprovalResult handle(SubscriptionApprovalRequest request) {
        if (!request.validPaymentMethod()) {
            return ApprovalResult.rejected("Payment method is invalid");
        }

        return next(request);
    }
}
