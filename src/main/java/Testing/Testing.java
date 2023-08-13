package Testing;

import com.formdev.flatlaf.FlatDarkLaf;
import control.DatabaseManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

public class Testing {
    public static void main(String[] args) {
        FlatDarkLaf.setup();
        DatabaseManager dbm = new DatabaseManager();
        TestingGUI gui = new TestingGUI(dbm);
        gui.setVisible(true);
        ResultSet rs = null;
        try {
            rs = dbm.executeQuery("select orders.OrderID, product.ProductName, order_item.quantity, product.UnitPrice, order_item.SubTotalCost, orders.TotalCost  from\n" +
                    "    orders left outer join order_item on (orders.OrderID = order_item.OrderID)\n" +
                    "    right outer join product on (order_item.ProductID = product.ProductID)\n" +
                    "    order by orders.OrderID");
        } catch (SQLException ex) {
            Logger.getLogger(Testing.class.getName()).log(Level.SEVERE, null, ex);
        }
        JTable testTable = null;
        if (rs != null) {
            
            try {
                testTable = dbm.createTable(rs);
                testTable.setAutoCreateRowSorter(true);
                System.out.println("TABLE CREATED");
            } catch (SQLException ex) {
                Logger.getLogger(Testing.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (testTable != null) {
                gui.addTable(testTable);
                System.out.println("TABLE ADDED");
                gui.revalidate();
                gui.repaint();
            }
        }
    }
}
