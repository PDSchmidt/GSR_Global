package model;

import javax.swing.table.DefaultTableModel;

public class NewOrderTableModel extends DefaultTableModel {
    boolean[] canEdit;

    public NewOrderTableModel() {
        this.addColumn("ProductID");
        this.addColumn("ProductName");
        this.addColumn("UnitPrice");
        this.addColumn("Quantity");
        this.addColumn("SubTotal");
        canEdit = new boolean [] {
                false, false, false, false, false
        };
    }
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
    }
}
