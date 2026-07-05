package com.joaoscioli.patterns.specification;

import java.util.List;

public class PlanUpgradePolicy {

    private final List<EligibilityRule> rules;

    public PlanUpgradePolicy(List<EligibilityRule> rules) {
        if (rules == null || rules.isEmpty()) {
            throw new IllegalArgumentException("rules must not be empty");
        }
        this.rules = List.copyOf(rules);
    }

    public static PlanUpgradePolicy standard() {
        return new PlanUpgradePolicy(List.of(
                new EligibilityRule("account must be active", PlanUpgradeSpecifications.accountIsActive()),
                new EligibilityRule("account must have no unpaid invoices", PlanUpgradeSpecifications.hasNoUnpaidInvoices()),
                new EligibilityRule("target plan must be different", PlanUpgradeSpecifications.targetPlanIsDifferent()),
                new EligibilityRule("requested seats must fit target plan", PlanUpgradeSpecifications.requestedSeatsFitTargetPlan())
        ));
    }

    public UpgradeDecision evaluate(PlanUpgradeRequest request) {
        var failedRules = rules.stream()
                .filter(rule -> !rule.isSatisfiedBy(request))
                .map(EligibilityRule::name)
                .toList();

        return new UpgradeDecision(failedRules.isEmpty(), failedRules);
    }
}
