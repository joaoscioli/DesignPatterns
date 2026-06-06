package com.joaoscioli.patterns.adapter;

public interface PaymentProcessor {
    PaymentResult charge(PaymentRequest request);
}
