package com.joaoscioli.patterns.specification;

import java.util.List;

public record UpgradeDecision(boolean approved, List<String> failedRules) {

    public UpgradeDecision {
        failedRules = List.copyOf(failedRules);
    }
}
