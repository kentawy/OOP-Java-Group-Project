package org.example.oopjavagroupproject.model;

import java.math.BigDecimal;

/**
 * Represents a room entity in the dormitory. This class corresponds to a single row
 * in the "rooms" table in the database.
 *
 * @author Bohdan Dmytrenko, Bohdan Ruban, Olha Sribna
 * @version 1.0
 */
public class Room {
    private int id;
    private String roomNumber;
    private int capacity;
    private BigDecimal price;

    /**
     * Constructs a new Room with specified details.
     *
     * @param id         The unique identifier of the room.
     * @param roomNumber The room number.
     * @param capacity   The maximum capacity of the room.
     * @param price      The price per occupant for the room.
     */
    public Room(int id, String roomNumber, int capacity, BigDecimal price) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.price = price;
    }

    /**
     * Returns the room's unique ID.
     * @return The room ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the room's unique ID.
     * @param id The new room ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the room number.
     * @return The room number.
     */
    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     * Sets the room number.
     * @param roomNumber The new room number.
     */
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * Returns the capacity of the room.
     * @return The room capacity.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity of the room.
     * @param capacity The new room capacity.
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Returns the price of the room.
     * @return The room price.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the price of the room.
     * @param price The new room price.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Returns the string representation of the room.
     * This is useful for displaying rooms in UI components.
     * @return A string in the format "Кімната №[roomNumber]".
     */
    @Override
    public String toString() {
        return "Кімната №" + roomNumber;
    }
}
