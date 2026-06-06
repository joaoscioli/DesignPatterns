package com.joaoscioli.patterns.decorator;

public class TraceIdResponseRenderer extends ResponseRendererDecorator {
    private final String traceId;

    public TraceIdResponseRenderer(ResponseRenderer delegate, String traceId) {
        super(delegate);

        if (traceId == null || traceId.isBlank()) {
            throw new IllegalArgumentException("traceId must not be blank");
        }

        this.traceId = traceId;
    }

    @Override
    public ApiResponse render(String resourceName) {
        return delegate().render(resourceName)
                .withHeader("X-Trace-Id", traceId);
    }
}
