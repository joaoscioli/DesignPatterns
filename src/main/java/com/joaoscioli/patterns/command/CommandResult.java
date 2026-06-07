package com.joaoscioli.patterns.command;

public record CommandResult(
        String commandName,
        String message
) {
    public CommandResult {
        if (commandName == null || commandName.isBlank()) {
            throw new IllegalArgumentException("commandName must not be blank");
        }

        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("message must not be blank");
        }
    }
}
