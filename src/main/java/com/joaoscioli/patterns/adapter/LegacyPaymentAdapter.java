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

        if (response == null) {
            // An absent response leaves the payment outcome unknown; do not retry here.
            throw new IllegalStateException("legacy provider returned no payment response");
        }

        return new PaymentResult(
                response.transactionCode(),
                response.approved(),
                response.providerMessage()
        );
    }
}
