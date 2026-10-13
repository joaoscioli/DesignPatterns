package com.joaoscioli.patterns.adapter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LegacyPaymentAdapterTest {
    @Test
    void preservesProviderFailureWithoutRetryingOrFabricatingAResult() {
        var failure = new IllegalStateException("provider unavailable");
        var attempts = new java.util.concurrent.atomic.AtomicInteger();
        var client = new LegacyPaymentClient() {
            @Override
            public LegacyPaymentResponse makePayment(String account, long cents, String currency) {
                attempts.incrementAndGet();
                assertEquals("customer-123", account);
                assertEquals(12_500, cents);
                assertEquals("BRL", currency);
                throw failure;
            }
        };
        var processor = new LegacyPaymentAdapter(client);

        var actual = org.junit.jupiter.api.Assertions.assertThrowsExactly(IllegalStateException.class,
                () -> processor.charge(new PaymentRequest("customer-123", 12_500, "BRL")));

        org.junit.jupiter.api.Assertions.assertSame(failure, actual);
        assertEquals(1, attempts.get());
    }

    @Test
    void adaptsLegacyPaymentResponseToDomainResult() {
        PaymentProcessor processor = new LegacyPaymentAdapter(new LegacyPaymentClient());
        PaymentRequest request = new PaymentRequest("customer-123", 12_500, "BRL");

        PaymentResult result = processor.charge(request);

        assertAll(
                () -> assertEquals("legacy_customer-123_12500_BRL", result.paymentId()),
                () -> assertTrue(result.approved()),
                () -> assertEquals("Approved by legacy provider", result.message())
        );
    }

    @Test
    void preservesLegacyRejectionAsDomainResult() {
        PaymentProcessor processor = new LegacyPaymentAdapter(new LegacyPaymentClient());
        PaymentRequest request = new PaymentRequest("customer-123", 0, "BRL");

        PaymentResult result = processor.charge(request);

        assertAll(
                () -> assertEquals("legacy_rejected", result.paymentId()),
                () -> assertFalse(result.approved()),
                () -> assertEquals("Amount must be positive", result.message())
        );
    }

    @Test
    void rejectsMissingLegacyClient() {
        assertThrows(NullPointerException.class, () -> new LegacyPaymentAdapter(null));
    }

    @Test
    void rejectsMissingPaymentRequest() {
        PaymentProcessor processor = new LegacyPaymentAdapter(new LegacyPaymentClient());

        assertThrows(NullPointerException.class, () -> processor.charge(null));
    }
}
