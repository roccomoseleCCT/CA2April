/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libraryapplication;

/**
 *
 * @author rocmos
 */
public class AdminUser extends User {
    public AdminUser(int id, String name, String email) {
        super(id, name, email, "Admin");
    }

    @Override
    public void displayMenu() {
        System.out.println("Admin Menu - Manage Books / Users");
    }
}

