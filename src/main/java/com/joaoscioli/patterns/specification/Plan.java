package com.joaoscioli.patterns.specification;

public enum Plan {
    STARTER(5),
    GROWTH(25),
    ENTERPRISE(250);

    private final int maxSeats;

    Plan(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    public int maxSeats() {
        return maxSeats;
    }
}
