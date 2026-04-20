package org.example.oopjavagroupproject.data;

import org.example.oopjavagroupproject.database.DatabaseHandler;
import org.example.oopjavagroupproject.model.Faculty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for handling CRUD operations for the {@link Faculty} entity.
 * This class provides methods to interact with the 'Faculty' table in the database.
 *
 * @author Bohdan Dmytrenko, Bohdan Ruban, Olha Sribna
 * @version 1.0
 */
public class FacultyDao {

    private final DatabaseHandler dbHandler = new DatabaseHandler();

    /**
     * Retrieves all faculties from the database, ordered by name.
     *
     * @return A list of all {@link Faculty} objects. Returns an empty list if an error occurs.
     */
    public List<Faculty> getAllFaculties() {
        List<Faculty> faculties = new ArrayList<>();
        String query = "SELECT * FROM Faculty ORDER BY name";

        try (Connection connection = dbHandler.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                faculties.add(new Faculty(
                        resultSet.getInt("id"),
                        resultSet.getString("name")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching faculties: " + e.getMessage());
        }
        return faculties;
    }

    /**
     * Adds a new faculty to the database.
     *
     * @param faculty The {@link Faculty} object to add. The faculty's ID is ignored.
     */
    public void addFaculty(Faculty faculty) {
        String query = "INSERT INTO Faculty (name) VALUES (?)";
        try (Connection connection = dbHandler.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, faculty.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while adding faculty: " + e.getMessage());
        }
    }

    /**
     * Updates an existing faculty in the database.
     *
     * @param faculty The {@link Faculty} object with an updated name. The faculty is identified by its ID.
     */
    public void updateFaculty(Faculty faculty) {
        String query = "UPDATE Faculty SET name = ? WHERE id = ?";
        try (Connection connection = dbHandler.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, faculty.getName());
            preparedStatement.setInt(2, faculty.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while updating faculty: " + e.getMessage());
        }
    }

    /**
     * Deletes a faculty from the database by its ID.
     *
     * @param id The ID of the faculty to delete.
     */
    public void deleteFaculty(int id) {
        String query = "DELETE FROM Faculty WHERE id = ?";
        try (Connection connection = dbHandler.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while deleting faculty: " + e.getMessage());
        }
    }
}
