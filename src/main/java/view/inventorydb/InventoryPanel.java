/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.inventorydb;

import control.DatabaseManager;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 *
 * @author Paul
 */
public class InventoryPanel extends javax.swing.JPanel {
    private DatabaseManager dbm;
    /**
     * Creates new form InventoryPanel
     */
    public InventoryPanel() {
        initComponents();
    }
    public InventoryPanel(final DatabaseManager dbm) {
        this.dbm = dbm;
        initComponents();
        generateInventory();
    }
    private void generateInventory() {
        JTable testTable = null;
        try {
            testTable = dbm.executeQueryGetTable("select * from inventory;");
            testTable.setAutoCreateRowSorter(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (testTable != null) {
            addTable(testTable);
            System.out.println("ACTIVE ORDERS TABLE ADDED");
        } else {
            System.out.println("ISSUE CREATING ACTIVE ORDERS TABLE");
        }
    }
    private void addTable(final JTable table) {
        InventoryScrollPane.getViewport().remove(InventoryTable);
        InventoryTable = table;
        InventoryScrollPane.getViewport().add(InventoryTable);
        revalidate();
        repaint();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        InventoryPanel = new javax.swing.JPanel();
        InventoryScrollPane = new javax.swing.JScrollPane();
        InventoryTable = new javax.swing.JTable();
        ApplyFiltersInventoryButton = new javax.swing.JButton();
        ResetFiltersInventoryButton = new javax.swing.JButton();

        InventoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "PartNumber", "PartName", "PartDescription", "Cost", "Stock", "Required"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        InventoryTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        InventoryTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        InventoryScrollPane.setViewportView(InventoryTable);

        ApplyFiltersInventoryButton.setText("Apply Filters...");

        ResetFiltersInventoryButton.setText("Reset Filters");

        javax.swing.GroupLayout InventoryPanelLayout = new javax.swing.GroupLayout(InventoryPanel);
        InventoryPanel.setLayout(InventoryPanelLayout);
        InventoryPanelLayout.setHorizontalGroup(
            InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InventoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(InventoryScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(InventoryPanelLayout.createSequentialGroup()
                        .addComponent(ApplyFiltersInventoryButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ResetFiltersInventoryButton)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        InventoryPanelLayout.setVerticalGroup(
            InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InventoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ApplyFiltersInventoryButton)
                    .addComponent(ResetFiltersInventoryButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(InventoryScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1280, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(InventoryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 689, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(InventoryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ApplyFiltersInventoryButton;
    private javax.swing.JPanel InventoryPanel;
    private javax.swing.JScrollPane InventoryScrollPane;
    private javax.swing.JTable InventoryTable;
    private javax.swing.JButton ResetFiltersInventoryButton;
    // End of variables declaration//GEN-END:variables
}
