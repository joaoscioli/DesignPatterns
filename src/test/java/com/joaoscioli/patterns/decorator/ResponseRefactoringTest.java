package com.joaoscioli.patterns.decorator;

import java.util.Map;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponseRefactoringTest {
    @ParameterizedTest
    @ValueSource(strings = {"subscriptions", "invoices", "plans"})
    void extractingOptionalHeadersIntoDecoratorsPreservesTheCompleteResponse(String resource) {
        // Before: the caller mixes resource rendering with cross-cutting headers.
        var before = new ApiResponse(200, "{\"resource\":\"" + resource + "\"}", Map.of(
                "Content-Type", "application/json",
                "Cache-Control", "no-store",
                "X-Trace-Id", "trace-123"));

        ResponseRenderer after = new CacheControlResponseRenderer(
                new TraceIdResponseRenderer(new BasicResponseRenderer(), "trace-123"), "no-store");
        ResponseRenderer reversed = new TraceIdResponseRenderer(
                new CacheControlResponseRenderer(new BasicResponseRenderer(), "no-store"), "trace-123");

        assertEquals(before, after.render(resource));
        // Distinct headers compose in either order without changing the contract.
        assertEquals(before, reversed.render(resource));
    }
}
