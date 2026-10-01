package com.joaoscioli.patterns.strategy;

import java.util.Objects;

public class CheckoutService {
    public CheckoutResult checkout(long subtotalCents, DiscountStrategy discountStrategy) {
        if (subtotalCents <= 0) {
            throw new IllegalArgumentException("subtotalCents must be greater than zero");
        }

        Objects.requireNonNull(discountStrategy, "discountStrategy must not be null");

        long finalAmountCents = discountStrategy.applyTo(subtotalCents);

        if (finalAmountCents < 0 || finalAmountCents > subtotalCents) {
            throw new IllegalStateException("discount strategy must return an amount between zero and subtotalCents");
        }

        return new CheckoutResult(subtotalCents, finalAmountCents, discountStrategy.description());
    }
}
