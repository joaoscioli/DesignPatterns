package com.joaoscioli.patterns.state;

public class CanceledSubscriptionState implements SubscriptionState {
    @Override
    public SubscriptionStatus status() {
        return SubscriptionStatus.CANCELED;
    }

    @Override
    public SubscriptionStatus onPaymentFailed() {
        throw new IllegalStateException("Canceled subscriptions cannot become past due");
    }

    @Override
    public SubscriptionStatus onPaymentRecovered() {
        throw new IllegalStateException("Canceled subscriptions cannot recover payment");
    }

    @Override
    public SubscriptionStatus onCanceled() {
        return SubscriptionStatus.CANCELED;
    }

    @Override
    public SubscriptionStatus onGracePeriodEnded() {
        throw new IllegalStateException("Canceled subscriptions do not have a grace period");
    }
}
