package com.joaoscioli.patterns.state;

public class ExpiredSubscriptionState implements SubscriptionState {
    @Override
    public SubscriptionStatus status() {
        return SubscriptionStatus.EXPIRED;
    }

    @Override
    public SubscriptionStatus onPaymentFailed() {
        throw new IllegalStateException("Expired subscriptions cannot become past due");
    }

    @Override
    public SubscriptionStatus onPaymentRecovered() {
        throw new IllegalStateException("Expired subscriptions cannot recover payment");
    }

    @Override
    public SubscriptionStatus onCanceled() {
        throw new IllegalStateException("Expired subscriptions cannot be canceled");
    }

    @Override
    public SubscriptionStatus onGracePeriodEnded() {
        return SubscriptionStatus.EXPIRED;
    }
}
