package com.ms_reserve.reserve.model;

public class Seat {
    private String id;
    private boolean available;
    private SeatCategory category;

    public Seat(String id, boolean available, SeatCategory category) {
        this.id = id;
        this.available = available;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public SeatCategory getCategory() {
        return category;
    }

    public void setCategory(SeatCategory category) {
        this.category = category;
    }
}
