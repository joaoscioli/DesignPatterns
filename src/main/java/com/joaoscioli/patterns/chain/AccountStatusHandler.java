package com.joaoscioli.patterns.chain;

public class AccountStatusHandler extends ChainedApprovalHandler {
    public AccountStatusHandler(ApprovalHandler next) {
        super(next);
    }

    @Override
    public ApprovalResult handle(SubscriptionApprovalRequest request) {
        if (!request.activeAccount()) {
            return ApprovalResult.rejected("Customer account is inactive");
        }

        return next(request);
    }
}
