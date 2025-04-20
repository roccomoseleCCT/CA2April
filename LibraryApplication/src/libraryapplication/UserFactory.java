/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libraryapplication;

/**
 * @author rocmos
 */
public class UserFactory {

    public static User createUser(String role, int id, String name, String email) {
        if (role.equalsIgnoreCase("Admin")) {
            return new AdminUser(id, name, email);
        } else {
            return new MemberUser(id, name, email);
        }
    }
}
