package com.joaoscioli.patterns.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SubscriptionEventPublisher {
    private final List<SubscriptionEventObserver> observers = new ArrayList<>();

    public void subscribe(SubscriptionEventObserver observer) {
        observers.add(Objects.requireNonNull(observer, "observer must not be null"));
    }

    public void unsubscribe(SubscriptionEventObserver observer) {
        observers.remove(observer);
    }

    public void publish(SubscriptionEvent event) {
        Objects.requireNonNull(event, "event must not be null");

        observers.forEach(observer -> observer.onSubscriptionEvent(event));
    }

    public int observerCount() {
        return observers.size();
    }
}
