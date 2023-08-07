package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class NewOrder {
    private int cusId;
    private NewDelivery delivery;
    private int LocationID;
    private List<NewOrderItem> items;
    private BigDecimal total;
    private AppCalender orderDate;
    private String orderStatus;
    public NewOrder(final int cusId, final NewDelivery delivery, final int LocationID, final List<NewOrderItem> items) {
        this.cusId = cusId;
        this.delivery = delivery;
        this.items = items;
        total = BigDecimal.ZERO;
        orderDate = new AppCalender();
        orderStatus = "PENDING PAYMENT";
        this.LocationID = LocationID;
    }
    public String getInsertStatement() {
        StringBuilder result = new StringBuilder();
        result.append("START TRANSACTION;\n");
        result.append("\tinsert into delivery (street, zipcode, deliverydate, DeliveryStatus)\n");
        result.append("\tVALUES\n");
        result.append("\t\t" + delivery.getFormattedValues() + ";\n");
        result.append("\tselect max(last_insert_id()) from delivery into @lastDeliveryID;\n");
        result.append("\tinsert into orders (OrderID, CustomerID, DeliveryID, LocationID, OrderDate, TotalCost, OrderStatus)\n");
        result.append("\tVALUES\n");
        result.append("\t\t(@lastDeliveryID, " + cusId + ", @lastDeliveryID, " + LocationID +
                ", \"" + orderDate.getFormatted() + "\", \"" + total.toString() + "\", \"" + orderStatus + "\");\n");
        if(!items.isEmpty()) {
            result.append("\tinsert into order_item (OrderID, ProductID, Quantity, SubTotalCost)\n");
            result.append("\tVALUES\n");
            for(int i = 0 ; i < items.size() - 1; i++) {
                result.append("\t\t" + items.get(i).getFormattedValues() + ",\n");
            }
            result.append("\t\t" + items.get(items.size() - 1).getFormattedValues() + ";");
        }
        return result.toString();
    }

}
