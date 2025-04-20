/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libraryapplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.sql.*;

/**
 * @author rocmos
 */
public class TransactionManager {
    private final List<Transaction> transactions = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);


    public void manageTransactions() {
        int choice;
        do {
            System.out.println("\n--- Transaction Management ---");
            System.out.println("1. Borrow Book");
            System.out.println("2. Return Book");
            System.out.println("3. View Transactions");
            System.out.println("0. Return");
            System.out.print("Choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> borrowBook();
                case 2 -> returnBook();
                case 3 -> viewTransactions();
                case 0 -> System.out.println("Returning to Member Panel...");
                default -> System.out.println("Invalid option.");
            }
        } while (choice != 0);
    }

    private void borrowBook() {
        System.out.print("Enter Book ID to Borrow: ");
        int bookId = scanner.nextInt();
        System.out.print("Enter Your User ID: ");
        int userId = scanner.nextInt();

        String sql = "INSERT INTO transactions (book_id, user_id, issue_date, status) VALUES (?, ?, ?, 'Borrowed')";
        String updateBook = "UPDATE books SET available = FALSE WHERE id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 PreparedStatement updateStmt = conn.prepareStatement(updateBook)) {

                stmt.setInt(1, bookId);
                stmt.setInt(2, userId);
                stmt.setDate(3, Date.valueOf(LocalDate.now()));
                stmt.executeUpdate();

                updateStmt.setInt(1, bookId);
                updateStmt.executeUpdate();

                conn.commit();
                System.out.println("Book borrowed successfully.");
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void returnBook() {
        System.out.print("Enter Book ID to Return: ");
        int bookId = scanner.nextInt();
        System.out.print("Enter Your User ID: ");
        int userId = scanner.nextInt();

        String sql = "UPDATE transactions SET return_date = ?, status = 'Returned' WHERE book_id = ? AND user_id = ? AND status = 'Borrowed'";
        String updateBook = "UPDATE books SET available = TRUE WHERE id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 PreparedStatement updateStmt = conn.prepareStatement(updateBook)) {

                stmt.setDate(1, Date.valueOf(LocalDate.now()));
                stmt.setInt(2, bookId);
                stmt.setInt(3, userId);
                stmt.executeUpdate();

                updateStmt.setInt(1, bookId);
                updateStmt.executeUpdate();

                conn.commit();
                System.out.println("Book returned successfully.");
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewTransactions() {
        String sql = "SELECT * FROM transactions";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("Transaction %d: Book %d - User %d | Issued: %s | Returned: %s | Status: %s\n",
                        rs.getInt("id"),
                        rs.getInt("book_id"),
                        rs.getInt("user_id"),
                        rs.getDate("issue_date"),
                        rs.getDate("return_date"),
                        rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
