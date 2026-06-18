package com.joaoscioli.patterns.chain;

import java.util.Objects;

abstract class ChainedApprovalHandler implements ApprovalHandler {
    private final ApprovalHandler next;

    protected ChainedApprovalHandler(ApprovalHandler next) {
        this.next = Objects.requireNonNull(next, "next handler must not be null");
    }

    protected ApprovalResult next(SubscriptionApprovalRequest request) {
        return next.handle(request);
    }
}
