package org.example.oopjavagroupproject.data;

import org.example.oopjavagroupproject.database.DatabaseHandler;
import org.example.oopjavagroupproject.model.Contract;
import org.example.oopjavagroupproject.model.Faculty;
import org.example.oopjavagroupproject.model.Room;
import org.example.oopjavagroupproject.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContractDao {

    private final DatabaseHandler dbHandler = new DatabaseHandler();

    public List<Contract> getAllContracts() {
        List<Contract> contracts = new ArrayList<>();
        String query = "SELECT c.id, c.start_date, c.end_date, c.status, " +
                "s.id as student_id, s.full_name, s.gender, s.phone, f.id as faculty_id, f.name as faculty_name, " +
                "r.id as room_id, r.room_number, r.capacity, r.price " +
                "FROM Contract c " +
                "JOIN Student s ON c.student_id = s.id " +
                "JOIN Room r ON c.room_id = r.id " +
                "JOIN Faculty f ON s.faculty_id = f.id";

        try (Connection connection = dbHandler.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                Faculty faculty = new Faculty(rs.getInt("faculty_id"), rs.getString("faculty_name"));
                Student student = new Student(rs.getInt("student_id"), rs.getString("full_name"), rs.getString("gender"), rs.getString("phone"), faculty);
                Room room = new Room(rs.getInt("room_id"), rs.getString("room_number"), rs.getInt("capacity"), rs.getBigDecimal("price"));
                Contract contract = new Contract(
                        rs.getInt("id"),
                        student,
                        room,
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate(),
                        rs.getString("status")
                );
                contracts.add(contract);
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching contracts: " + e.getMessage());
        }
        return contracts;
    }

    public void addContract(Contract contract) {
        String query = "INSERT INTO Contract (student_id, room_id, start_date, end_date, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dbHandler.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, contract.getStudentId());
            preparedStatement.setInt(2, contract.getRoomId());
            preparedStatement.setDate(3, Date.valueOf(contract.getStartDate()));
            preparedStatement.setDate(4, Date.valueOf(contract.getEndDate()));
            preparedStatement.setString(5, contract.getStatus());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while adding contract: " + e.getMessage());
        }
    }

    public void updateContract(Contract contract) {
        String query = "UPDATE Contract SET student_id = ?, room_id = ?, start_date = ?, end_date = ?, status = ? WHERE id = ?";
        try (Connection connection = dbHandler.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, contract.getStudentId());
            preparedStatement.setInt(2, contract.getRoomId());
            preparedStatement.setDate(3, Date.valueOf(contract.getStartDate()));
            preparedStatement.setDate(4, Date.valueOf(contract.getEndDate()));
            preparedStatement.setString(5, contract.getStatus());
            preparedStatement.setInt(6, contract.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while updating contract: " + e.getMessage());
        }
    }

    public void deleteContract(int id) {
        String query = "DELETE FROM Contract WHERE id = ?";
        try (Connection connection = dbHandler.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while deleting contract: " + e.getMessage());
        }
    }
}
