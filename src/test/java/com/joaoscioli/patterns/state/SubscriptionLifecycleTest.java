package com.joaoscioli.patterns.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SubscriptionLifecycleTest {
    @Test
    void movesActiveSubscriptionToPastDueWhenPaymentFails() {
        SubscriptionLifecycle lifecycle = new SubscriptionLifecycle(SubscriptionStatus.ACTIVE);

        lifecycle.paymentFailed();

        assertEquals(SubscriptionStatus.PAST_DUE, lifecycle.status());
    }

    @Test
    void recoversPastDueSubscriptionWhenPaymentSucceeds() {
        SubscriptionLifecycle lifecycle = new SubscriptionLifecycle(SubscriptionStatus.PAST_DUE);

        lifecycle.paymentRecovered();

        assertEquals(SubscriptionStatus.ACTIVE, lifecycle.status());
    }

    @Test
    void expiresPastDueSubscriptionWhenGracePeriodEnds() {
        SubscriptionLifecycle lifecycle = new SubscriptionLifecycle(SubscriptionStatus.PAST_DUE);

        lifecycle.gracePeriodEnded();

        assertEquals(SubscriptionStatus.EXPIRED, lifecycle.status());
    }

    @Test
    void rejectsPaymentRecoveryForCanceledSubscription() {
        SubscriptionLifecycle lifecycle = new SubscriptionLifecycle(SubscriptionStatus.CANCELED);

        assertThrows(IllegalStateException.class, lifecycle::paymentRecovered);
    }
}
