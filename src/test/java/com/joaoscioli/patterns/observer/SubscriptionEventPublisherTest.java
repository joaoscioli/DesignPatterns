package com.joaoscioli.patterns.observer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertSame;

class SubscriptionEventPublisherTest {
    @Test
    void observerFailureStopsDeliveryButDoesNotRemoveSubscriptions() {
        var publisher = new SubscriptionEventPublisher();
        var notifications = new ArrayList<String>();
        var failure = new IllegalStateException("email transport unavailable");
        SubscriptionEventObserver failing = event -> {
            notifications.add("email attempted");
            throw failure;
        };
        publisher.subscribe(failing);
        publisher.subscribe(event -> notifications.add("audit recorded"));
        var event = new SubscriptionEvent(SubscriptionEventType.CREATED, "acme", "sub-123");

        // This synchronous publisher is fail-fast; it has no retry or exception isolation.
        assertSame(failure, assertThrows(IllegalStateException.class, () -> publisher.publish(event)));
        assertEquals(List.of("email attempted"), notifications);
        assertEquals(2, publisher.observerCount());

        publisher.unsubscribe(failing);
        publisher.publish(event);

        assertEquals(List.of("email attempted", "audit recorded"), notifications);
        assertEquals(1, publisher.observerCount());
    }

    @Test
    void callbackSubscriptionChangesTakeEffectOnTheNextPublication() {
        var publisher = new SubscriptionEventPublisher();
        var notifications = new ArrayList<String>();
        var event = new SubscriptionEvent(SubscriptionEventType.CREATED, "acme", "sub-123");
        SubscriptionEventObserver late = received -> notifications.add("late");
        SubscriptionEventObserver removed = received -> notifications.add("removed");
        SubscriptionEventObserver changing = new SubscriptionEventObserver() {
            @Override
            public void onSubscriptionEvent(SubscriptionEvent received) {
                notifications.add("changing");
                publisher.unsubscribe(this);
                publisher.unsubscribe(removed);
                publisher.subscribe(late);
            }
        };
        publisher.subscribe(changing);
        publisher.subscribe(removed);

        publisher.publish(event);

        assertEquals(List.of("changing", "removed"), notifications);
        assertEquals(1, publisher.observerCount());

        publisher.publish(event);

        assertEquals(List.of("changing", "removed", "late"), notifications);
    }

    @Test
    void notifiesAllSubscribedObservers() {
        SubscriptionEventPublisher publisher = new SubscriptionEventPublisher();
        EmailNotificationObserver emailObserver = new EmailNotificationObserver();
        AuditLogObserver auditObserver = new AuditLogObserver();
        SubscriptionEvent event = new SubscriptionEvent(
                SubscriptionEventType.CREATED,
                "acme",
                "sub-123"
        );

        publisher.subscribe(emailObserver);
        publisher.subscribe(auditObserver);

        publisher.publish(event);

        assertAll(
                () -> assertEquals(2, publisher.observerCount()),
                () -> assertEquals("Subscription sub-123 was created", emailObserver.sentMessages().getFirst()),
                () -> assertEquals("CREATED:acme:sub-123", auditObserver.auditEntries().getFirst())
        );
    }

    @Test
    void stopsNotifyingUnsubscribedObserver() {
        SubscriptionEventPublisher publisher = new SubscriptionEventPublisher();
        EmailNotificationObserver emailObserver = new EmailNotificationObserver();
        SubscriptionEvent event = new SubscriptionEvent(
                SubscriptionEventType.CANCELED,
                "acme",
                "sub-123"
        );

        publisher.subscribe(emailObserver);
        publisher.unsubscribe(emailObserver);
        publisher.publish(event);

        assertAll(
                () -> assertEquals(0, publisher.observerCount()),
                () -> assertEquals(0, emailObserver.sentMessages().size())
        );
    }

    @Test
    void rejectsMissingObserver() {
        SubscriptionEventPublisher publisher = new SubscriptionEventPublisher();

        assertThrows(NullPointerException.class, () -> publisher.subscribe(null));
    }

    @Test
    void rejectsMissingEvent() {
        SubscriptionEventPublisher publisher = new SubscriptionEventPublisher();

        assertThrows(NullPointerException.class, () -> publisher.publish(null));
    }
}
