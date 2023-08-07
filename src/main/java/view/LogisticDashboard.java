/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import control.DatabaseManager;

/**
 *
 * @author Paul
 */
public class LogisticDashboard extends javax.swing.JPanel {
    private DatabaseManager dbm;
    /**
     * Creates new form LogisticDashboard
     */
    public LogisticDashboard(final DatabaseManager dbm) {
        this.dbm = dbm;
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

        LogisticsTabbedPanel = new javax.swing.JTabbedPane();
        InventoryPanel = new InventoryPanel(dbm);
        ActiveOrdersPanel = new OrderPanel("ACTIVE", dbm);
        ClosedOrdersPanel = new OrderPanel("CLOSED", dbm);
        VendorsPanel = new VendorPanel(dbm);

        setMinimumSize(new java.awt.Dimension(1280, 720));

        LogisticsTabbedPanel.addTab("Inventory", InventoryPanel);
        LogisticsTabbedPanel.addTab("Active Orders", ActiveOrdersPanel);
        LogisticsTabbedPanel.addTab("Closed Orders", ClosedOrdersPanel);
        LogisticsTabbedPanel.addTab("Vendors", VendorsPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LogisticsTabbedPanel))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LogisticsTabbedPanel)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void NewVendorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewVendorButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NewVendorButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.OrderPanel ActiveOrdersPanel;
    private view.OrderPanel ClosedOrdersPanel;
    private view.InventoryPanel InventoryPanel;
    private javax.swing.JTabbedPane LogisticsTabbedPanel;
    private view.VendorPanel VendorsPanel;
    // End of variables declaration//GEN-END:variables
}
