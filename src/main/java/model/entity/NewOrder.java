package model.entity;

import model.AppCalender;
import java.math.BigDecimal;
import java.util.List;
/**
 * Represents a New Order to be inserted into the Database.
 * @author Paul Schmidt
*/
public class NewOrder {
    /**
     * The ID of the customer that placed the order
     */
    private final int cusId;
    /**
     * The Delivery entity associated with this Order
     */
    private final NewDelivery delivery;
    /**
     * The Location ID of the store associated with this Order.
     */
    private final int LocationID;
    /**
     * The OrderItems associated with this Order
     */
    private final List<NewOrderItem> items;
    /**
     * The total cost to the customer of this Order
     */
    private BigDecimal total;
    /**
     * The date that the order was placed
     */
    private final AppCalender orderDate;
    /**
     * The Status of this order
     */
    private final String orderStatus;

    /**
     * Constructs an Order to be inserted into the database
     * @param cusId the ID of the customer
     * @param delivery the delivery of this order
     * @param LocationID the ID of the store who will fulfill the order
     * @param items the list of items associated with the order
     */
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
