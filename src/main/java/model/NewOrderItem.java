package model;

import java.math.BigDecimal;

public class NewOrderItem extends Product {
    private int quantity;
    private BigDecimal subtotal;

    public NewOrderItem(final Product theProduct, final Integer quantity) {
        super(theProduct.getID(),theProduct.getName(), theProduct.getDescription(), theProduct.getUP());
        this.quantity = quantity;
        this.subtotal = theProduct.getUP().multiply(new BigDecimal(quantity));
    }

    public String getFormattedValues() {
        return "(@lastDeliveryID, " + getID() + ", " + quantity + ", " + subtotal.toString() + ")";
    }
    public int getQuantity() {
        return this.quantity;
    }
    public BigDecimal getSubtotal() {
        return this.subtotal;
    }
}
