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
                case 1 -> addBook();
                case 2 -> viewBooks();
                case 3 -> searchBook();
                case 4 -> deleteBook();
                case 0 -> System.out.println("Returning to Admin Panel...");
                default -> System.out.println("Invalid option.");
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

        int id = books.size() + 1;
        Book newBook = new Book(id, title, author, genre, true);
        books.add(newBook);
        System.out.println("Book added successfully.");
    }

    private void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            books.forEach(System.out::println);
        }
    }

    private void searchBook() {
        System.out.print("Enter Title to Search: ");
        String title = scanner.nextLine().toLowerCase();
        books.stream()
            .filter(b -> b.getTitle().toLowerCase().contains(title))
            .forEach(System.out::println);
    }

    private void deleteBook() {
        System.out.print("Enter Book ID to Delete: ");
        int id = scanner.nextInt();
        boolean removed = books.removeIf(b -> b.getId() == id);
        System.out.println(removed ? "Book deleted." : "Book not found.");
    }
        public List<Book> getBooks() {
        return new ArrayList<>(books); 
    }

    public Book getBookById(int id) {
        return books.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }
}
