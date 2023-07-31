package control;

import com.formdev.flatlaf.FlatDarkLaf;
import view.LoginFrame;

public class Main {
    public static void main(String[] args) {
        FlatDarkLaf.setup();
        LoginFrame mainWindow = new LoginFrame("dark");
        mainWindow.setVisible(true);
        System.out.println("Hello world!");
    }
}