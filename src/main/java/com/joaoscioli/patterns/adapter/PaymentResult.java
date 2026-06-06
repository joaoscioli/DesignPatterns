package com.joaoscioli.patterns.adapter;

public record PaymentResult(String paymentId, boolean approved, String message) {
}
