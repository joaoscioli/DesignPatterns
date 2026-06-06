package com.joaoscioli.patterns.decorator;

import java.util.Objects;

public abstract class ResponseRendererDecorator implements ResponseRenderer {
    private final ResponseRenderer delegate;

    protected ResponseRendererDecorator(ResponseRenderer delegate) {
        this.delegate = Objects.requireNonNull(delegate, "delegate must not be null");
    }

    protected ResponseRenderer delegate() {
        return delegate;
    }
}
