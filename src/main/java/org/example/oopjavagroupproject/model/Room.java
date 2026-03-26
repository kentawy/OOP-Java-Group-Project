package org.example.oopjavagroupproject.model;

import java.math.BigDecimal;

public class Room {
    private int id;
    private String roomNumber;
    private int capacity;
    private BigDecimal price;

    public Room(int id, String roomNumber, int capacity, BigDecimal price) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Кімната №" + roomNumber;
    }
}
