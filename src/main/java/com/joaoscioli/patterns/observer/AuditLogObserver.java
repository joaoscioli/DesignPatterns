package com.joaoscioli.patterns.observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AuditLogObserver implements SubscriptionEventObserver {
    private final List<String> auditEntries = new ArrayList<>();

    @Override
    public void onSubscriptionEvent(SubscriptionEvent event) {
        auditEntries.add("%s:%s:%s".formatted(event.type(), event.organizationSlug(), event.subscriptionId()));
    }

    public List<String> auditEntries() {
        return Collections.unmodifiableList(auditEntries);
    }
}
