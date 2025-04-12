package hex.org.database;

import models.Course;
import models.Enrollment;
import models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import exceptions.InvalidStudentDataException;

public class EnrollmentDAO {

    public void enrollStudent(int studentId, int courseId) {
        String query = "INSERT INTO enrollments (student_id, course_id, enrolled_at) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            stmt.executeUpdate();
            System.out.println("Student enrollment successfull**.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Enrollment> getEnrollmentsForStudent(int studentId) throws InvalidStudentDataException {
        List<Enrollment> enrollments = new ArrayList<>();
        String query = "SELECT * FROM enrollments WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Enrollment enrollment = new Enrollment(
                        rs.getInt("id"),
                        rs.getInt("student_id"),
                        rs.getInt("course_id"),
                        rs.getTimestamp("enrolled_at")
                );
                enrollments.add(enrollment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollments;
    }

    // Insert a new enrollment using Enrollment object
    public void insertEnrollment(Enrollment enrollment) {
        String query = "INSERT INTO enrollments (student_id, course_id, enrolled_at) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, enrollment.getStudentId());
            stmt.setInt(2, enrollment.getCourseId());
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            stmt.executeUpdate();
            System.out.println("Enrollment added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update existing enrollment
    public void updateEnrollment(Enrollment enrollment) {
        String query = "UPDATE enrollments SET course_id = ?, enrolled_at = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, enrollment.getCourseId());
            stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(3, enrollment.getEnrollmentID());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Enrollment updated successfully.");
            } else {
                System.out.println("⚠️ Enrollment not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertEnrollment(Integer studentId, Integer courseId) {
        String query = "INSERT INTO enrollments (student_id, course_id, enrolled_at) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, studentId); 
            stmt.setInt(2, courseId);  
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            stmt.executeUpdate();
            System.out.println("Enrollment added using Student and Course IDs.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> getEnrolledStudentsByCourseId(int courseId) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT s.id, s.first_name, s.last_name, s.email, s.phone_number, s.date_of_birth " +
                       "FROM students s " +
                       "JOIN enrollments e ON s.id = e.student_id " +
                       "WHERE e.course_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setEmail(rs.getString("email"));
                student.setPhoneNumber(rs.getString("phone_number"));
                student.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
                

                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }


 // Check if a student is already enrolled in a course
    public boolean isEnrolled(int studentId, int courseId) {
        String query = "SELECT COUNT(*) FROM enrollments WHERE student_id = ? AND course_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Enrollment> getEnrollmentsByStudentId(Integer studentId) {
        List<Enrollment> enrollments = new ArrayList<>();
        String query = "SELECT e.id AS enrollment_id, e.course_id, e.enrolled_at, c.course_name " +
                       "FROM enrollments e " +
                       "JOIN courses c ON e.course_id = c.id " +
                       "WHERE e.student_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
            	Enrollment enrollment = new Enrollment(studentId, studentId);
            	enrollment.setEnrollmentId(studentId);
            	enrollment.setCourseId(rs.getInt("course_id"));
            	enrollment.setEnrollmentId(rs.getInt("enrollment_id"));


                Course course = new Course(studentId, query, studentId);
                course.setCourseId(rs.getInt("course_id"));
                course.setCourseName(rs.getString("course_name"));
                enrollment.setCourseId(rs.getInt("course_id")); 
                Student student = new Student(); 
                student.setId(studentId);
                enrollment.setStudent(student);

                enrollments.add(enrollment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollments;
    }
}
