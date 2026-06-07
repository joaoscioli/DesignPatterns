package com.joaoscioli.patterns.command;

public class SuspendSubscriptionCommand implements BillingCommand {
    private final String subscriptionId;
    private final String reason;

    public SuspendSubscriptionCommand(String subscriptionId, String reason) {
        if (subscriptionId == null || subscriptionId.isBlank()) {
            throw new IllegalArgumentException("subscriptionId must not be blank");
        }

        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("reason must not be blank");
        }

        this.subscriptionId = subscriptionId;
        this.reason = reason;
    }

    @Override
    public CommandResult execute() {
        return new CommandResult(
                "SuspendSubscriptionCommand",
                "Subscription " + subscriptionId + " suspended because " + reason
        );
    }
}
