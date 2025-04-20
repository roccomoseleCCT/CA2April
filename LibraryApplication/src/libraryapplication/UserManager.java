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
public class UserManager {

     private final Scanner scanner = new Scanner(System.in);

    public void manageUsers() {
        int choice;
        do {
            System.out.println("\n--- User Management ---");
            System.out.println("1. Add User");
            System.out.println("2. View All Users");
            System.out.println("3. Search User by Name");
            System.out.println("4. Delete User");
            System.out.println("0. Return");
            System.out.print("Choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addUser();
                case 2 -> viewUsers();
                case 3 -> searchUser();
                case 4 -> deleteUser();
                case 0 -> System.out.println("Returning to Admin Panel...");
                default -> System.out.println("Invalid option.");
            }
        } while (choice != 0);
    }

    private void addUser() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Role (Admin/Member): ");
        String role = scanner.nextLine();

        String sql = "INSERT INTO users (name, role) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, role);
            stmt.executeUpdate();
            System.out.println("User added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewUsers() {
        String sql = "SELECT * FROM users";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("[%d] %s - %s\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchUser() {
        System.out.print("Enter Name to Search: ");
        String search = scanner.nextLine();
        String sql = "SELECT * FROM users WHERE name LIKE ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + search + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.printf("[%d] %s - %s\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteUser() {
        System.out.print("Enter User ID to Delete: ");
        int id = scanner.nextInt();

        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "User deleted." : "User not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
