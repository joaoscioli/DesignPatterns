package com.joaoscioli.patterns.factory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NotifierFactoryTest {
    private final NotifierFactory factory = new NotifierFactory();

    @ParameterizedTest
    @MethodSource("notifierExamples")
    void createsNotifierForSelectedChannel(NotificationChannel channel, Class<?> expectedType, String expectedChannelName) {
        Notifier notifier = factory.create(channel);

        NotificationReceipt receipt = notifier.notify("user@example.com", "Welcome");

        assertAll(
                () -> assertInstanceOf(expectedType, notifier),
                () -> assertEquals(expectedChannelName, receipt.channel()),
                () -> assertEquals("user@example.com", receipt.recipient()),
                () -> assertEquals("Welcome", receipt.message())
        );
    }

    @Test
    void rejectsMissingChannel() {
        assertThrows(NullPointerException.class, () -> factory.create(null));
    }

    private static Stream<Arguments> notifierExamples() {
        return Stream.of(
                Arguments.of(NotificationChannel.EMAIL, EmailNotifier.class, "email"),
                Arguments.of(NotificationChannel.SMS, SmsNotifier.class, "sms"),
                Arguments.of(NotificationChannel.WEBHOOK, WebhookNotifier.class, "webhook")
        );
    }
}
