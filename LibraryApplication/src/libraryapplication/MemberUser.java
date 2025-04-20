/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libraryapplication;

/**
 *
 * @author rocmos
 */
public class MemberUser extends User {
    public MemberUser(int id, String name, String email) {
        super(id, name, email, "Member");
    }

    @Override
    public void displayMenu() {
        System.out.println("Member Menu - Borrow/Return Books");
    }
}