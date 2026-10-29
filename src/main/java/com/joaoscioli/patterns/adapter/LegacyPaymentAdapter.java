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

        if (response.approved() && (response.transactionCode() == null || response.transactionCode().isBlank())) {
            // An approval without a reference cannot safely confirm or retry a charge.
            throw new IllegalStateException("legacy provider approved payment without a transaction code");
        }

        return new PaymentResult(
                response.transactionCode(),
                response.approved(),
                response.providerMessage()
        );
    }
}
