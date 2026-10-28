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
    void composedRulesPropagateTheOriginalFailureAndCanEvaluateALaterCandidate() {
        var calls = new ArrayList<String>();
        var failure = new IllegalStateException("eligibility source unavailable");
        Specification<String> available = candidate -> {
            calls.add("available:" + candidate);
            if (candidate.equals("offline")) throw failure;
            return true;
        };
        Specification<String> eligible = candidate -> { calls.add("eligible:" + candidate); return true; };
        var policy = available.and(eligible);
        assertTrue(calls.isEmpty());

        assertSame(failure, assertThrowsExactly(IllegalStateException.class,
                () -> policy.isSatisfiedBy("offline")));
        assertEquals(List.of("available:offline"), calls);

        assertTrue(policy.isSatisfiedBy("healthy"));
        assertEquals(List.of("available:offline", "available:healthy", "eligible:healthy"), calls);
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
