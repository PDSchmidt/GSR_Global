package DataGeneration;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class Delivery {

    private int id;
    private String street;
    private String zipcode;
    private GregorianCalendar deliveryDate;
    public String deliveryStatus;
    public Delivery(int id, GregorianCalendar orderdate, String street, String zipcode) {
        this.id = id;
        this.street = street;
        this.zipcode = zipcode;
        boolean delivered = Math.random() < .50;
//        System.out.print("Ordered:" + orderdate.get(orderdate.YEAR) + "-" +
//                (orderdate.get(orderdate.MONTH) + 1) + "-" + orderdate.get(orderdate.DAY_OF_MONTH));
        if(delivered) {
            deliveryDate = (GregorianCalendar) orderdate.clone();
            Random rand = new Random();
            int daystoship = rand.nextInt(1,15);
            deliveryDate.add(GregorianCalendar.DAY_OF_MONTH, daystoship);
//            System.out.print(" DELIVERED:" + deliveryDate.get(deliveryDate.YEAR) + "-" +
//                    (deliveryDate.get(deliveryDate.MONTH) + 1) + "-" + deliveryDate.get(deliveryDate.DAY_OF_MONTH));
            deliveryStatus = "DELIVERED";
        } else {
            deliveryStatus = "NOT SHIPPED";
        }
        System.out.println();
    }
    @Override
    public String toString(){
        String result = "(\"" + street + "\", \"" + zipcode + "\", ";
        if(deliveryDate != null) {
            result += "\"" + deliveryDate.get(deliveryDate.YEAR) + "-" +
                    (deliveryDate.get(deliveryDate.MONTH) + 1) + "-" + deliveryDate.get(deliveryDate.DAY_OF_MONTH) + "\"";
        } else {
            result += null;
        }
        return  result + ", \"" + deliveryStatus + "\")";
    }

}
