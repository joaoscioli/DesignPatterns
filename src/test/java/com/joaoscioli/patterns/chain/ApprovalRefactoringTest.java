package com.joaoscioli.patterns.chain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApprovalRefactoringTest {
    @ParameterizedTest
    @CsvSource({
            "false, false, false, 100, false, Customer account is inactive",
            "true, false, false, 100, false, Subscription plan is unavailable",
            "true, true, false, 100, false, Payment method is invalid",
            "true, true, true, 80, false, Fraud risk is too high",
            "true, true, true, 79, true, Subscription approved",
            "true, true, true, 0, true, Subscription approved"
    })
    void chainPreservesLegacyRulePrecedenceAndRiskBoundary(
            boolean active, boolean available, boolean validPayment, int risk,
            boolean approved, String reason) {
        var request = new SubscriptionApprovalRequest("cus-123", active, available, validPayment, risk);
        var expected = new ApprovalResult(approved, reason);

        assertEquals(expected, legacyApprove(request));
        assertEquals(expected, new SubscriptionApprovalChain().approve(request));
    }

    // Before refactoring: ordered conditionals, retained only as a characterization fixture.
    private ApprovalResult legacyApprove(SubscriptionApprovalRequest request) {
        if (!request.activeAccount()) {
            return ApprovalResult.rejected("Customer account is inactive");
        }
        if (!request.availablePlan()) {
            return ApprovalResult.rejected("Subscription plan is unavailable");
        }
        if (!request.validPaymentMethod()) {
            return ApprovalResult.rejected("Payment method is invalid");
        }
        if (request.fraudRiskScore() >= 80) {
            return ApprovalResult.rejected("Fraud risk is too high");
        }
        return ApprovalResult.success();
    }
}
