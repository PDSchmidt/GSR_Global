package model.entity;
/**
*/
public class NewDelivery {
    private String ZIPCODE;
    private String street;
    private String deliveryDate;
    private String deliveryStatus;
    public NewDelivery(final String ZIPCODE, final String street) {
        this.ZIPCODE = ZIPCODE;
        this.street = street;
        deliveryDate = null;
        deliveryStatus = "NOT SHIPPED";
    }
    public String getFormattedValues() {
        return "(\"" + street + "\", \"" + ZIPCODE + "\", null, \"" + deliveryStatus + "\")";
    }
}
