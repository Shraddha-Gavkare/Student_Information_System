package models;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private int courseID;
    private String courseName;
    private String courseCode;
    private Teacher instructor;
    private Integer teacherId; 
    private List<Enrollment> enrollments;

    public Course(int courseID, String courseName, String courseCode, Integer teacherId) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseCode = (courseCode != null) ? courseCode.trim() : null; // Trim whitespace
        this.teacherId = teacherId;
        this.enrollments = new ArrayList<>();
    }

    
    public Course(int courseID, String courseName, int teacherId) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.teacherId = teacherId;
        this.enrollments = new ArrayList<>();
    }

    public void assignTeacher(Teacher teacher) {
        this.instructor = teacher;
        this.teacherId = teacher.getId(); // Update teacherId when assigning a Teacher object
        System.out.println("Teacher assigned: " + teacher.getFirstName() + " " + teacher.getLastName());
    }

    public void updateCourseInfo(String courseCode, String courseName, Teacher instructor) {
        this.courseCode = (courseCode != null) ? courseCode.trim() : null; // Trim whitespace
        this.courseName = courseName;
        this.instructor = instructor;
        this.teacherId = (instructor != null) ? instructor.getId() : null; // Update teacherId
        System.out.println("Course information updated successfully.");
    }

    public void displayCourseInfo() {
        System.out.println("Course ID: " + courseID);
        System.out.println("Course Name: " + courseName);
        System.out.println("Course Code: " + courseCode);
        System.out.println("Instructor: " + (instructor != null ? instructor.getFirstName() + " " + instructor.getLastName() : "Not Assigned"));
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public Integer getId() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public String setCourseName(String courseName) {
        return this.courseName = courseName;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(int i) {
        this.courseCode = String.valueOf(i);
    }

    public Teacher getInstructor() {
        return instructor;
    }

    public void setInstructor(Teacher instructor) {
        this.instructor = instructor;
        this.teacherId = (instructor != null) ? instructor.getId() : null;
    }

    @Override
    public String toString() {
        return "Course ID: " + courseID +
               ", Name: " + courseName +
               ", Code: " + courseCode +
               ", Instructor: " + (instructor != null ? instructor.getFirstName() + " " + instructor.getLastName() : "Not Assigned");
    }

    public void setCourseId(int id) {
        this.courseID = id;
    }

    public void setTeacherId(int id) {
        this.teacherId = id;
    }
}