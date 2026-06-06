package com.joaoscioli.patterns.adapter;

import java.util.Objects;

public class LegacyPaymentAdapter implements PaymentProcessor {
    private final LegacyPaymentClient legacyPaymentClient;

    public LegacyPaymentAdapter(LegacyPaymentClient legacyPaymentClient) {
        this.legacyPaymentClient = Objects.requireNonNull(legacyPaymentClient);
    }

    @Override
    public PaymentResult charge(PaymentRequest request) {
        Objects.requireNonNull(request, "request must not be null");

        LegacyPaymentResponse response = legacyPaymentClient.makePayment(
                request.customerId(),
                request.amountCents(),
                request.currency()
        );

        return new PaymentResult(
                response.transactionCode(),
                response.approved(),
                response.providerMessage()
        );
    }
}
