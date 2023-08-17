package model;

import javax.swing.table.DefaultTableModel;

/**
 * Class used to create the JTable model for New Orders.
 * @author Paul Schmidt
 */
public class NewOrderTableModel extends DefaultTableModel {
    /**
     * An array that represents whether the columns should be editable
     */
    boolean[] canEdit;

    /**
     * Constructs a NewOrderTableModel with default properties
     */
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

    /**
     * Whether this cell is editable.
     * @param rowIndex             the row whose value is to be queried
     * @param columnIndex          the column whose value is to be queried
     * @return true if the cell is editable, false otherwise.
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
    }
}
