package hex.org.database;

import models.Course;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM courses";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Course course = new Course(
                    rs.getInt("id"),
                    rs.getString("course_name"),
                    rs.getObject("teacher_id") != null ? rs.getInt("teacher_id") : null
                );
                courses.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    private boolean teacherExists(Integer teacherId) {
        if (teacherId == null) return true;

        String query = "SELECT COUNT(*) FROM teachers WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, teacherId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void insertCourse(Course course) {
        if (!teacherExists(course.getTeacherId())) {
            System.out.println("Cannot insert course: Teacher ID " + course.getTeacherId() + " does not exist.");
            return;
        }

        String query = "INSERT INTO courses (course_name, teacher_id, code) VALUES (?, ?, ?)"; // Added 'code' to the insert columns

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, course.getCourseName());
            stmt.setObject(2, course.getTeacherId());
            stmt.setString(3, course.getCourseCode()); // Set the 'code' here

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        course.setCourseId(generatedKeys.getInt(1)); // Use setCourseId for the primary key 'id'
                    }
                }
                System.out.println("Course inserted successfully with ID: " + course.getId()); // Use getId()
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCourse(Course course) {
        String query = "UPDATE courses SET course_name = ?, teacher_id = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, course.getCourseName());
            stmt.setObject(2, course.getTeacherId());
            stmt.setInt(3, course.getId()); 

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Course updated successfully.");
            } else {
                System.out.println("Course not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Course getCourseByName(String integer2) {
        Course course = null;
        String query = "SELECT * FROM courses WHERE course_name = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setNString(1, integer2);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("id");
                
                String code = rs.getString("code");
                course = new Course(id, integer2, code, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        
        if (course == null) {
            System.out.println("Course not found for name: " + integer2);
        } else {
            System.out.println("Found course: " + course.getId() + ", " + course.getCourseName());
        }

        return course;
    }



	
    public Course getCourseById(int courseId) {
        String query = "SELECT * FROM courses WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Course(
                    rs.getInt("id"),
                    rs.getString("course_name"),
                    rs.getObject("teacher_id") != null ? rs.getInt("teacher_id") : null
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Course getCourseByCode(String courseCode) {
        String query = "SELECT id, course_name, code, teacher_id FROM courses WHERE code = ?"; // Use 'code' in SELECT and WHERE
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, courseCode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Course(
                    rs.getInt("id"),
                    rs.getString("course_name"),
                    rs.getString("code"), 
                    rs.getObject("teacher_id") == null ? null : rs.getInt("teacher_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
