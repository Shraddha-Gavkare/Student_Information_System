package models;

import java.util.ArrayList;
import java.util.List;

public class Teacher {
    private int teacherID;
    private String firstName;
    private String lastName;
    private String email;
    private String expertise;
    private List<Course> assignedCourses;

    // Constructor for new teachers (without ID)
    public Teacher(String firstName, String lastName, String email, String expertise) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.expertise = expertise;
        this.assignedCourses = new ArrayList<>();
    }

    // Constructor for database records (with ID)
    public Teacher(int teacherID, String firstName, String lastName, String email, String expertise) {
        this.teacherID = teacherID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.expertise = expertise;
        this.assignedCourses = new ArrayList<>();
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public List<Course> getAssignedCourses() {
        return assignedCourses;
    }

    public void assignCourse(Course course) {
        assignedCourses.add(course);
        System.out.println("Course " + course.getCourseName() + " assigned to " + firstName + " " + lastName);
    }

    public void displayTeacherInfo() {
        System.out.println("Teacher ID: " + teacherID);
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Email: " + email);
        System.out.println("Expertise: " + expertise);
    }

    public int getId() {
        return teacherID;
    }

    @Override
    public String toString() {
        return "Teacher ID: " + teacherID +
               ", Name: " + firstName + " " + lastName +
               ", Email: " + email +
               ", Expertise: " + expertise;
    }

    public void setId(int id) {
        this.teacherID = id;
    }

}
