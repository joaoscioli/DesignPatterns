package com.joaoscioli.patterns.strategy;

public record CheckoutResult(long subtotalCents, long finalAmountCents, String discountDescription) {
}
