package com.joaoscioli.patterns.factory;

public class EmailNotifier implements Notifier {
    @Override
    public NotificationReceipt notify(String recipient, String message) {
        return new NotificationReceipt("email", recipient, message);
    }
}
