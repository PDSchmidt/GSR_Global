package model.entity;

/**
 * Represents a new Delivery Entity for the purpose of insertion into the database
 * @author Paul Schmidt
 */
public class NewDelivery {
    /**
     * The zipcode to ship to
     */
    private String ZIPCODE;
    /**
     * The street to ship to
     */
    private String street;
    /**
     * The date of the delivery
     */
    private String deliveryDate;
    /**
     * The status of the delivery
     */
    private String deliveryStatus;

    /**
     * Constructs a Delivery entity using the given Zipcode and Street
     * @param ZIPCODE the zipcode to deliver to
     * @param street the street to deliver to
     */
    public NewDelivery(final String ZIPCODE, final String street) {
        this.ZIPCODE = ZIPCODE;
        this.street = street;
        deliveryDate = null;
        deliveryStatus = "NOT SHIPPED";
    }

    /**
     * RReturns the attributes of this Delivery entity in the form of an insert statement
     * NOTE: This is not user-inputted information.
     * @return the SQL statement
     */
    public String getFormattedValues() {
        return "(\"" + street + "\", \"" + ZIPCODE + "\", null, \"" + deliveryStatus + "\")";
    }
}
