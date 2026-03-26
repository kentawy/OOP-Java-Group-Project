package org.example.oopjavagroupproject.model;

import java.time.LocalDate;

public class Contract {
    private int id;
    private Student student;
    private Room room;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    // Constructor for existing contracts (with ID)
    public Contract(int id, Student student, Room room, LocalDate startDate, LocalDate endDate, String status) {
        this.id = id;
        this.student = student;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    // Constructor for new contracts (without ID)
    public Contract(Student student, Room room, LocalDate startDate, LocalDate endDate, String status) {
        this.student = student;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Helper methods for DAO operations
    public int getStudentId() {
        return (student != null) ? student.getId() : 0;
    }

    public int getRoomId() {
        return (room != null) ? room.getId() : 0;
    }
}
