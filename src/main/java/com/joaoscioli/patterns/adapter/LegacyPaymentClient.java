package com.joaoscioli.patterns.adapter;

public class LegacyPaymentClient {
    public LegacyPaymentResponse makePayment(String accountReference, long amountInCents, String isoCurrency) {
        if (amountInCents <= 0) {
            return new LegacyPaymentResponse("legacy_rejected", false, "Amount must be positive");
        }

        String transactionCode = "legacy_%s_%d_%s".formatted(accountReference, amountInCents, isoCurrency);

        return new LegacyPaymentResponse(transactionCode, true, "Approved by legacy provider");
    }
}
