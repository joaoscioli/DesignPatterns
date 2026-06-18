package com.joaoscioli.patterns.chain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SubscriptionApprovalChainTest {
    private final SubscriptionApprovalChain chain = new SubscriptionApprovalChain();

    @Test
    void approvesSubscriptionWhenAllHandlersPass() {
        ApprovalResult result = chain.approve(validRequest());

        assertAll(
                () -> assertTrue(result.approved()),
                () -> assertEquals("Subscription approved", result.reason())
        );
    }

    @Test
    void rejectsInactiveCustomerBeforeCheckingLaterRules() {
        ApprovalResult result = chain.approve(new SubscriptionApprovalRequest("cus-123", false, true, true, 10));

        assertAll(
                () -> assertFalse(result.approved()),
                () -> assertEquals("Customer account is inactive", result.reason())
        );
    }

    @Test
    void rejectsUnavailablePlan() {
        ApprovalResult result = chain.approve(new SubscriptionApprovalRequest("cus-123", true, false, true, 10));

        assertEquals("Subscription plan is unavailable", result.reason());
    }

    @Test
    void rejectsInvalidPaymentMethod() {
        ApprovalResult result = chain.approve(new SubscriptionApprovalRequest("cus-123", true, true, false, 10));

        assertEquals("Payment method is invalid", result.reason());
    }

    @Test
    void rejectsHighFraudRisk() {
        ApprovalResult result = chain.approve(new SubscriptionApprovalRequest("cus-123", true, true, true, 95));

        assertEquals("Fraud risk is too high", result.reason());
    }

    @Test
    void rejectsInvalidRiskScore() {
        assertThrows(IllegalArgumentException.class,
                () -> new SubscriptionApprovalRequest("cus-123", true, true, true, 101));
    }

    private SubscriptionApprovalRequest validRequest() {
        return new SubscriptionApprovalRequest("cus-123", true, true, true, 20);
    }
}
