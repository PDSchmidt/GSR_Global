package model.entity;

import java.math.BigDecimal;

/**
 * Represents a line item in an Order
 * @author Paul Schmidt
*/
public class NewOrderItem extends Product {
    /**
     * The quantity of this Product
     */
    private int quantity;
    /**
     * The subtotal of this line item
     */
    private BigDecimal subtotal;

    /**
     * Constructs an OrderItem using the given Product and Quantity
     * @param theProduct the Product to be ordered
     * @param quantity the quantity to be ordered
     */

    public NewOrderItem(final Product theProduct, final Integer quantity) {
        super(theProduct.getID(),theProduct.getName(), theProduct.getDescription(), theProduct.getUP());
        this.quantity = quantity;
        this.subtotal = theProduct.getUP().multiply(new BigDecimal(quantity));
    }

    /**
     * Returns the insert statement necessary to insert this order item into the database
     * @return the insert statement necessary to insert this order item into the database
     */
    public String getFormattedValues() {
        return "(@lastDeliveryID, " + getID() + ", " + quantity + ", " + subtotal.toString() + ")";
    }

    /**
     * Returns the quantity of the product to buy
     * @return the quantity of the product to buy
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Returns the subtotal of this Order Item
     * @return the subtotal of this Order Item
     */
    public BigDecimal getSubtotal() {
        return this.subtotal;
    }

    /**
     * Sets the quantity of this Order Item
     * @param newQuantity the new quantity
     */
    public void setQuantity(final int newQuantity) {
        quantity = newQuantity;
        subtotal = new BigDecimal(quantity).multiply(this.getUP());
    }
}
