package com.joaoscioli.patterns.adapter;

public record PaymentRequest(String customerId, long amountCents, String currency) {
}
