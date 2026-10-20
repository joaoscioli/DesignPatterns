package com.joaoscioli.patterns.builder;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApiRequestTest {
    @Test
    void directConstructionDefensivelyCopiesHeadersAndNormalizesMethod() {
        var headers = new java.util.HashMap<>(Map.of("Accept", "application/json"));
        var request = new ApiRequest("post", "https://api.example.com", headers, "", Duration.ofNanos(1));
        headers.put("Accept", "text/plain");

        assertEquals("POST", request.method());
        assertEquals(Map.of("Accept", "application/json"), request.headers());
        assertThrows(UnsupportedOperationException.class, () -> request.headers().put("X-New", "value"));
    }

    @Test
    void constructorAndBuilderRejectInvalidTimeoutsAndRequiredFields() {
        for (var timeout : new Duration[]{Duration.ZERO, Duration.ofNanos(-1)}) {
            assertThrows(IllegalArgumentException.class,
                    () -> new ApiRequest("GET", "https://api.example.com", Map.of(), "", timeout));
            assertThrows(IllegalArgumentException.class,
                    () -> ApiRequest.builder().url("https://api.example.com").timeout(timeout).build());
        }
        assertThrows(IllegalArgumentException.class,
                () -> new ApiRequest(" ", "https://api.example.com", Map.of(), "", Duration.ofSeconds(1)));
        assertThrows(IllegalArgumentException.class,
                () -> new ApiRequest("GET", " ", Map.of(), "", Duration.ofSeconds(1)));
        assertThrows(NullPointerException.class,
                () -> new ApiRequest("GET", "https://api.example.com", null, "", Duration.ofSeconds(1)));
    }

    @Test
    void buildsReadableRequestWithExplicitFields() {
        ApiRequest request = ApiRequest.builder()
                .method("post")
                .url("https://api.example.com/subscriptions")
                .headers(Map.of("Authorization", "Bearer token"))
                .body("{\"plan\":\"starter\"}")
                .timeout(Duration.ofSeconds(10))
                .build();

        assertAll(
                () -> assertEquals("POST", request.method()),
                () -> assertEquals("https://api.example.com/subscriptions", request.url()),
                () -> assertEquals("Bearer token", request.headers().get("Authorization")),
                () -> assertEquals("{\"plan\":\"starter\"}", request.body()),
                () -> assertEquals(Duration.ofSeconds(10), request.timeout())
        );
    }

    @Test
    void appliesDefaultsForOptionalFields() {
        ApiRequest request = ApiRequest.builder()
                .url("https://api.example.com/health")
                .build();

        assertAll(
                () -> assertEquals("GET", request.method()),
                () -> assertEquals(Map.of(), request.headers()),
                () -> assertEquals("", request.body()),
                () -> assertEquals(Duration.ofSeconds(5), request.timeout())
        );
    }

    @Test
    void rejectsMissingUrl() {
        assertThrows(IllegalArgumentException.class, () -> ApiRequest.builder().build());
    }

    @Test
    void rejectsBlankMethod() {
        assertThrows(
                IllegalArgumentException.class,
                () -> ApiRequest.builder()
                        .method(" ")
                        .url("https://api.example.com/health")
                        .build()
        );
    }

    @Test
    void rejectsNullTimeout() {
        assertThrows(
                NullPointerException.class,
                () -> ApiRequest.builder()
                        .url("https://api.example.com/health")
                        .timeout(null)
                        .build()
        );
    }
}
