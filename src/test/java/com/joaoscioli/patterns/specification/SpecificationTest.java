package com.joaoscioli.patterns.specification;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SpecificationTest {
    @Test
    void rejectsMissingOperandsAtCompositionEvenWhenEvaluationWouldShortCircuit() {
        Specification<String> denied = candidate -> false;
        Specification<String> allowed = candidate -> true;
        assertThrowsExactly(NullPointerException.class, () -> denied.and(null));
        assertThrowsExactly(NullPointerException.class, () -> allowed.or(null));
    }

    @Test
    void compositionIsLazyAndShortCircuitsInLeftToRightOrder() {
        var calls = new ArrayList<String>();
        Specification<String> left = candidate -> { calls.add("left:" + candidate); return candidate.equals("yes"); };
        Specification<String> right = candidate -> { calls.add("right:" + candidate); return true; };
        var both = left.and(right);
        var either = left.or(right);
        assertTrue(calls.isEmpty());
        assertFalse(both.isSatisfiedBy("no"));
        assertEquals(List.of("left:no"), calls);
        calls.clear();
        assertTrue(either.isSatisfiedBy("yes"));
        assertEquals(List.of("left:yes"), calls);
        calls.clear();
        assertTrue(both.isSatisfiedBy("yes"));
        assertEquals(List.of("left:yes", "right:yes"), calls);
        calls.clear();
        assertTrue(either.isSatisfiedBy("no"));
        assertEquals(List.of("left:no", "right:no"), calls);
        calls.clear();
        assertFalse(left.not().isSatisfiedBy("yes"));
        assertEquals(List.of("left:yes"), calls);
    }
}
