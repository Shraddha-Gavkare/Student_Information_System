package hex.org.database;

import models.Teacher;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {

    private Connection conn;

    public TeacherDAO() throws SQLException {
        conn = DatabaseConnection.getConnection(); // Your DB connection logic
    }

    
    public Teacher getTeacherByEmail1(String email) {
        try {
            String sql = "SELECT * FROM teachers WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Teacher(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("expertise") // changed from 'department' to 'expertise'
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        String query = "SELECT * FROM teachers";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Teacher teacher = new Teacher(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("expertise")
                );
                teachers.add(teacher);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    
    public Teacher getTeacherByEmail(String email) {
        String query = "SELECT * FROM teachers WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Teacher(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("expertise")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public boolean teacherExists(String email) {
        String query = "SELECT COUNT(*) FROM teachers WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public boolean teacherExists(int id) {
        String query = "SELECT COUNT(*) FROM teachers WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void insertTeacher(Teacher teacher) {
        if (teacherExists(teacher.getEmail())) {
            System.out.println("Teacher with this email already exists.");
            return;
        }

        String query = "INSERT INTO teachers (first_name, last_name, email, expertise) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, teacher.getFirstName());
            stmt.setString(2, teacher.getLastName());
            stmt.setString(3, teacher.getEmail());
            stmt.setString(4, teacher.getExpertise());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        teacher.setTeacherID(generatedKeys.getInt(1));
                    }
                }
                System.out.println("Teacher inserted successfully.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void updateTeacher(Teacher teacher) {
        String query = "UPDATE teachers SET first_name = ?, last_name = ?, email = ?, expertise = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, teacher.getFirstName());
            stmt.setString(2, teacher.getLastName());
            stmt.setString(3, teacher.getEmail());
            stmt.setString(4, teacher.getExpertise());
            stmt.setInt(5, teacher.getTeacherID());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Teacher updated successfully.");
            } else {
                System.out.println("Teacher not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
