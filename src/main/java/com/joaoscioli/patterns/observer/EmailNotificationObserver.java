package com.joaoscioli.patterns.observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmailNotificationObserver implements SubscriptionEventObserver {
    private final List<String> sentMessages = new ArrayList<>();

    @Override
    public void onSubscriptionEvent(SubscriptionEvent event) {
        sentMessages.add("Subscription %s was %s".formatted(event.subscriptionId(), event.type().name().toLowerCase()));
    }

    public List<String> sentMessages() {
        return Collections.unmodifiableList(sentMessages);
    }
}
