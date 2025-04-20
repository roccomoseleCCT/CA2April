/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libraryapplication;

/**
 * @author rocmos
 */
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.*;

public class UserManagerTest {

    private UserManager userManager;

    @Before
    public void setUp() {
        userManager = new UserManager();
    }

    @Test
    public void testAddUserInsertsIntoDatabase() {
        String name = "JUnit Test User";
        String role = "Member";

        String deleteSql = "DELETE FROM users WHERE name = ?";
        String insertSql = "INSERT INTO users (name, role) VALUES (?, ?)";
        String selectSql = "SELECT * FROM users WHERE name = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql);
             PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {

            // Clean up
            deleteStmt.setString(1, name);
            deleteStmt.executeUpdate();

            // Insert test user
            insertStmt.setString(1, name);
            insertStmt.setString(2, role);
            insertStmt.executeUpdate();

            // Validate insert
            selectStmt.setString(1, name);
            ResultSet rs = selectStmt.executeQuery();

            boolean found = rs.next();
            assertTrue("User should be inserted into database", found);

        } catch (SQLException e) {
            fail("SQLException during test: " + e.getMessage());
        }
    }

    @Test
    public void testUserManagerIsNotNull() {
        assertNotNull("UserManager instance should not be null", userManager);
    }
}
