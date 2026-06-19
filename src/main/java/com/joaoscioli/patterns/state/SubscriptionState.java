package com.joaoscioli.patterns.state;

public interface SubscriptionState {
    SubscriptionStatus status();

    SubscriptionStatus onPaymentFailed();

    SubscriptionStatus onPaymentRecovered();

    SubscriptionStatus onCanceled();

    SubscriptionStatus onGracePeriodEnded();
}
