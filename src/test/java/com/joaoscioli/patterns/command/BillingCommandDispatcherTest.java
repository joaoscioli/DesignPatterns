package com.joaoscioli.patterns.command;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BillingCommandDispatcherTest {
    @Test
    void retryResumesAtFailureWithoutRepeatingSuccessfulCommandsOrSkippingPendingWork() {
        var dispatcher = new BillingCommandDispatcher();
        var calls = new java.util.ArrayList<String>();
        var attempts = new java.util.concurrent.atomic.AtomicInteger();
        var failure = new IllegalStateException("temporary failure");
        dispatcher.enqueue(() -> { calls.add("first"); return new CommandResult("first", "done"); });
        dispatcher.enqueue(() -> {
            calls.add("second");
            if (attempts.incrementAndGet() == 1) { throw failure; }
            return new CommandResult("second", "recovered");
        });
        dispatcher.enqueue(() -> { calls.add("third"); return new CommandResult("third", "done"); });

        org.junit.jupiter.api.Assertions.assertSame(failure,
                assertThrows(IllegalStateException.class, dispatcher::dispatchAll));
        assertEquals(List.of("first", "second"), calls);
        assertEquals(2, dispatcher.queuedCommands());
        assertEquals(List.of(new CommandResult("second", "recovered"), new CommandResult("third", "done")),
                dispatcher.dispatchAll());
        assertEquals(List.of("first", "second", "second", "third"), calls);
        assertEquals(0, dispatcher.queuedCommands());
        assertEquals(List.of(), dispatcher.dispatchAll());
    }

    @Test
    void nullResultKeepsFailedAndPendingCommandsQueuedForExplicitRetry() {
        var dispatcher = new BillingCommandDispatcher();
        var calls = new java.util.ArrayList<String>();
        var attempts = new java.util.concurrent.atomic.AtomicInteger();
        dispatcher.enqueue(() -> { calls.add("first"); return new CommandResult("first", "done"); });
        dispatcher.enqueue(() -> {
            calls.add("second");
            return attempts.incrementAndGet() == 1 ? null : new CommandResult("second", "recovered");
        });
        dispatcher.enqueue(() -> { calls.add("third"); return new CommandResult("third", "done"); });

        var failure = assertThrows(NullPointerException.class, dispatcher::dispatchAll);
        assertEquals("command result must not be null", failure.getMessage());
        assertEquals(List.of("first", "second"), calls);
        assertEquals(2, dispatcher.queuedCommands());
        assertEquals(List.of(new CommandResult("second", "recovered"), new CommandResult("third", "done")),
                dispatcher.dispatchAll());
        assertEquals(List.of("first", "second", "second", "third"), calls);
        assertEquals(0, dispatcher.queuedCommands());
    }

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
