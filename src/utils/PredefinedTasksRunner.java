package utils;

import hex.org.database.StudentDAO;
import exceptions.InvalidStudentDataException;

import java.sql.SQLException;

import hex.org.database.CourseDAO;
import hex.org.database.EnrollmentDAO;
import models.Student;
import models.Course;
import models.Enrollment;

public class PredefinedTasksRunner {
    public static void runPredefinedTasks() throws InvalidStudentDataException, SQLException {
        StudentDAO studentDAO = new StudentDAO();
        CourseDAO courseDAO = new CourseDAO();
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

        // Student data
        String firstName = "John";
        String lastName = "Doe";
        String dob = "2000-05-15";
        String email = "john.doe@example.com";
        String phone = "9876543210";

        // Check or create student
        Student john = studentDAO.getStudentByEmail(email);
        if (john == null) {
            System.out.println("Creating student with email: " + email);
            john = new Student(firstName, lastName, dob, email, phone);  // ✅ Correct constructor
            studentDAO.insertStudent(john);
        }

        // Check or create course
        Course course1 = courseDAO.getCourseByName("Introduction to Programming");
        if (course1 == null) {
            course1 = new Course(0, "Introduction to Programming", "CS101", null);
            courseDAO.insertCourse(course1);
        }

        // Enroll if not already
        if (!enrollmentDAO.isEnrolled(john.getId(), course1.getId())) {
            Enrollment enrollment = new Enrollment(john.getId(), course1.getId());
            enrollmentDAO.insertEnrollment(enrollment);
            System.out.println("Enrolled John in Introduction to Programming.");
        } else {
            System.out.println("John is already enrolled in Introduction to Programming.");
        }
    }
}
