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
    private final BookManager bookManager = new BookManager();
    private final UserManager userManager = new UserManager();
    private final TransactionManager transactionManager = new TransactionManager();
    private final NotificationService notificationService = new NotificationService();

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
                    displayAdminPanel();
                    break;
                case 2:
                    displayMemberPanel();
                    break;
                case 3:
                    notificationService.checkOverdueBooks();
                    break;
                case -1:
                    System.out.println("Exiting... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != -1);
    }

    private void displayAdminPanel() {
        int choice;
        do {
            System.out.println("\n--- Admin Panel ---");
            System.out.println("1. Manage Books");
            System.out.println("2. Manage Users");
            System.out.println("0. Return to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    bookManager.manageBooks();
                    break;
                case 2:
                    userManager.manageUsers();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private void displayMemberPanel() {
        int choice;
        do {
            System.out.println("\n--- Member Panel ---");
            System.out.println("1. Borrow / Return Books");
            System.out.println("2. View My Transactions");
            System.out.println("0. Return to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    transactionManager.manageTransactions();
                    break;
                case 2:
                    System.out.println("View My Transactions");
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }
}
