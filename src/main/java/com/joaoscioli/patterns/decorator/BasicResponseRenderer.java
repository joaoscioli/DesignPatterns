package com.joaoscioli.patterns.decorator;

import java.util.Map;

public class BasicResponseRenderer implements ResponseRenderer {
    @Override
    public ApiResponse render(String resourceName) {
        if (resourceName == null || resourceName.isBlank()) {
            throw new IllegalArgumentException("resourceName must not be blank");
        }

        return new ApiResponse(
                200,
                "{\"resource\":\"" + resourceName + "\"}",
                Map.of("Content-Type", "application/json")
        );
    }
}
