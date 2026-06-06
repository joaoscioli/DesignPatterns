package com.joaoscioli.patterns.strategy;

public class FixedAmountDiscountStrategy implements DiscountStrategy {
    private final long discountCents;

    public FixedAmountDiscountStrategy(long discountCents) {
        if (discountCents < 0) {
            throw new IllegalArgumentException("discountCents must not be negative");
        }

        this.discountCents = discountCents;
    }

    @Override
    public long applyTo(long subtotalCents) {
        return Math.max(0, subtotalCents - discountCents);
    }

    @Override
    public String description() {
        return "Fixed amount discount";
    }
}
