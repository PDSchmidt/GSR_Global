package Testing;

import javax.swing.table.AbstractTableModel;

public class ResultTableModel extends AbstractTableModel {
    private Object[] myColumnNames;
    private Object[][] myData;
    private int numColumns;
    private int numRows;
    public ResultTableModel(final Object[][] data, final Object[] columnNames, final int numRow) {
        myColumnNames = columnNames;
        numColumns = myColumnNames.length;
        numRows = numRow;
        myData = data;
    }
    @Override
    public int getRowCount() {
        return numRows;
    }

    @Override
    public int getColumnCount() {
        return numColumns;
    }
    @Override
    public String getColumnName(int columnIndex) {
        return (String)myColumnNames[columnIndex];
    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (numRows == 0) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return myData[rowIndex][columnIndex];
    }
}
