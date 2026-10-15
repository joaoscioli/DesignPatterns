package com.joaoscioli.patterns.state;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

/** A transition table is an executable contract when refactoring conditionals into State objects. */
class SubscriptionTransitionMatrixTest {
    @ParameterizedTest
    @CsvSource({
            "ACTIVE, FAILED, PAST_DUE",
            "ACTIVE, RECOVERED, ACTIVE",
            "ACTIVE, CANCELED, CANCELED",
            "PAST_DUE, FAILED, PAST_DUE",
            "PAST_DUE, RECOVERED, ACTIVE",
            "PAST_DUE, CANCELED, CANCELED",
            "PAST_DUE, GRACE_ENDED, EXPIRED",
            "CANCELED, CANCELED, CANCELED",
            "EXPIRED, GRACE_ENDED, EXPIRED"
    })
    void allowedTransitionProducesTheContractedState(
            SubscriptionStatus initial, Event event, SubscriptionStatus expected) {
        var lifecycle = new SubscriptionLifecycle(initial);

        apply(lifecycle, event);

        assertEquals(expected, lifecycle.status());
    }

    @ParameterizedTest
    @CsvSource({
            "ACTIVE, GRACE_ENDED, Active subscriptions are not in a grace period",
            "CANCELED, FAILED, Canceled subscriptions cannot become past due",
            "CANCELED, RECOVERED, Canceled subscriptions cannot recover payment",
            "CANCELED, GRACE_ENDED, Canceled subscriptions do not have a grace period",
            "EXPIRED, FAILED, Expired subscriptions cannot become past due",
            "EXPIRED, RECOVERED, Expired subscriptions cannot recover payment",
            "EXPIRED, CANCELED, Expired subscriptions cannot be canceled"
    })
    void forbiddenTransitionPreservesTheStateAndExplainsTheRejection(
            SubscriptionStatus initial, Event event, String message) {
        var lifecycle = new SubscriptionLifecycle(initial);

        var failure = assertThrowsExactly(IllegalStateException.class, () -> apply(lifecycle, event));

        assertEquals(message, failure.getMessage());
        assertEquals(initial, lifecycle.status());
    }

    private static void apply(SubscriptionLifecycle lifecycle, Event event) {
        switch (event) {
            case FAILED -> lifecycle.paymentFailed();
            case RECOVERED -> lifecycle.paymentRecovered();
            case CANCELED -> lifecycle.cancel();
            case GRACE_ENDED -> lifecycle.gracePeriodEnded();
        }
    }

    enum Event { FAILED, RECOVERED, CANCELED, GRACE_ENDED }
}
