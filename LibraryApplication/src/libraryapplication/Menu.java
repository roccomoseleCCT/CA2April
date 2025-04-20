/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libraryapplication;

import java.util.Scanner;

/**
 * @author rocmos
 */
public class Menu {
    private final Scanner scanner = new Scanner(System.in);

    public void displayMainMenu() {
        int choice;
        do {
            System.out.println("\n==== LIBRARY MANAGEMENT SYSTEM ====");
            System.out.println("1. Admin Panel");
            System.out.println("2. Member Panel");
            System.out.println("3. Notifications");
            System.out.println("-1. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Admin Panel ");
                    break;
                case 2:
                    System.out.println("Member Panel ");
                    break;
                case 3:
                    System.out.println("Notifications ");
                    break;
                case -1:
                    System.out.println("Exiting, goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != -1);
    }
}
