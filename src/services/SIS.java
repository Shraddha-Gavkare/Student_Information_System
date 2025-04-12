package services;

import models.*;
import exceptions.*;
import hex.org.database.*;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SIS {
    private final StudentDAO studentDAO;
    private final TeacherDAO teacherDAO;

    public final CourseDAO courseDAO = new CourseDAO();
    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private final PaymentDAO paymentDAO = new PaymentDAO();

    private List<Student> students;
    private List<Course> courses;
    private List<Teacher> teachers;

    public SIS() throws SQLException {
        this.students = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.teachers = new ArrayList<>();
        this.studentDAO = new StudentDAO();
        this.teacherDAO = new TeacherDAO();
    }

    public EnrollmentDAO getEnrollmentDAO() {
        return enrollmentDAO;
    }

    // STUDENT OPERATIONS
    public void addStudent(Student student) throws InvalidStudentDataException {
        if (student.getEmail() == null || student.getEmail().isEmpty()) {
            throw new InvalidStudentDataException("Email cannot be empty.");
        }
        studentDAO.insertStudent(student);
        students.add(student);
        System.out.println("Student added successfully.");
    }

    public void updateStudent(Student student) {
        studentDAO.updateStudent(student);
        System.out.println("Student updated successfully.");
    }

    // TEACHER OPERATIONS
    public void addTeacher(Teacher teacher) throws InvalidTeacherDataException {
        if (teacher.getEmail() == null || teacher.getEmail().isEmpty()) {
            throw new InvalidTeacherDataException("Email cannot be empty.");
        }
        teacherDAO.insertTeacher(teacher);
        teachers.add(teacher);
        System.out.println("Teacher added successfully.");
    }

    public void updateTeacher(Teacher teacher) {
        teacherDAO.updateTeacher(teacher);
        System.out.println("Teacher updated successfully.");
    }

    // COURSE OPERATIONS
    public void addCourse(Course course) throws InvalidCourseDataException {
        if (course.getCourseName() == null || course.getCourseName().isEmpty()) {
            throw new InvalidCourseDataException("Course name cannot be empty.");
        }
        courseDAO.insertCourse(course);
        courses.add(course);
        System.out.println("Course added successfully.");
    }

    public void updateCourse(Course course) {
        courseDAO.updateCourse(course);
        System.out.println("Course updated successfully.");
    }

    public void enrollStudent(Scanner sc) {
        try {
            System.out.print("Enter student email: ");
            String email = sc.nextLine();
            Student student = studentDAO.getStudentByEmail(email);
            if (student == null) {
                System.out.println("Error: Student not found.");
                return;
            }

            Integer studentId = student.getId(); // Get the Integer ID
            if (studentId == null) {
                System.out.println("Error: Could not retrieve student ID.");
                return;
            }

            System.out.print("Enter course name: ");
            String courseName = sc.nextLine();
            Course course = courseDAO.getCourseByName(courseName);
            if (course == null) {
                System.out.println("Error: Course not found.");
                return;
            }

            Integer courseId = course.getId(); // Get the Integer ID
            if (courseId == null) {
                System.out.println("Error: Could not retrieve course ID.");
                return;
            }

            enrollStudentInCourse1(studentId, courseId);
            System.out.println("***Student enrolled successfully!"); // Add success message

        } catch (Exception e) {
            System.out.println("Error enrolling student.");
            e.printStackTrace();
        }
    }

 // PAYMENT
    public void recordPayment(String email, double amount)
            throws PaymentValidationException, StudentNotFoundException, InvalidStudentDataException {

        Student student = studentDAO.getStudentByEmail(email);

        if (student == null)
            throw new StudentNotFoundException("Student not found.");
        if (amount <= 0)
            throw new PaymentValidationException("Amount must be greater than zero.");

        LocalDateTime dateTime = LocalDateTime.now();
        Payment payment = new Payment(student.getId(), amount, dateTime);

        paymentDAO.insertPayment(payment);
        student.getPaymentHistory().add(payment); // Optional: You can use a helper method for display
        System.out.println("Payment recorded successfully.");
    }

    // Overloaded method (optional/future use)
    public void recordPayment(Student student, double amount, Date date) {
        if (student != null && amount > 0 && date != null) {
            LocalDateTime dateTime = date.toLocalDate().atStartOfDay();
            Payment payment = new Payment(student.getId(), amount, dateTime);
            paymentDAO.insertPayment(payment);
            student.getPaymentHistory().add(payment);
            System.out.println("Payment recorded successfully (with custom date).");
        }
    }


    // DISPLAY
    public void displayAllStudents() throws InvalidStudentDataException {
        List<Student> studentsFromDB = studentDAO.getAllStudents();
        for (Student student : studentsFromDB) {
            System.out.println("Student Name: " + student.getFirstName() + " " + student.getLastName());
        }
    }

    public void generateEnrollmentReport(String courseName) {
        Course course = courseDAO.getCourseByName(courseName);
        if (course == null) {
            System.out.println("Course not found: " + courseName);
            return;
        }

        int courseId = course.getId();
        List<Student> enrolledStudents = enrollmentDAO.getEnrolledStudentsByCourseId(courseId);

        System.out.println("\n Enrollment Report for: " + course.getCourseName());
        for (Student s : enrolledStudents) {
            System.out.println(" - " + s.getFirstName() + " " + s.getLastName());
        }
    }

    // UTILITY METHOD
    public Student getStudentByEmail(String email) throws InvalidStudentDataException {
        return studentDAO.getStudentByEmail(email);
    }

    // UI USING SCANNER
    public void addStudent(Scanner sc) {
        try {
            System.out.print("First Name: ");
            String first = sc.nextLine();
            System.out.print("Last Name: ");
            String last = sc.nextLine();
            System.out.print("DOB (yyyy-mm-dd): ");
            String dobStr = sc.nextLine();
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Phone: ");
            String phone = sc.nextLine();

            Student student = new Student(0, first, last, Date.valueOf(dobStr), email, phone);
            addStudent(student);
        } catch (Exception e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    public void addTeacher(Scanner sc) {
        try {
            System.out.print("First Name: ");
            String first = sc.nextLine();
            System.out.print("Last Name: ");
            String last = sc.nextLine();
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Department: ");
            String dept = sc.nextLine();

            Teacher teacher = new Teacher(0, first, last, email, dept);
            addTeacher(teacher);
        } catch (Exception e) {
            System.out.println("Error adding teacher: " + e.getMessage());
        }
    }

    public void addCourse(Scanner sc) {
        try {
            System.out.print("Course Name: ");
            String name = sc.nextLine();
            System.out.print("Course Code: ");
            String code = sc.nextLine();

            Course course = new Course(0, name, code, null);
            addCourse(course);
        } catch (Exception e) {
            System.out.println("Error adding course: " + e.getMessage());
        }
    }

    public void enrollStudentInCourse1(Integer studentId, Integer courseId) {
        if (studentId == null || courseId == null) {
            System.out.println("Cannot enroll. Student ID or Course ID is null.");
            return;
        }
        enrollmentDAO.enrollStudent(studentId, courseId);
    }


    public void assignTeacherToCourse(Scanner sc) {
        try {
            System.out.print("Enter teacher email: ");
            String email = sc.nextLine();
            Teacher teacher = teacherDAO.getTeacherByEmail(email);
            if (teacher == null) throw new TeacherNotFoundException("Teacher not found.");

            System.out.print("Enter course name: ");
            String courseName = sc.nextLine();
            Course course = courseDAO.getCourseByName(courseName);
            if (course == null) throw new CourseNotFoundException("Course not found.");

            course.setTeacherId(teacher.getId());
            updateCourse(course);
        } catch (Exception e) {
            System.out.println("Error assigning teacher: " + e.getMessage());
        }
    }

    public void recordPayment(Scanner sc) {
        try {
            System.out.print("Enter student email: ");
            String email = sc.nextLine();
            System.out.print("Enter amount: ");
            double amount = Double.parseDouble(sc.nextLine());

            recordPayment(email, amount);
        } catch (Exception e) {
            System.out.println("Error recording payment: " + e.getMessage());
        }
    }

    public void generateEnrollmentReport(Scanner sc) {
        System.out.print("Enter course name: ");
        String courseName = sc.nextLine();
        generateEnrollmentReport(courseName);
    }

    public Teacher getTeacherByEmail(String email) throws SQLException {
        // First, check the in-memory list
        for (Teacher teacher : teachers) {
            if (teacher.getEmail().equalsIgnoreCase(email)) {
                return teacher;
            }
        }
        // If not found in memory, fetch from the database
        return teacherDAO.getTeacherByEmail(email);
    }

    public EnrollmentDAO getEnrollmentDAO1() {
        return enrollmentDAO;
    }
    
 // Generate Payment Report for a specific student
    public void generatePaymentReport(Student student) throws InvalidStudentDataException {
        System.out.println("\n--- Payment Report for Student: " + student.getFirstName() + " " + student.getLastName() + " ---");
        List<Payment> paymentHistory = paymentDAO.getPaymentsByStudentId(student.getId());
        if (paymentHistory.isEmpty()) {
            System.out.println("No payment history found for this student.");
        } else {
            for (Payment payment : paymentHistory) {
                System.out.println("Date: " + payment.getPaymentDate() + ", Amount: $" + payment.getPaymentAmount());
            }
            double totalPaid = paymentHistory.stream()
                    .mapToDouble(payment -> payment.getPaymentAmount().doubleValue())
                    .sum();
            System.out.println("--------------------------------------------------");
            System.out.println("Total Amount Paid: $" + totalPaid);
        }
    }
    // Calculate statistics for a specific course
    public void calculateCourseStatistics(Course course) {
        System.out.println("\n--- Course Statistics for: " + course.getCourseName() + " (" + course.getCourseCode() + ") ---");

        // Number of Enrollments
        List<Student> enrolledStudents = enrollmentDAO.getEnrolledStudentsByCourseId(course.getId());
        int numberOfEnrollments = enrolledStudents.size();
        System.out.println("Number of Enrollments: " + numberOfEnrollments);

        // Total Payments for the course (assuming each enrolled student paid a standard fee)
        // This is a simplified calculation. A more complex system might track individual payment per enrollment.
        double standardCourseFee = 500.0; // Example standard fee
        double totalPayments = numberOfEnrollments * standardCourseFee;
        System.out.println("Estimated Total Payments (at $" + standardCourseFee + " per student): $" + totalPayments);

        // You can add more statistics here, such as average payment, etc.
    }

}