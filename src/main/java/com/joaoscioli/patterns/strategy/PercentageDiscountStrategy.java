package com.joaoscioli.patterns.strategy;

public class PercentageDiscountStrategy implements DiscountStrategy {
    private final int percentage;

    public PercentageDiscountStrategy(int percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("percentage must be between 0 and 100");
        }

        this.percentage = percentage;
    }

    @Override
    public long applyTo(long subtotalCents) {
        return subtotalCents - (subtotalCents * percentage / 100);
    }

    @Override
    public String description() {
        return "%d%% discount".formatted(percentage);
    }
}
