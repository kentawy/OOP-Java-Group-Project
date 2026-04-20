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

/**
 * Data Access Object (DAO) for handling CRUD operations for the {@link Student} entity.
 * This class provides methods to interact with the 'Student' table in the database.
 *
 * @author Bohdan Dmytrenko, Bohdan Ruban, Olha Sribna
 * @version 1.0
 */
public class StudentDao {

    private final DatabaseHandler dbHandler = new DatabaseHandler();

    /**
     * Retrieves all students from the database.
     * It performs a JOIN with the Faculty table to populate the student's faculty information.
     *
     * @return A list of all {@link Student} objects. Returns an empty list if an error occurs.
     */
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
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

    /**
     * Adds a new student to the database.
     *
     * @param student The {@link Student} object to add. The student's ID is ignored.
     */
    public void addStudent(Student student) {
        String query = "INSERT INTO Student (full_name, gender, phone, faculty_id) VALUES (?, ?, ?, ?)";

        try (Connection connection = dbHandler.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, student.getFullName());
            preparedStatement.setString(2, student.getGender());
            preparedStatement.setString(3, student.getPhone());
            preparedStatement.setInt(4, student.getFacultyId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while adding a student: " + e.getMessage());
        }
    }

    /**
     * Updates an existing student in the database.
     *
     * @param student The {@link Student} object with updated information. The student is identified by their ID.
     */
    public void updateStudent(Student student) {
        String query = "UPDATE Student SET full_name = ?, gender = ?, phone = ?, faculty_id = ? WHERE id = ?";

        try (Connection connection = dbHandler.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, student.getFullName());
            preparedStatement.setString(2, student.getGender());
            preparedStatement.setString(3, student.getPhone());
            preparedStatement.setInt(4, student.getFacultyId());
            preparedStatement.setInt(5, student.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while updating a student: " + e.getMessage());
        }
    }

    /**
     * Deletes a student from the database by their ID.
     *
     * @param id The ID of the student to delete.
     */
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
