package com.joaoscioli.patterns.state;

public class PastDueSubscriptionState implements SubscriptionState {
    @Override
    public SubscriptionStatus status() {
        return SubscriptionStatus.PAST_DUE;
    }

    @Override
    public SubscriptionStatus onPaymentFailed() {
        return SubscriptionStatus.PAST_DUE;
    }

    @Override
    public SubscriptionStatus onPaymentRecovered() {
        return SubscriptionStatus.ACTIVE;
    }

    @Override
    public SubscriptionStatus onCanceled() {
        return SubscriptionStatus.CANCELED;
    }

    @Override
    public SubscriptionStatus onGracePeriodEnded() {
        return SubscriptionStatus.EXPIRED;
    }
}
