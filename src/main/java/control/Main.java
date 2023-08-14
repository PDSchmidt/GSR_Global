package control;

import com.formdev.flatlaf.FlatDarkLaf;
import view.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            FlatDarkLaf.setup();
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