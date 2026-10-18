package com.joaoscioli.patterns.observer;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObserverRefactoringTest {
    @ParameterizedTest
    @EnumSource(SubscriptionEventType.class)
    void observerRefactoringPreservesDirectNotificationContentAndOrder(SubscriptionEventType type) {
        var event = new SubscriptionEvent(type, "acme", "sub-123");
        var before = new ArrayList<String>();
        notifyDirectly(event, before);

        var after = new ArrayList<String>();
        var email = new EmailNotificationObserver();
        var audit = new AuditLogObserver();
        var publisher = new SubscriptionEventPublisher();
        publisher.subscribe(received -> {
            email.onSubscriptionEvent(received);
            after.add(email.sentMessages().getLast());
        });
        publisher.subscribe(received -> {
            audit.onSubscriptionEvent(received);
            after.add(audit.auditEntries().getLast());
        });

        publisher.publish(event);

        var expected = type == SubscriptionEventType.CREATED
                ? List.of("Subscription sub-123 was created", "CREATED:acme:sub-123")
                : List.of("Subscription sub-123 was canceled", "CANCELED:acme:sub-123");
        assertEquals(expected, before);
        assertEquals(before, after);
    }

    // Before refactoring: the workflow knows each concrete notification directly.
    private void notifyDirectly(SubscriptionEvent event, List<String> effects) {
        String action = event.type() == SubscriptionEventType.CREATED ? "created" : "canceled";
        effects.add("Subscription " + event.subscriptionId() + " was " + action);
        effects.add(event.type() + ":" + event.organizationSlug() + ":" + event.subscriptionId());
    }
}
