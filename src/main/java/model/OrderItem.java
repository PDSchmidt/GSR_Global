package model;

import java.math.BigDecimal;

public class OrderItem extends Product{
    private int quantity;
    private BigDecimal subtotal;
    public OrderItem(int id, String name, String desc, BigDecimal unitPrice, int quantity, BigDecimal subtotal) {
        super(id, name, desc, unitPrice);
        this.quantity = quantity;
        this.subtotal = new BigDecimal(subtotal.toString());
    }
}
