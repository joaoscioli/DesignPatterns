package com.joaoscioli.patterns.state;

public class SubscriptionLifecycle {
    private SubscriptionStatus status;

    public SubscriptionLifecycle(SubscriptionStatus status) {
        this.status = status;
    }

    public SubscriptionStatus status() {
        return status;
    }

    public void paymentFailed() {
        status = state().onPaymentFailed();
    }

    public void paymentRecovered() {
        status = state().onPaymentRecovered();
    }

    public void cancel() {
        status = state().onCanceled();
    }

    public void gracePeriodEnded() {
        status = state().onGracePeriodEnded();
    }

    private SubscriptionState state() {
        return switch (status) {
            case ACTIVE -> new ActiveSubscriptionState();
            case PAST_DUE -> new PastDueSubscriptionState();
            case CANCELED -> new CanceledSubscriptionState();
            case EXPIRED -> new ExpiredSubscriptionState();
        };
    }
}
