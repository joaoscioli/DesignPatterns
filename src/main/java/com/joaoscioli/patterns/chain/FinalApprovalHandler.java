package com.joaoscioli.patterns.chain;

public class FinalApprovalHandler implements ApprovalHandler {
    @Override
    public ApprovalResult handle(SubscriptionApprovalRequest request) {
        return ApprovalResult.success();
    }
}
