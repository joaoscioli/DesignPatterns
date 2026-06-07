package com.joaoscioli.patterns.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BillingCommandDispatcher {
    private final List<BillingCommand> queue = new ArrayList<>();

    public void enqueue(BillingCommand command) {
        queue.add(Objects.requireNonNull(command, "command must not be null"));
    }

    public List<CommandResult> dispatchAll() {
        List<CommandResult> results = queue.stream()
                .map(BillingCommand::execute)
                .toList();

        queue.clear();

        return results;
    }

    public int queuedCommands() {
        return queue.size();
    }
}
