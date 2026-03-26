package org.example.oopjavagroupproject.data;

import org.example.oopjavagroupproject.database.DatabaseHandler;
import org.example.oopjavagroupproject.model.Faculty;
import org.example.oopjavagroupproject.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {

    private final DatabaseHandler dbHandler = new DatabaseHandler();

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        // The query now joins with the Faculty table
        String query = "SELECT s.id, s.full_name, s.gender, s.phone, s.faculty_id, f.name AS faculty_name " +
                       "FROM Student s " +
                       "LEFT JOIN Faculty f ON s.faculty_id = f.id";

        try (Connection connection = dbHandler.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Faculty faculty = new Faculty(
                        resultSet.getInt("faculty_id"),
                        resultSet.getString("faculty_name")
                );
                Student student = new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("full_name"),
                        resultSet.getString("gender"),
                        resultSet.getString("phone"),
                        faculty
                );
                students.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching students: " + e.getMessage());
        }
        return students;
    }

    public void addStudent(Student student) {
        String query = "INSERT INTO Student (full_name, gender, phone, faculty_id) VALUES (?, ?, ?, ?)";

        try (Connection connection = dbHandler.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, student.getFullName());
            preparedStatement.setString(2, student.getGender());
            preparedStatement.setString(3, student.getPhone());
            preparedStatement.setInt(4, student.getFacultyId()); // Use the helper method

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while adding a student: " + e.getMessage());
        }
    }

    public void updateStudent(Student student) {
        String query = "UPDATE Student SET full_name = ?, gender = ?, phone = ?, faculty_id = ? WHERE id = ?";

        try (Connection connection = dbHandler.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, student.getFullName());
            preparedStatement.setString(2, student.getGender());
            preparedStatement.setString(3, student.getPhone());
            preparedStatement.setInt(4, student.getFacultyId()); // Use the helper method
            preparedStatement.setInt(5, student.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while updating a student: " + e.getMessage());
        }
    }

    public void deleteStudent(int id) {
        String query = "DELETE FROM Student WHERE id = ?";

        try (Connection connection = dbHandler.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while deleting a student: " + e.getMessage());
        }
    }
}
