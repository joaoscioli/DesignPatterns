package com.joaoscioli.patterns.factory;

public class SmsNotifier implements Notifier {
    @Override
    public NotificationReceipt notify(String recipient, String message) {
        return new NotificationReceipt("sms", recipient, message);
    }
}
