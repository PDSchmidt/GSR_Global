package DataGeneration;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Order {
    public List<OrderItem> oi;
    public Delivery deliv;
    private int id;
    private int cusID;
    private int locID;
    private double totalCost;
    private GregorianCalendar orderDate;
    private String orderStatus;

    public Order(int id, List<String> addresses, List<String> zips){
        this.id = id;
        oi = new ArrayList<OrderItem>();
        totalCost = 0.0;
        Random rand = new Random();
        locID = rand.nextInt(10) + 1;
        cusID = rand.nextInt(20) + 1;
        orderDate = RandomDate.randomDate();
        deliv = new Delivery(id, orderDate, addresses.get(cusID-1), zips.get(cusID-1));
        if(deliv.deliveryStatus.equals("DELIVERED")) {
            orderStatus = "COMPLETE";
        } else {
            int status = rand.nextInt(3) + 1;
            switch (status) {
                case 1:
                    orderStatus = "PENDING PAYMENT";
                    break;
                case 2:
                    orderStatus = "READY TO SHIP";
                    break;
                case 3:
                    orderStatus = "PROCESSING";
                    break;
            }
        }
    }

    public void add(OrderItem theItem){
        oi.add(theItem);
        totalCost += theItem.subtotal;
    }
    public String printOrder() {
        StringBuilder s = new StringBuilder();
        s.append(this.toString());
        s.append("\n\tDelivery:");
        s.append("\n\t" + this.deliv);
        for(OrderItem item : this.oi) {
            s.append("\n\t\t" + item.toString());
        }
        return s.toString();
    }
    private String orderDateString() {
        return orderDate.get(orderDate.YEAR) + "-" +
                (orderDate.get(orderDate.MONTH) + 1) + "-" + orderDate.get(orderDate.DAY_OF_MONTH);
    }
    @Override
    public String toString(){
        String cost = String.format("%.2f", totalCost);
        return "("+ cusID + ", " + id + ", " + locID + ", \"" +
                orderDateString() + "\", " + cost + ", " + "\"" + orderStatus + "\")";
    }
}
