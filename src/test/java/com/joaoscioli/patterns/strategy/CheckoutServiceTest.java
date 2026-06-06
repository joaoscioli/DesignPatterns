package com.joaoscioli.patterns.strategy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CheckoutServiceTest {
    private final CheckoutService checkoutService = new CheckoutService();

    @ParameterizedTest
    @MethodSource("discountStrategies")
    void appliesSelectedDiscountStrategy(DiscountStrategy strategy, long expectedFinalAmountCents) {
        CheckoutResult result = checkoutService.checkout(10_000, strategy);

        assertEquals(expectedFinalAmountCents, result.finalAmountCents());
    }

    @Test
    void keepsCheckoutResultReadable() {
        CheckoutResult result = checkoutService.checkout(10_000, new PercentageDiscountStrategy(15));

        assertAll(
                () -> assertEquals(10_000, result.subtotalCents()),
                () -> assertEquals(8_500, result.finalAmountCents()),
                () -> assertEquals("15% discount", result.discountDescription())
        );
    }

    @Test
    void fixedAmountDiscountNeverReturnsNegativeFinalAmount() {
        CheckoutResult result = checkoutService.checkout(5_000, new FixedAmountDiscountStrategy(10_000));

        assertEquals(0, result.finalAmountCents());
    }

    @ParameterizedTest
    @ValueSource(longs = {0, -1, -1000})
    void rejectsInvalidSubtotal(long subtotalCents) {
        assertThrows(
                IllegalArgumentException.class,
                () -> checkoutService.checkout(subtotalCents, new NoDiscountStrategy())
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 101})
    void rejectsInvalidPercentageDiscounts(int percentage) {
        assertThrows(IllegalArgumentException.class, () -> new PercentageDiscountStrategy(percentage));
    }

    @Test
    void rejectsMissingStrategy() {
        assertThrows(NullPointerException.class, () -> checkoutService.checkout(10_000, null));
    }

    private static Stream<Arguments> discountStrategies() {
        return Stream.of(
                Arguments.of(new NoDiscountStrategy(), 10_000),
                Arguments.of(new PercentageDiscountStrategy(10), 9_000),
                Arguments.of(new FixedAmountDiscountStrategy(2_500), 7_500)
        );
    }
}
