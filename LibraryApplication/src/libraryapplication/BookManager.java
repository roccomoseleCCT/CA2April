/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libraryapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.*;

/**
 * @author rocmos
 */
public class BookManager {
    private final List<Book> books = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public void manageBooks() {
        int choice;
        do {
            System.out.println("\n--- Book Management ---");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search Book by Title");
            System.out.println("4. Delete Book");
            System.out.println("0. Return");
            System.out.print("Choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 ->
                    addBook();
                case 2 ->
                    viewBooks();
                case 3 ->
                    searchBook();
                case 4 ->
                    deleteBook();
                case 0 ->
                    System.out.println("Returning to Admin Panel...");
                default ->
                    System.out.println("Invalid option.");
            }
        } while (choice != 0);
    }

    private void addBook() {
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Author: ");
        String author = scanner.nextLine();
        System.out.print("Enter Genre: ");
        String genre = scanner.nextLine();

        String sql = "INSERT INTO books (title, author, genre, available) VALUES (?, ?, ?, TRUE)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, genre);
            stmt.executeUpdate();
            System.out.println("Book added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewBooks() {
        String sql = "SELECT * FROM books";
        try (Connection conn = DatabaseConnection.getInstance().getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("[%d] %s by %s (%s) - %s\n",
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("genre"),
                        rs.getBoolean("available") ? "Available" : "Checked Out");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchBook() {
        System.out.print("Enter Title to Search: ");
        String search = scanner.nextLine();
        String sql = "SELECT * FROM books WHERE title LIKE ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + search + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.printf("[%d] %s by %s (%s) - %s\n",
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("genre"),
                        rs.getBoolean("available") ? "Available" : "Checked Out");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteBook() {
        System.out.print("Enter Book ID to Delete: ");
        int id = scanner.nextInt();

        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "Book deleted." : "Book not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     public List<Book> getBooks() {
        return new ArrayList<>(books); 
     }
     
    public Book getBookById(int id) {
        return books.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }
}
