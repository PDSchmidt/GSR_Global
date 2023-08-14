package model.entity;

import model.AppCalender;

import java.math.BigDecimal;
import java.util.List;
/**
*/
public class NewOrder {
    private final int cusId;
    private final NewDelivery delivery;
    private final int LocationID;
    private final List<NewOrderItem> items;
    private BigDecimal total;
    private final AppCalender orderDate;
    private final String orderStatus;
    public NewOrder(final int cusId, final NewDelivery delivery, final int LocationID, final List<NewOrderItem> items) {
    this.cusId = cusId;
        this.delivery = delivery;
        this.items = items;
        total = BigDecimal.ZERO;
        for(NewOrderItem item : this.items) {
            total = total.add(item.getSubtotal());
        }
        orderDate = new AppCalender();
        orderStatus = "PENDING PAYMENT";
        this.LocationID = LocationID;
    }

    /**
     * Prepares the SQL Statement necessary to insert this Order into the database as a new
     * order with all the appropriate statements for the line items associated with the order
     * as well.
     * Uses a Transaction to create the multiple statements. This ensures transaction should be
     * rolled back if any part of the overall statement fails.
     * Note: Not possible for SQL Injection, no user input here or in the creation of the data used
     * @return String The String representation of the SQL Insert Statement
     */
    public String getInsertStatement() {
        StringBuilder result = new StringBuilder();
        result.append("START TRANSACTION;\n");
        result.append("\tinsert into delivery (Street, ZIPCODE, DeliveryDate, DeliveryStatus)\n");
        result.append("\tVALUES\n");
        result.append("\t\t").append(delivery.getFormattedValues()).append(";\n");
        result.append("\tselect max(last_insert_id()) from delivery into @lastDeliveryID;\n");
        result.append("\tinsert into orders (OrderID, CustomerID, DeliveryID, LocationID, OrderDate, TotalCost, OrderStatus)\n");
        result.append("\tVALUES\n");
        result.append("\t\t(@lastDeliveryID, ").append(cusId).append(", @lastDeliveryID, ").append(LocationID).append(", \"")
                .append(orderDate.getFormatted()).append("\", \"").append(total.toString()).append("\", \"")
                .append(orderStatus).append("\");\n");
        //If there are order items
        if(!items.isEmpty()) {
            result.append("\tinsert into order_item (OrderID, ProductID, Quantity, SubTotalCost)\n");
            result.append("\tVALUES\n");
            //For each order item, append its information in the form of an insert statement
            for(int i = 0 ; i < items.size() - 1; i++) {
                result.append("\t\t").append(items.get(i).getFormattedValues()).append(",\n");
            }
            result.append("\t\t").append(items.get(items.size() - 1).getFormattedValues()).append(";\n");
        }
        result.append("COMMIT;");
        return result.toString();
    }
}
