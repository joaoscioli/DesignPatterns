package com.joaoscioli.patterns.chain;

public record SubscriptionApprovalRequest(
        String customerId,
        boolean activeAccount,
        boolean availablePlan,
        boolean validPaymentMethod,
        int fraudRiskScore
) {
    public SubscriptionApprovalRequest {
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("customerId must not be blank");
        }

        if (fraudRiskScore < 0 || fraudRiskScore > 100) {
            throw new IllegalArgumentException("fraudRiskScore must be between 0 and 100");
        }
    }
}
