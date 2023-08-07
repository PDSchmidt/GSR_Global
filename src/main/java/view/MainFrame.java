/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import control.DatabaseManager;

import java.awt.EventQueue;


/**
 *
 * @author Paul
 */
public class MainFrame extends javax.swing.JFrame {
    private String currentTheme;
    private MainFrame myRef;
    private DatabaseManager dbm;
    /**
     * Creates new form NewJFrame
     */
    public MainFrame( final String theme, final DatabaseManager dbm) {
        currentTheme = theme;
        this.dbm = dbm;
        myRef = this;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MenuBar = new javax.swing.JMenuBar();
        FileMenu = new javax.swing.JMenu();
        OptionsMenu = new javax.swing.JMenu();
        LightDarkModeToggle = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        BackToMenuItem = new javax.swing.JMenuItem();
        ExitMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1280, 720));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        FileMenu.setText("File");
        MenuBar.add(FileMenu);

        OptionsMenu.setText("Options");

        LightDarkModeToggle.setText("Toggle Theme");
        LightDarkModeToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LightDarkModeToggleActionPerformed(evt);
            }
        });
        OptionsMenu.add(LightDarkModeToggle);
        OptionsMenu.add(jSeparator1);

        BackToMenuItem.setText("Back to Login");
        BackToMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackToMenuItemActionPerformed(evt);
            }
        });
        OptionsMenu.add(BackToMenuItem);

        ExitMenuItem.setText("Exit Application");
        ExitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitMenuItemActionPerformed(evt);
            }
        });
        OptionsMenu.add(ExitMenuItem);

        MenuBar.add(OptionsMenu);

        setJMenuBar(MenuBar);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void LightDarkModeToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LightDarkModeToggleActionPerformed
        if (currentTheme.equals("light")) {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    FlatDarkLaf.setup();
                    FlatLaf.updateUI();
                }
            });
            currentTheme = "dark";
        } else {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    FlatLightLaf.setup();
                    FlatLaf.updateUI();
                }
            });
            currentTheme = "light";
        }
    }//GEN-LAST:event_LightDarkModeToggleActionPerformed

    private void ExitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitMenuItemActionPerformed
        myRef.dispose();
        System.exit(0);
    }//GEN-LAST:event_ExitMenuItemActionPerformed

    private void BackToMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackToMenuItemActionPerformed
        LoginFrame login = new LoginFrame(currentTheme);
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BackToMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem BackToMenuItem;
    private javax.swing.JMenuItem ExitMenuItem;
    private javax.swing.JMenu FileMenu;
    private javax.swing.JMenuItem LightDarkModeToggle;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JMenu OptionsMenu;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
