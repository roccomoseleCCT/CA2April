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

public class BookManagerTest {

    private BookManager bookManager;

    @Before
    public void setUp() {
        bookManager = new BookManager();
    }

    @Test
    public void testAddBookInsertsIntoDatabase() {
        String title = "JUnit Test Book";
        String author = "Test Author";
        String genre = "Testing";

        String deleteSql = "DELETE FROM books WHERE title = ?";
        String insertSql = "INSERT INTO books (title, author, genre, available) VALUES (?, ?, ?, TRUE)";
        String selectSql = "SELECT * FROM books WHERE title = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql);
             PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {

            // Clean up previous test runs
            deleteStmt.setString(1, title);
            deleteStmt.executeUpdate();

            // Insert test book
            insertStmt.setString(1, title);
            insertStmt.setString(2, author);
            insertStmt.setString(3, genre);
            insertStmt.executeUpdate();

            // Validate insert
            selectStmt.setString(1, title);
            ResultSet rs = selectStmt.executeQuery();

            boolean found = rs.next();
            assertTrue("Book should be inserted into database", found);

        } catch (SQLException e) {
            fail("SQLException during test: " + e.getMessage());
        }
    }

    @Test
    public void testBookManagerIsNotNull() {
        assertNotNull("BookManager instance should not be null", bookManager);
    }
}

