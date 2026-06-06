package com.joaoscioli.patterns.decorator;

public interface ResponseRenderer {
    ApiResponse render(String resourceName);
}
