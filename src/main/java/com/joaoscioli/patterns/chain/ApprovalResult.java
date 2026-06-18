package com.joaoscioli.patterns.chain;

public record ApprovalResult(boolean approved, String reason) {
    public static ApprovalResult success() {
        return new ApprovalResult(true, "Subscription approved");
    }

    public static ApprovalResult rejected(String reason) {
        return new ApprovalResult(false, reason);
    }
}
