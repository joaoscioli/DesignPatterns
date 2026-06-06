package com.joaoscioli.patterns.builder;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;

public record ApiRequest(
        String method,
        String url,
        Map<String, String> headers,
        String body,
        Duration timeout
) {
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String method = "GET";
        private String url;
        private Map<String, String> headers = Map.of();
        private String body = "";
        private Duration timeout = Duration.ofSeconds(5);

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers = Map.copyOf(headers);
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder timeout(Duration timeout) {
            this.timeout = timeout;
            return this;
        }

        public ApiRequest build() {
            validateRequiredFields();

            return new ApiRequest(
                    method.toUpperCase(),
                    url,
                    headers,
                    body,
                    timeout
            );
        }

        private void validateRequiredFields() {
            if (method == null || method.isBlank()) {
                throw new IllegalArgumentException("method must not be blank");
            }

            if (url == null || url.isBlank()) {
                throw new IllegalArgumentException("url must not be blank");
            }

            Objects.requireNonNull(headers, "headers must not be null");
            Objects.requireNonNull(body, "body must not be null");
            Objects.requireNonNull(timeout, "timeout must not be null");
        }
    }
}
