package com.joaoscioli.patterns.command;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BillingCommandDispatcherTest {
    @Test
    void dispatchingEmptyQueueReturnsNoResults() {
        BillingCommandDispatcher dispatcher = new BillingCommandDispatcher();

        List<CommandResult> results = dispatcher.dispatchAll();

        assertAll(
                () -> assertEquals(0, results.size()),
                () -> assertEquals(0, dispatcher.queuedCommands())
        );
    }

    @Test
    void dispatchesQueuedCommandsInOrder() {
        BillingCommandDispatcher dispatcher = new BillingCommandDispatcher();

        dispatcher.enqueue(new GenerateInvoiceCommand("cus-123", 12900));
        dispatcher.enqueue(new SuspendSubscriptionCommand("sub-456", "payment failed"));

        List<CommandResult> results = dispatcher.dispatchAll();

        assertAll(
                () -> assertEquals(2, results.size()),
                () -> assertEquals("GenerateInvoiceCommand", results.get(0).commandName()),
                () -> assertEquals("Invoice generated for cus-123 with amount 12900", results.get(0).message()),
                () -> assertEquals("SuspendSubscriptionCommand", results.get(1).commandName()),
                () -> assertEquals("Subscription sub-456 suspended because payment failed", results.get(1).message()),
                () -> assertEquals(0, dispatcher.queuedCommands())
        );
    }

    @Test
    void rejectsMissingCommand() {
        BillingCommandDispatcher dispatcher = new BillingCommandDispatcher();

        assertThrows(NullPointerException.class, () -> dispatcher.enqueue(null));
    }

    @Test
    void keepsCommandsQueuedWhenDispatchFails() {
        BillingCommandDispatcher dispatcher = new BillingCommandDispatcher();
        dispatcher.enqueue(() -> {
            throw new IllegalStateException("payment provider unavailable");
        });

        assertThrows(IllegalStateException.class, dispatcher::dispatchAll);

        assertEquals(1, dispatcher.queuedCommands());
    }

    @Test
    void rejectsInvalidInvoiceAmount() {
        assertThrows(IllegalArgumentException.class, () -> new GenerateInvoiceCommand("cus-123", 0));
    }

    @Test
    void rejectsBlankSuspensionReason() {
        assertThrows(IllegalArgumentException.class, () -> new SuspendSubscriptionCommand("sub-456", " "));
    }
}
