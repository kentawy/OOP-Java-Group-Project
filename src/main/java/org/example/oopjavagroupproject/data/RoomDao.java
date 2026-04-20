package org.example.oopjavagroupproject.data;

import org.example.oopjavagroupproject.database.DatabaseHandler;
import org.example.oopjavagroupproject.model.Room;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for handling CRUD operations for the {@link Room} entity.
 * This class provides methods to interact with the 'Room' table in the database.
 *
 * @author Bohdan Dmytrenko, Bohdan Ruban, Olha Sribna
 * @version 1.0
 */
public class RoomDao {

    private final DatabaseHandler dbHandler = new DatabaseHandler();

    /**
     * Retrieves all rooms from the database, ordered by room number.
     *
     * @return A list of all {@link Room} objects. Returns an empty list if an error occurs.
     */
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM Room ORDER BY room_number";

        try (Connection connection = dbHandler.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                rooms.add(new Room(
                        resultSet.getInt("id"),
                        resultSet.getString("room_number"),
                        resultSet.getInt("capacity"),
                        resultSet.getBigDecimal("price")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching rooms: " + e.getMessage());
        }
        return rooms;
    }

    /**
     * Adds a new room to the database.
     *
     * @param room The {@link Room} object to add. The room's ID is ignored.
     */
    public void addRoom(Room room) {
        String query = "INSERT INTO Room (room_number, capacity, price) VALUES (?, ?, ?)";
        try (Connection connection = dbHandler.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, room.getRoomNumber());
            preparedStatement.setInt(2, room.getCapacity());
            preparedStatement.setBigDecimal(3, room.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while adding room: " + e.getMessage());
        }
    }

    /**
     * Updates an existing room in the database.
     *
     * @param room The {@link Room} object with updated information. The room is identified by its ID.
     */
    public void updateRoom(Room room) {
        String query = "UPDATE Room SET room_number = ?, capacity = ?, price = ? WHERE id = ?";
        try (Connection connection = dbHandler.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, room.getRoomNumber());
            preparedStatement.setInt(2, room.getCapacity());
            preparedStatement.setBigDecimal(3, room.getPrice());
            preparedStatement.setInt(4, room.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while updating room: " + e.getMessage());
        }
    }

    /**
     * Deletes a room from the database by its ID.
     *
     * @param id The ID of the room to delete.
     */
    public void deleteRoom(int id) {
        String query = "DELETE FROM Room WHERE id = ?";
        try (Connection connection = dbHandler.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while deleting room: " + e.getMessage());
        }
    }
}
