package com.joaoscioli.patterns.decorator;

public class CacheControlResponseRenderer extends ResponseRendererDecorator {
    private final String policy;

    public CacheControlResponseRenderer(ResponseRenderer delegate, String policy) {
        super(delegate);

        if (policy == null || policy.isBlank()) {
            throw new IllegalArgumentException("policy must not be blank");
        }

        this.policy = policy;
    }

    @Override
    public ApiResponse render(String resourceName) {
        return delegate().render(resourceName)
                .withHeader("Cache-Control", policy);
    }
}
