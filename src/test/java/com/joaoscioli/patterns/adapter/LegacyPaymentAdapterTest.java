package com.joaoscioli.patterns.adapter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LegacyPaymentAdapterTest {
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
