package org.example.oopjavagroupproject.model;

/**
 * Represents a faculty entity. This class corresponds to a single row
 * in the "faculties" table in the database.
 *
 * @author Bohdan Dmytrenko, Bohdan Ruban, Olha Sribna
 * @version 1.0
 */
public class Faculty {
    private int id;
    private String name;

    /**
     * Constructs a new Faculty with a specified ID and name.
     *
     * @param id   The unique identifier of the faculty.
     * @param name The name of the faculty.
     */
    public Faculty(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Returns the faculty's unique ID.
     * @return The faculty ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the faculty's unique ID.
     * @param id The new faculty ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the faculty.
     * @return The faculty name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the faculty.
     * @param name The new faculty name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the string representation of the faculty, which is its name.
     * This is useful for displaying faculties in UI components like ComboBox.
     * @return The faculty's name.
     */
    @Override
    public String toString() {
        return name;
    }
}
