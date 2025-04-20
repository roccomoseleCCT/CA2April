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

/**
 * @author rocmos
 */
public class TransactionManager {
     private final List<Transaction> transactions = new ArrayList<>();
    private final List<Book> books; // reference to shared books list
    private final Scanner scanner = new Scanner(System.in);

    public TransactionManager(List<Book> books) {
        this.books = books;
    }

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
        scanner.nextLine();

        Book book = findBookById(bookId);
        if (book == null || !book.isAvailable()) {
            System.out.println("Book not available.");
            return;
        }

        System.out.print("Enter Your User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        book.setAvailable(false);
        Transaction transaction = new Transaction(
                transactions.size() + 1,
                bookId,
                userId,
                LocalDate.now(),
                null,
                "Borrowed"
        );
        transactions.add(transaction);
        System.out.println("Book borrowed successfully.");
    }

    private void returnBook() {
        System.out.print("Enter Book ID to Return: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();

        Optional<Transaction> optional = transactions.stream()
                .filter(t -> t.getBookId() == bookId && t.getReturnDate() == null)
                .findFirst();

        if (optional.isEmpty()) {
            System.out.println("No active borrow transaction found.");
            return;
        }

        Transaction transaction = optional.get();
        transaction.setReturnDate(LocalDate.now());
        transaction.setStatus("Returned");

        Book book = findBookById(bookId);
        if (book != null) {
            book.setAvailable(true);
        }

        System.out.println("Book returned successfully.");
    }

    private void viewTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions available.");
        } else {
            for (Transaction t : transactions) {
                System.out.println("Transaction ID: " + t.getTransactionId() +
                        ", Book ID: " + t.getBookId() +
                        ", User ID: " + t.getUserId() +
                        ", Issued: " + t.getIssueDate() +
                        ", Returned: " + t.getReturnDate() +
                        ", Status: " + t.getStatus());
            }
        }
    }

    private Book findBookById(int id) {
        for (Book b : books) {
            if (b.getId() == id) return b;
        }
        return null;
    }
}
