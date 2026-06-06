package com.joaoscioli.patterns.observer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SubscriptionEventPublisherTest {
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
