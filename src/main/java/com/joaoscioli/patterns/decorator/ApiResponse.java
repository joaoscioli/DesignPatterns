package com.joaoscioli.patterns.decorator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public record ApiResponse(
        int statusCode,
        String body,
        Map<String, String> headers
) {
    public ApiResponse {
        if (statusCode < 100 || statusCode > 599) {
            throw new IllegalArgumentException("statusCode must be a valid HTTP status code");
        }

        Objects.requireNonNull(body, "body must not be null");
        headers = Map.copyOf(Objects.requireNonNull(headers, "headers must not be null"));
    }

    public ApiResponse withHeader(String name, String value) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }

        Objects.requireNonNull(value, "value must not be null");

        Map<String, String> updatedHeaders = new LinkedHashMap<>(headers);
        updatedHeaders.put(name, value);

        return new ApiResponse(statusCode, body, updatedHeaders);
    }
}
