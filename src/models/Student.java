package models;

import exceptions.InvalidStudentDataException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Student {
    private int studentId;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String email;
    private String phoneNumber;
    private List<Enrollment> enrollments;
    private List<Payment> payments;

   
    public Student() {
        this.enrollments = new ArrayList<>();
        this.payments = new ArrayList<>();
    }

    
    public Student(int studentId) {
        this.studentId = studentId;
        this.enrollments = new ArrayList<>();
        this.payments = new ArrayList<>();
    }

    
    public Student(int studentId, String firstName, String lastName, Date dateOfBirth, String email, String phoneNumber) throws InvalidStudentDataException {
        System.out.println("Creating student with email: " + email); // 🪵 Log email
        validateStudentData(email, phoneNumber);
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.enrollments = new ArrayList<>();
        this.payments = new ArrayList<>();
    }

    
    public Student(String firstName, String lastName, String dob, String email, String phoneNumber) throws InvalidStudentDataException {
        System.out.println("Creating student:");
        System.out.println("  Name: " + firstName + " " + lastName);
        System.out.println("  DOB: " + dob);
        System.out.println("  Email: " + email); // 🪵 Log email
        System.out.println("  Phone: " + phoneNumber);
        
        validateStudentData(email, phoneNumber);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = Date.valueOf(dob); // yyyy-MM-dd
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.enrollments = new ArrayList<>();
        this.payments = new ArrayList<>();
    }

   
    private void validateStudentData(String email, String phoneNumber) throws InvalidStudentDataException {
        if (email == null || email.isEmpty()) {
            throw new InvalidStudentDataException("Email cannot be empty.");
        }
        if (phoneNumber == null || phoneNumber.length() != 10) {
            throw new InvalidStudentDataException("Phone number must be 10 digits.");
        }
    }

    
    public void enrollInCourse(Course course) {
        for (Enrollment e : enrollments) {
            if (e.getCourse().equals(course)) {
                System.out.println("Already enrolled in course: " + course.getCourseName());
                return;
            }
        }
        Enrollment enrollment = new Enrollment(this, course);
        enrollments.add(enrollment);
        System.out.println(firstName + " has been enrolled in " + course.getCourseName());
    }

    
    public void updateStudentInfo(String firstName, String lastName, Date dateOfBirth, String email, String phoneNumber) throws InvalidStudentDataException {
        validateStudentData(email, phoneNumber);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        System.out.println("Student information updated successfully.");
    }

    
    public void makePayment(double amount, LocalDateTime paymentDate) throws InvalidStudentDataException {
        if (amount <= 0) {
            throw new InvalidStudentDataException("Payment amount must be greater than zero.");
        }

        // Create Payment using only student ID
        Payment payment = new Payment(this.getId(), amount, paymentDate);
        
        payments.add(payment); // Assuming 'payments' is your List<Payment> or similar
        System.out.println("Payment of " + amount + " recorded on " + paymentDate);
    }

    
    public void displayStudentInfo() {
        System.out.println("Student ID: " + studentId);
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Date of Birth: " + dateOfBirth);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phoneNumber);
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    
    public void setDateOfBirth(LocalDate localDate) {
        this.dateOfBirth = Date.valueOf(localDate);
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setId(int student) {
        this.studentId = student;
    }

    public Integer getId() {
        return studentId;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public void addPayment(Payment payment) {
        this.payments.add(payment);
    }

    public List<Payment> getPaymentHistory() {
        return payments;
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId +
               ", Name: " + firstName + " " + lastName +
               ", Email: " + email +
               ", Phone: " + phoneNumber;
    }

    public void setAddress(String address) {
        
        System.out.println("Address set to: " + address); 
    }
}
