package com.joaoscioli.patterns.adapter;

public record LegacyPaymentResponse(String transactionCode, boolean approved, String providerMessage) {
}
