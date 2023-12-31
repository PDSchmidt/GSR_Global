package view.inventorydb;

import control.DatabaseManager;
import java.sql.SQLException;
import javax.swing.JTable;

/**
 * Panel that generates and holds information about Vendors that supply the company with parts
 * @author Paul Schmidt
 */
public class VendorPanel extends javax.swing.JPanel {
    /**
     * The dbm that holds a connection to the database
     */
    private DatabaseManager dbm;
    /**
     * Creates new form VendorPanel without a connection to a database
     */
    public VendorPanel() {
        initComponents();
    }

    /**
     * Creates a VendorPanel with a connection to a database
     * @param dbm the connection to the desired database
     */
    public VendorPanel(final DatabaseManager dbm) {
        this.dbm = dbm;
        initComponents();
        generateVendors();
    }

    /**
     * Generates a JTable with information about the Vendors and adds it to the Panel
     */
    private void generateVendors() {
        JTable testTable = null;
        try {
            testTable = dbm.executeQueryGetTable("select VendorID, VendorName, Email, Phone, Street, City, State\n" +
                    "from vendors natural join zip;");
            testTable.setAutoCreateRowSorter(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (testTable != null) {
            addTable(testTable);
            System.out.println("VENDORS TABLE ADDED");
        } else {
            System.out.println("ISSUE CREATING VENDORS TABLE");
        }
    }

    /**
     * Removes the old table from this Panel and adds a New one to it
     * @param table the new table to display
     */
    private void addTable(final JTable table) {
        VendorsScrollPane.getViewport().remove(VendorsTable);
        VendorsTable = table;
        VendorsScrollPane.getViewport().add(VendorsTable);
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

        VendorsPanel = new javax.swing.JPanel();
        VendorsScrollPane = new javax.swing.JScrollPane();
        VendorsTable = new javax.swing.JTable();

        VendorsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "VendorID", "Name", "Email", "Address", "Phone"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        VendorsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        VendorsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        VendorsScrollPane.setViewportView(VendorsTable);

        javax.swing.GroupLayout VendorsPanelLayout = new javax.swing.GroupLayout(VendorsPanel);
        VendorsPanel.setLayout(VendorsPanelLayout);
        VendorsPanelLayout.setHorizontalGroup(
            VendorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VendorsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(VendorsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        VendorsPanelLayout.setVerticalGroup(
            VendorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VendorsPanelLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(VendorsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(VendorsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 689, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(VendorsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel VendorsPanel;
    private javax.swing.JScrollPane VendorsScrollPane;
    private javax.swing.JTable VendorsTable;
    // End of variables declaration//GEN-END:variables
}
