package com.joaoscioli.patterns.factory;

public class WebhookNotifier implements Notifier {
    @Override
    public NotificationReceipt notify(String recipient, String message) {
        return new NotificationReceipt("webhook", recipient, message);
    }
}
