package models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {
    private static int idCounter = 1;
    private int paymentID;
    private int studentId;
    private BigDecimal amount;
    private LocalDateTime paymentDate;

    // Constructor for inserting new payments (without manual paymentID)
    public Payment(int studentId, double amount, LocalDateTime paymentDate) {
        this.paymentID = idCounter++;
        this.studentId = studentId;
        this.amount = BigDecimal.valueOf(amount);
        this.paymentDate = paymentDate;
    }

    // Constructor for retrieving from database
    public Payment(int paymentID, int studentId, BigDecimal amount, LocalDateTime paymentDate) {
        this.paymentID = paymentID;
        this.studentId = studentId;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public int getStudentId() {
        return studentId;
    }

    public BigDecimal getPaymentAmount() {
        return amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void displayPaymentInfo() {
        System.out.println("Payment ID: " + paymentID);
        System.out.println("Student ID: " + studentId);
        System.out.println("Amount: " + amount);
        System.out.println("Date: " + paymentDate);
    }
}
