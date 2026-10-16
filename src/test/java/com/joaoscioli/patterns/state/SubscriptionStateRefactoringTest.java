package com.joaoscioli.patterns.state;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubscriptionStateRefactoringTest {
    @ParameterizedTest
    @CsvSource({
            "FAILED RECOVERED FAILED GRACE_ENDED, EXPIRED",
            "FAILED FAILED RECOVERED RECOVERED, ACTIVE",
            "FAILED RECOVERED CANCELED CANCELED, CANCELED",
            "FAILED GRACE_ENDED GRACE_ENDED, EXPIRED"
    })
    void extractingStateObjectsPreservesMultiEventJourneys(String journey, SubscriptionStatus expected) {
        var before = new ConditionalLifecycle();
        var after = new SubscriptionLifecycle(SubscriptionStatus.ACTIVE);

        for (var event : journey.split(" ")) {
            before.apply(event);
            switch (event) {
                case "FAILED" -> after.paymentFailed();
                case "RECOVERED" -> after.paymentRecovered();
                case "CANCELED" -> after.cancel();
                case "GRACE_ENDED" -> after.gracePeriodEnded();
                default -> throw new IllegalArgumentException(event);
            }
            assertEquals(before.status, after.status(), "State after " + event + " in " + journey);
        }
        assertEquals(expected, before.status);
        assertEquals(expected, after.status());
    }

    // Before: event handling and status-dependent rules accumulate in one class.
    // Rejection messages and the full single-event matrix are covered separately.
    private static class ConditionalLifecycle {
        private SubscriptionStatus status = SubscriptionStatus.ACTIVE;

        void apply(String event) {
            if (event.equals("CANCELED") && status != SubscriptionStatus.EXPIRED) {
                status = SubscriptionStatus.CANCELED;
            } else if (event.equals("GRACE_ENDED") &&
                    (status == SubscriptionStatus.PAST_DUE || status == SubscriptionStatus.EXPIRED)) {
                status = SubscriptionStatus.EXPIRED;
            } else if (event.equals("FAILED") &&
                    (status == SubscriptionStatus.ACTIVE || status == SubscriptionStatus.PAST_DUE)) {
                status = SubscriptionStatus.PAST_DUE;
            } else if (event.equals("RECOVERED") &&
                    (status == SubscriptionStatus.ACTIVE || status == SubscriptionStatus.PAST_DUE)) {
                status = SubscriptionStatus.ACTIVE;
            } else {
                throw new IllegalStateException("Invalid transition");
            }
        }
    }
}
