package com.joaoscioli.patterns.adapter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentAdapterRefactoringTest {
    @ParameterizedTest
    @CsvSource({
            "customer-123, 1, BRL, legacy_customer-123_1_BRL, true, Approved by legacy provider",
            "customer-456, 9223372036854775807, USD, legacy_customer-456_9223372036854775807_USD, true, Approved by legacy provider",
            "customer-789, 0, EUR, legacy_rejected, false, Amount must be positive",
            "customer-789, -1, EUR, legacy_rejected, false, Amount must be positive"
    })
    void extractingTheAdapterPreservesInlineLegacyMapping(
            String customer, long cents, String currency, String id, boolean approved, String message) {
        var request = new PaymentRequest(customer, cents, currency);
        var client = new LegacyPaymentClient();
        var expected = new PaymentResult(id, approved, message);

        assertEquals(expected, chargeBeforeRefactoring(client, request));
        assertEquals(expected, new LegacyPaymentAdapter(client).charge(request));
    }

    // Before: each checkout caller depends directly on the legacy response shape.
    private PaymentResult chargeBeforeRefactoring(LegacyPaymentClient client, PaymentRequest request) {
        var response = client.makePayment(request.customerId(), request.amountCents(), request.currency());
        return new PaymentResult(response.transactionCode(), response.approved(), response.providerMessage());
    }
}
