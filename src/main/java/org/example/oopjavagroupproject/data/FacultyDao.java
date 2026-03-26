package org.example.oopjavagroupproject.data;

import org.example.oopjavagroupproject.database.DatabaseHandler;
import org.example.oopjavagroupproject.model.Faculty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacultyDao {

    private final DatabaseHandler dbHandler = new DatabaseHandler();

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
