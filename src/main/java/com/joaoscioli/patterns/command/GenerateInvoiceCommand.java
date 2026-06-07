package com.joaoscioli.patterns.command;

public class GenerateInvoiceCommand implements BillingCommand {
    private final String customerId;
    private final long amountCents;

    public GenerateInvoiceCommand(String customerId, long amountCents) {
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("customerId must not be blank");
        }

        if (amountCents <= 0) {
            throw new IllegalArgumentException("amountCents must be greater than zero");
        }

        this.customerId = customerId;
        this.amountCents = amountCents;
    }

    @Override
    public CommandResult execute() {
        return new CommandResult(
                "GenerateInvoiceCommand",
                "Invoice generated for " + customerId + " with amount " + amountCents
        );
    }
}
