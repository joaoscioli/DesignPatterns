package com.joaoscioli.patterns.strategy;

public class NoDiscountStrategy implements DiscountStrategy {
    @Override
    public long applyTo(long subtotalCents) {
        return subtotalCents;
    }

    @Override
    public String description() {
        return "No discount";
    }
}
