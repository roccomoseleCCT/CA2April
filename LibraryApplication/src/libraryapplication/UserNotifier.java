/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libraryapplication;

/**
 * @author rocmos
 */
public class UserNotifier implements Observer {
    private final int userId;

    public UserNotifier(int userId) {
        this.userId = userId;
    }

    @Override
    public void update(String message) {
        System.out.println("Notification to User " + userId + ": " + message);
    }
}
