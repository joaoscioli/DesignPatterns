package com.joaoscioli.patterns.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SubscriptionEventPublisher {
    private final List<SubscriptionEventObserver> observers = new ArrayList<>();

    public void subscribe(SubscriptionEventObserver observer) {
        Objects.requireNonNull(observer, "observer must not be null");
        // Repeated registration must not duplicate the observer's side effects.
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void unsubscribe(SubscriptionEventObserver observer) {
        observers.remove(observer);
    }

    public void publish(SubscriptionEvent event) {
        Objects.requireNonNull(event, "event must not be null");

        // Subscription changes during a callback apply to the next publication.
        List.copyOf(observers).forEach(observer -> observer.onSubscriptionEvent(event));
    }

    public int observerCount() {
        return observers.size();
    }
}
