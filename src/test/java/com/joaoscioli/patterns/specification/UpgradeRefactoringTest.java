package com.joaoscioli.patterns.specification;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UpgradeRefactoringTest {
    @ParameterizedTest
    @MethodSource("requests")
    void specificationsPreserveAllFailuresAndTheirOrderFromInlineConditionals(PlanUpgradeRequest request) {
        assertEquals(beforeRefactoring(request), PlanUpgradePolicy.standard().evaluate(request));
    }

    static Stream<PlanUpgradeRequest> requests() {
        // Every combination of the four rules, including the seat boundary and one above it.
        return Stream.of(true, false).flatMap(active -> Stream.of(0, 1).flatMap(unpaid ->
                Stream.of(Plan.STARTER, Plan.GROWTH).flatMap(target ->
                        Stream.of(target.maxSeats(), target.maxSeats() + 1).map(seats ->
                                new PlanUpgradeRequest(Plan.STARTER, target, active, unpaid, seats)))));
    }

    private UpgradeDecision beforeRefactoring(PlanUpgradeRequest request) {
        var failures = new ArrayList<String>();
        if (!request.accountActive()) {
            failures.add("account must be active");
        }
        if (request.unpaidInvoices() != 0) {
            failures.add("account must have no unpaid invoices");
        }
        if (request.currentPlan() == request.targetPlan()) {
            failures.add("target plan must be different");
        }
        if (request.requestedSeats() > request.targetPlan().maxSeats()) {
            failures.add("requested seats must fit target plan");
        }
        return new UpgradeDecision(failures.isEmpty(), failures);
    }
}
