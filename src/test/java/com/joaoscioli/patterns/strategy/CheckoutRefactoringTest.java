package com.joaoscioli.patterns.strategy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Characterization tests preserve checkout behavior while replacing a switch with Strategy. */
class CheckoutRefactoringTest {
    @ParameterizedTest
    @MethodSource("checkouts")
    void strategyRefactoringPreservesTheCompleteCheckoutResult(
            long subtotal, String kind, int value, DiscountStrategy strategy, long expectedAmount) {
        CheckoutResult before = legacyCheckout(subtotal, kind, value);
        CheckoutResult after = new CheckoutService().checkout(subtotal, strategy);

        assertEquals(expectedAmount, before.finalAmountCents());
        assertEquals(before, after);
    }

    // Before: every new discount type requires changing the central checkout switch.
    // Kept as a test fixture so the production path uses only the refactored design.
    private CheckoutResult legacyCheckout(long subtotal, String kind, int value) {
        return switch (kind) {
            case "none" -> new CheckoutResult(subtotal, subtotal, "No discount");
            case "percentage" -> new CheckoutResult(subtotal,
                    subtotal - subtotal * value / 100, value + "% discount");
            case "fixed" -> new CheckoutResult(subtotal,
                    Math.max(0, subtotal - value), "Fixed amount discount");
            default -> throw new IllegalArgumentException("unknown discount type");
        };
    }

    private static Stream<Arguments> checkouts() {
        return Stream.of(
                Arguments.of(10_000L, "none", 0, new NoDiscountStrategy(), 10_000L),
                Arguments.of(10_000L, "percentage", 15, new PercentageDiscountStrategy(15), 8_500L),
                Arguments.of(101L, "percentage", 15, new PercentageDiscountStrategy(15), 86L),
                Arguments.of(101L, "percentage", 100, new PercentageDiscountStrategy(100), 0L),
                Arguments.of(10_000L, "fixed", 2_500, new FixedAmountDiscountStrategy(2_500), 7_500L),
                Arguments.of(1_000L, "fixed", 2_500, new FixedAmountDiscountStrategy(2_500), 0L)
        );
    }
}
