package com.joaoscioli.patterns.specification;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlanUpgradePolicyTest {

    private final PlanUpgradePolicy policy = PlanUpgradePolicy.standard();

    @Test
    void approvesEligibleUpgradeRequest() {
        var request = new PlanUpgradeRequest(
                Plan.STARTER,
                Plan.GROWTH,
                true,
                0,
                12
        );

        var decision = policy.evaluate(request);

        assertTrue(decision.approved());
        assertTrue(decision.failedRules().isEmpty());
    }

    @Test
    void rejectsUpgradeWhenMultipleSpecificationsFail() {
        var request = new PlanUpgradeRequest(
                Plan.STARTER,
                Plan.GROWTH,
                false,
                2,
                12
        );

        var decision = policy.evaluate(request);

        assertFalse(decision.approved());
        assertIterableEquals(List.of(
                "account must be active",
                "account must have no unpaid invoices"
        ), decision.failedRules());
    }

    @Test
    void rejectsUpgradeToSamePlan() {
        var request = new PlanUpgradeRequest(
                Plan.GROWTH,
                Plan.GROWTH,
                true,
                0,
                12
        );

        var decision = policy.evaluate(request);

        assertFalse(decision.approved());
        assertIterableEquals(List.of("target plan must be different"), decision.failedRules());
    }

    @Test
    void rejectsSeatCountAboveTargetPlanLimit() {
        var request = new PlanUpgradeRequest(
                Plan.STARTER,
                Plan.GROWTH,
                true,
                0,
                40
        );

        var decision = policy.evaluate(request);

        assertFalse(decision.approved());
        assertIterableEquals(List.of("requested seats must fit target plan"), decision.failedRules());
    }

    @Test
    void composesSpecificationsWithAndOrAndNot() {
        var request = new PlanUpgradeRequest(
                Plan.STARTER,
                Plan.ENTERPRISE,
                true,
                0,
                100
        );
        var activeAndPaid = PlanUpgradeSpecifications.accountIsActive()
                .and(PlanUpgradeSpecifications.hasNoUnpaidInvoices());
        var samePlan = PlanUpgradeSpecifications.targetPlanIsDifferent().not();
        var enterpriseOrSamePlan = ((Specification<PlanUpgradeRequest>) candidate -> candidate.targetPlan() == Plan.ENTERPRISE)
                .or(samePlan);

        assertTrue(activeAndPaid.isSatisfiedBy(request));
        assertTrue(enterpriseOrSamePlan.isSatisfiedBy(request));
    }

    @Test
    void rejectsInvalidRequestData() {
        var exception = assertThrows(IllegalArgumentException.class, () -> new PlanUpgradeRequest(
                Plan.STARTER,
                Plan.GROWTH,
                true,
                -1,
                10
        ));

        assertEquals("unpaidInvoices must not be negative", exception.getMessage());
    }
}
