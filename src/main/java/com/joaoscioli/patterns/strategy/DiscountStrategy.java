package com.joaoscioli.patterns.strategy;

public interface DiscountStrategy {
    long applyTo(long subtotalCents);

    String description();
}
