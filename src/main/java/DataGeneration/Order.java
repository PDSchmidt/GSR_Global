package model;

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
    private GregorianCalendar date;
    private String orderStatus;

    public Order(int id, List<String> addresses, List<String> zips){
        this.id = id;
        oi = new ArrayList<OrderItem>();
        totalCost = 0.0;
        Random rand = new Random();
        locID = rand.nextInt(10) + 1;
        cusID = rand.nextInt(20) + 1;
        date = RandomDate.randomDate();
        deliv = new Delivery(id, date, addresses.get(cusID-1), zips.get(cusID-1));
        if(deliv.deliveryStatus.equals("DELIVERED")) {
            orderStatus = "COMPLETE";
        } else {
            int status = rand.nextInt(1, 4);
            switch (status) {
                case 1:
                    orderStatus = "PENDING PAYMENT";
                    break;
                case 2:
                    orderStatus = "READY TO SHIP";
                    break;
                case 3:
                    orderStatus = "SHIPPED";
                    break;
                case 4:
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
    @Override
    public String toString(){
        String cost = String.format("%.2f", totalCost);
        return "("+ cusID + ", " + id + ", " + locID + ", " + cost + ", " + "\"" + orderStatus + "\")";
    }
}
