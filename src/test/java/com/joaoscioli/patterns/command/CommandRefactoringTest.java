package com.joaoscioli.patterns.command;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandRefactoringTest {
    @Test
    void replacingOperationSwitchWithCommandsPreservesMixedBatchOrderAndResults() {
        var operations = List.of(
                new LegacyOperation("invoice", "cus-123", 12900, null),
                new LegacyOperation("suspend", "sub-456", 0, "payment failed"),
                new LegacyOperation("invoice", "cus-789", 1, null));
        var before = operations.stream().map(CommandRefactoringTest::executeBeforeRefactoring).toList();
        var dispatcher = new BillingCommandDispatcher();
        dispatcher.enqueue(new GenerateInvoiceCommand("cus-123", 12900));
        dispatcher.enqueue(new SuspendSubscriptionCommand("sub-456", "payment failed"));
        dispatcher.enqueue(new GenerateInvoiceCommand("cus-789", 1));

        var after = dispatcher.dispatchAll();

        var expected = List.of(
                new CommandResult("GenerateInvoiceCommand", "Invoice generated for cus-123 with amount 12900"),
                new CommandResult("SuspendSubscriptionCommand", "Subscription sub-456 suspended because payment failed"),
                new CommandResult("GenerateInvoiceCommand", "Invoice generated for cus-789 with amount 1"));
        assertEquals(expected, before);
        assertEquals(before, after);
        assertEquals(0, dispatcher.queuedCommands());
        assertEquals(List.of(), dispatcher.dispatchAll());
    }

    // Before: a central dispatcher knows every operation and its data fields.
    private static CommandResult executeBeforeRefactoring(LegacyOperation operation) {
        return switch (operation.kind()) {
            case "invoice" -> new CommandResult("GenerateInvoiceCommand",
                    "Invoice generated for " + operation.id() + " with amount " + operation.amount());
            case "suspend" -> new CommandResult("SuspendSubscriptionCommand",
                    "Subscription " + operation.id() + " suspended because " + operation.reason());
            default -> throw new IllegalArgumentException("unknown operation");
        };
    }

    private record LegacyOperation(String kind, String id, long amount, String reason) { }
}
