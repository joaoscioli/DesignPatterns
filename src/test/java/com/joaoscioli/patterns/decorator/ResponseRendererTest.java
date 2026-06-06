package com.joaoscioli.patterns.decorator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResponseRendererTest {
    @Test
    void rendersBasicJsonResponse() {
        ResponseRenderer renderer = new BasicResponseRenderer();

        ApiResponse response = renderer.render("subscriptions");

        assertAll(
                () -> assertEquals(200, response.statusCode()),
                () -> assertEquals("{\"resource\":\"subscriptions\"}", response.body()),
                () -> assertEquals("application/json", response.headers().get("Content-Type"))
        );
    }

    @Test
    void decoratorsAddHeadersWithoutChangingBaseBody() {
        ResponseRenderer renderer = new CacheControlResponseRenderer(
                new TraceIdResponseRenderer(new BasicResponseRenderer(), "trace-123"),
                "no-store"
        );

        ApiResponse response = renderer.render("invoices");

        assertAll(
                () -> assertEquals("{\"resource\":\"invoices\"}", response.body()),
                () -> assertEquals("application/json", response.headers().get("Content-Type")),
                () -> assertEquals("trace-123", response.headers().get("X-Trace-Id")),
                () -> assertEquals("no-store", response.headers().get("Cache-Control"))
        );
    }

    @Test
    void rejectsMissingDelegate() {
        assertThrows(NullPointerException.class, () -> new TraceIdResponseRenderer(null, "trace-123"));
    }

    @Test
    void rejectsBlankResourceName() {
        ResponseRenderer renderer = new BasicResponseRenderer();

        assertThrows(IllegalArgumentException.class, () -> renderer.render(" "));
    }
}
