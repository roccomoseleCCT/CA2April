/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libraryapplication;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author rocmos
 */
public class NotificationService implements Subject {
    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer o : observers) {
            o.update(message);
        }
    }

    public void checkOverdueBooks() {
        String sql = "SELECT * FROM transactions WHERE status = 'Borrowed' AND issue_date < CURDATE() - INTERVAL 7 DAY";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                addObserver(new UserNotifier(userId));
            }
            notifyObservers("Please return your overdue book!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}