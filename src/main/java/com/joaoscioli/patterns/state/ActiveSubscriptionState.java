package com.joaoscioli.patterns.state;

public class ActiveSubscriptionState implements SubscriptionState {
    @Override
    public SubscriptionStatus status() {
        return SubscriptionStatus.ACTIVE;
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
        throw new IllegalStateException("Active subscriptions are not in a grace period");
    }
}
