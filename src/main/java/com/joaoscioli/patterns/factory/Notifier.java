package com.joaoscioli.patterns.factory;

public interface Notifier {
    NotificationReceipt notify(String recipient, String message);
}
