package DataGeneration;

import java.util.Random;

public class OrderItem {
    public static Random rand = new Random();
    private int orderid;
    private Product product;
    private int quantity;
    public double subtotal;
    public OrderItem(int theOrder, Product theProduct) {
        orderid = theOrder;
        product = theProduct;
        quantity = rand.nextInt(4) + 1;
        subtotal = quantity * product.cost;
    }
    @Override
    public String toString() {
        String s = String.format("%.2f", subtotal);
        return "(" + orderid + ", " + product.id + ", " + quantity + ", " + s + ")";
    }
}
