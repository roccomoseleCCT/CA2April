/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libraryapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author rocmos
 */
public class UserManager {

    private final List<User> users = new ArrayList<>();
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
                case 1 ->
                    addUser();
                case 2 ->
                    viewUsers();
                case 3 ->
                    searchUser();
                case 4 ->
                    deleteUser();
                case 0 ->
                    System.out.println("Returning to Admin Panel...");
                default ->
                    System.out.println("Invalid option.");
            }
        } while (choice != 0);
    }

    private void addUser() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Role (Admin/Member): ");
        String role = scanner.nextLine();

        int id = users.size() + 1;
        User user = UserFactory.createUser(role, id, name, email);
        users.add(user);
        System.out.println("User added successfully.");
    }

    private void viewUsers() {
        if (users.isEmpty()) {
            System.out.println("No users available.");
        } else {
            users.forEach(u -> System.out.println("[" + u.id + "] " + u.name + " - " + u.role));
        }
    }

    private void searchUser() {
        System.out.print("Enter Name to Search: ");
        String name = scanner.nextLine().toLowerCase();
        users.stream()
                .filter(u -> u.name.toLowerCase().contains(name))
                .forEach(u -> System.out.println("[" + u.id + "] " + u.name + " - " + u.role));
    }

    private void deleteUser() {
        System.out.print("Enter User ID to Delete: ");
        int id = scanner.nextInt();
        boolean removed = users.removeIf(u -> u.id == id);
        System.out.println(removed ? "User deleted." : "User not found.");
    }
}
