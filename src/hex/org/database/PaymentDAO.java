package hex.org.database;

import models.Payment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class PaymentDAO {

    // Retrieve all payments for a specific student
    public List<Payment> getPaymentsForStudent(int studentId) {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT * FROM payments WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Payment payment = new Payment(
                    rs.getInt("id"),
                    rs.getInt("student_id"),
                    rs.getBigDecimal("amount"),
                    rs.getTimestamp("payment_date").toLocalDateTime()
                );
                payments.add(payment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    // Insert a new payment record
    public void insertPayment(Payment payment) {
        String query = "INSERT INTO payments (student_id, amount, payment_date) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, payment.getStudentId());
            stmt.setBigDecimal(2, payment.getPaymentAmount());
            stmt.setTimestamp(3, Timestamp.valueOf(payment.getPaymentDate()));

            stmt.executeUpdate();
            System.out.println("Payment inserted successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update an existing payment record
    public void updatePayment(Payment payment) {
        String query = "UPDATE payments SET amount = ?, payment_date = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setBigDecimal(1, payment.getPaymentAmount());
            stmt.setTimestamp(2, Timestamp.valueOf(payment.getPaymentDate()));
            stmt.setInt(3, payment.getPaymentID());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Payment updated successfully.");
            } else {
                System.out.println("Payment not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve payments by student ID (alternative method)
    public List<Payment> getPaymentsByStudentId(Integer studentId) {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT id, student_id, amount, payment_date FROM payments WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Payment payment = new Payment(
                    rs.getInt("id"),
                    rs.getInt("student_id"),
                    rs.getBigDecimal("amount"),
                    rs.getTimestamp("payment_date").toLocalDateTime()
                );
                payments.add(payment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }
}
