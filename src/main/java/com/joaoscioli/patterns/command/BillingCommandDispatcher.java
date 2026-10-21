package com.joaoscioli.patterns.command;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

public class BillingCommandDispatcher {
    private final Deque<BillingCommand> queue = new ArrayDeque<>();

    public void enqueue(BillingCommand command) {
        queue.add(Objects.requireNonNull(command, "command must not be null"));
    }

    public List<CommandResult> dispatchAll() {
        List<CommandResult> results = new ArrayList<>();
        while (!queue.isEmpty()) {
            results.add(queue.getFirst().execute());
            // Remove only after success; retry resumes at the failed command.
            queue.removeFirst();
        }
        return List.copyOf(results);
    }

    public int queuedCommands() {
        return queue.size();
    }
}
