package control;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import view.LoginFrame;

import javax.swing.*;
import java.awt.*;

/**
 * A small application for manufacturing companies that helps track and manipulate orders, customers, and inventory.
 * The application utilizes JDBC to connect to an online mySQL Database that stores the company's information.
 * This class is a launch point for the application.
 * @author Paul Schmidt
 */
public class Main {
    public static void main(String[] args) {
        try {
            FlatDarkLaf.setup();
            UIManager.put("Table.showVerticalLines", true);
            UIManager.put("Table.showHorizontalLines", true);
            UIManager.put("Table.cellMargins", new Insets(0,10,0,10));
            FlatLaf.updateUI();
        } catch (Exception e) {
            JFrame err = new JFrame();
            err.setLocationRelativeTo(null);
            err.setVisible(true);
            err.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JOptionPane.showMessageDialog(new JFrame(), "Error setting look and feel.");
        }
        LoginFrame mainWindow = new LoginFrame("dark");
        mainWindow.setVisible(true);
    }
}