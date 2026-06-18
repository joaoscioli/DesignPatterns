package com.joaoscioli.patterns.chain;

public interface ApprovalHandler {
    ApprovalResult handle(SubscriptionApprovalRequest request);
}
