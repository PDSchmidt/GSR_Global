package DataGeneration;

import java.util.GregorianCalendar;
import java.util.Random;

/**
 * A class that helps generate data for the Delivery table of the Database Schema.
 * @author Paul Schmidt
 */
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
        boolean delivered = false;
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(gc.YEAR, 2023);
        gc.set(gc.DAY_OF_MONTH, 1);
        gc.set(gc.MONTH, 1);
        if (orderdate.before(gc)) {
            delivered = true;
        } else {
            delivered = Math.random() < .20;
        }
        if(delivered) {
            deliveryDate = (GregorianCalendar) orderdate.clone();
            Random rand = new Random();
            int daystoship = rand.nextInt(1,15);
            deliveryDate.add(GregorianCalendar.DAY_OF_MONTH, daystoship);
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
