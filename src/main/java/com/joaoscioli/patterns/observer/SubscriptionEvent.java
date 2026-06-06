package com.joaoscioli.patterns.observer;

public record SubscriptionEvent(
        SubscriptionEventType type,
        String organizationSlug,
        String subscriptionId
) {
}
