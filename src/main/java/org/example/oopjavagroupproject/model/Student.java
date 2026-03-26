package org.example.oopjavagroupproject.model;

public class Student {
    private int id;
    private String fullName;
    private String gender;
    private String phone;
    private Faculty faculty;

    // Constructor for existing students (with ID)
    public Student(int id, String fullName, String gender, String phone, Faculty faculty) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.phone = phone;
        this.faculty = faculty;
    }

    // Constructor for new students (without ID)
    public Student(String fullName, String gender, String phone, Faculty faculty) {
        this.fullName = fullName;
        this.gender = gender;
        this.phone = phone;
        this.faculty = faculty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public int getFacultyId() {
        return (faculty != null) ? faculty.getId() : 0;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
