package com.joaoscioli.patterns.strategy;

public interface DiscountStrategy {
    /** Returns the final amount in cents, between zero and the supplied subtotal (inclusive). */
    long applyTo(long subtotalCents);

    String description();
}
