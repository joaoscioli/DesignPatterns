package com.joaoscioli.patterns.factory;

import java.util.Objects;

public class NotifierFactory {
    public Notifier create(NotificationChannel channel) {
        Objects.requireNonNull(channel, "channel must not be null");

        return switch (channel) {
            case EMAIL -> new EmailNotifier();
            case SMS -> new SmsNotifier();
            case WEBHOOK -> new WebhookNotifier();
        };
    }
}
