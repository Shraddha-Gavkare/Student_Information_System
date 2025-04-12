package hex.org.main;

import models.*;
import services.SIS;
import exceptions.*;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InvalidStudentDataException, SQLException {
        System.out.println("Application started***.");
        Scanner sc = new Scanner(System.in);
        SIS sis = new SIS();  // SIS object created here

        while (true) {
        	System.out.println("==========================================");
        	System.out.println("       Student Information System J317S         ");
        	System.out.println("==========================================");
        	System.out.println("1.  Add Student");
        	System.out.println("2.  Add Teacher");
        	System.out.println("3.  Add Course");
        	System.out.println("4.  Enroll Student in Course");
        	System.out.println("5.  Assign Teacher to Course");
        	System.out.println("6.  Record Payment");
        	System.out.println("7.  Generate Enrollment Report");
        	System.out.println("8.  Run Task 8: Student Enrollment");
        	System.out.println("9.  Run Task 9: Teacher Assignment");
        	System.out.println("10. Run Task 10: Payment Record");
        	System.out.println("11. Run Task 11: Enrollment Report Generation");
        	System.out.println("12. Generate Payment Report");
        	System.out.println("13. Calculate Course Statistics");
        	System.out.println("14. Exit");
        	System.out.println("==========================================");
        	System.out.print("Enter your choice: ");


            int choice = sc.nextInt();
            sc.nextLine();

            try {
                switch (choice) {
                    case 1 -> sis.addStudent(sc);
                    case 2 -> sis.addTeacher(sc);
                    case 3 -> sis.addCourse(sc);
                    case 4 -> sis.enrollStudent(sc);
                    case 5 -> sis.assignTeacherToCourse(sc);
                    case 6 -> sis.recordPayment(sc);
                    case 7 -> sis.generateEnrollmentReport(sc);
                    case 8 -> runTask8(sis);
                    case 9 -> runTask9(sis);
                    case 10 -> runTask10(sis);
                    case 11 -> runTask11(sis);
                    case 12 -> runTask12(sis);
                    case 13 -> runTask13(sis);
                    case 14 -> {
                        System.out.println("Exiting application. Goodbye!");
                        return;
                    }
                    default -> System.out.println("⚠️  Invalid option! Try again.");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void runTask8(SIS sis) throws Exception {
        System.out.println("\n--- Running Task 8: Student Enrollment ---");
        String johnEmail = "john.doe@example.com";
        Student john = sis.getStudentByEmail(johnEmail);

        if (john == null) {
            john = new Student(0, "John", "Doe", Date.valueOf(LocalDate.of(1995, 8, 15)), johnEmail, "1234567890");
            sis.addStudent(john);
            john = sis.getStudentByEmail(johnEmail);
        }

        Course progCourse = sis.courseDAO.getCourseByName("Introduction to Programming");
        Course mathCourse = sis.courseDAO.getCourseByName("Mathematics 101");

        if (john != null && progCourse != null && mathCourse != null) {
            if (!sis.getEnrollmentDAO().isEnrolled(john.getId(), progCourse.getId())) {
                sis.enrollStudentInCourse1(john.getId(), progCourse.getId());
                System.out.println("John Doe enrolled in Introduction to Programming.");
            } else {
                System.out.println("John Doe is already enrolled in Introduction to Programming.");
            }

            if (!sis.getEnrollmentDAO().isEnrolled(john.getId(), mathCourse.getId())) {
                sis.enrollStudentInCourse1(john.getId(), mathCourse.getId());
                System.out.println("John Doe enrolled in Mathematics 101.");
            } else {
                System.out.println("John Doe is already enrolled in Mathematics 101.");
            }
        } else {
            System.out.println("❌ Error: Could not find John Doe or the courses.");
        }
    }

    private static void runTask9(SIS sis) throws Exception {
        System.out.println("\n--- Running Task 9: Teacher Assignment ---");
        String sarahEmail = "sarah.smith@example.com";
        Teacher sarah = sis.getTeacherByEmail(sarahEmail);

        if (sarah == null) {
            sarah = new Teacher(0, "Sarah", "Smith", sarahEmail, "Computer Science");
            sis.addTeacher(sarah);
            sarah = sis.getTeacherByEmail(sarahEmail);
        }

        Course advDb = sis.courseDAO.getCourseByCode("CS302");
        if (advDb == null) {
            System.out.println("Inserting course with code: CS302");
            advDb = new Course(0, "Advanced Database Management", "CS302", null);
            sis.addCourse(advDb);
            advDb = sis.courseDAO.getCourseByCode("CS302");
        }

        if (sarah != null && advDb != null) {
            advDb.assignTeacher(sarah);
            sis.updateCourse(advDb);
            System.out.println("Sarah Smith assigned to Advanced Database Management.");
        } else {
            System.out.println("❌ Error: Could not assign Sarah Smith to the course.");
        }
    }

    private static void runTask10(SIS sis) throws Exception {
        System.out.println("\n--- Running Task 10: Payment Record ---");
        String janeEmail = "jane@example.com";
        Student jane = sis.getStudentByEmail(janeEmail);

        if (jane == null) {
            jane = new Student(0, "Jane", "Johnson", Date.valueOf("1997-03-20"), janeEmail, "9876543210");
            sis.addStudent(jane);
            jane = sis.getStudentByEmail(janeEmail);
        }

        if (jane != null) {
            sis.recordPayment(jane.getEmail(), 500.00);
            System.out.println("Payment recorded for Jane Johnson.");
        } else {
            System.out.println("❌ Error: Could not record payment for Jane Johnson.");
        }
    }

    private static void runTask11(SIS sis) throws Exception {
        System.out.println("\n--- Running Task 11: Enrollment Report Generation ---");
        sis.generateEnrollmentReport("Computer Science 101");
    }

    private static void runTask12(SIS sis) throws Exception {
        System.out.println("\n--- Running Task 12: Generate Payment Report ---");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter student email to generate payment report: ");
        String studentEmail = sc.nextLine();
        Student student = sis.getStudentByEmail(studentEmail);

        if (student != null) {
            sis.generatePaymentReport(student);
        } else {
            System.out.println("❌ Error: Student with email '" + studentEmail + "' not found.");
        }
    }

    private static void runTask13(SIS sis) throws Exception {
        System.out.println("\n--- Running Task 13: Calculate Course Statistics ---");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter course name to calculate statistics: ");
        String courseName = sc.nextLine();
        Course course = sis.courseDAO.getCourseByName(courseName);

        if (course != null) {
            sis.calculateCourseStatistics(course);
        } else {
            System.out.println("❌ Error: Course with name '" + courseName + "' not found.");
        }
    }
}
