package control;

import model.Order;
import model.OrderItem;
import model.Product;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public class DataGenerator {

    public static void main(String[] args) {
        List<Order> orderList = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, 99.13));
        products.add(new Product(2, 51.87));
        products.add(new Product(3, 46.07));
        products.add(new Product(4, 7.05));
        products.add(new Product(5, 72.65));
        products.add(new Product(6, 72.35));
        products.add(new Product(7, 91.45));
        products.add(new Product(8, 89.55));
        products.add(new Product(9, 60.54));
        products.add(new Product(10, 79.65));
        List<String> addresses = new ArrayList<>();
        addresses.add("P.O. Box 149, 3093 Nunc Street");
        addresses.add("8805 Duis Rd.");
        addresses.add("442-9344 Rutrum Roadt");
        addresses.add("6597 Faucibus St.");
        addresses.add("6710 Aliquam Street");
        addresses.add("Ap #509-9834 Ultricies Rd.t");
        addresses.add("Ap #924-4831 Phasellus Street");
        addresses.add("335-824 Tellus Ave");
        addresses.add("756-5899 Tempus St.");
        addresses.add("P.O. Box 219, 9128 Et, Avenue");
        addresses.add("Ap #375-8750 Sapien, Av.");
        addresses.add("6887 Et Road");
        addresses.add("4828 Nec, Ave");
        addresses.add("Ap #938-3382 Semper. St.");
        addresses.add("6083 Arcu. Street");
        addresses.add("3927 Scelerisque Rd.");
        addresses.add("511-422 Rhoncus. St.");
        addresses.add("4246 Mauris St.");
        addresses.add("P.O. Box 260, 9703 Libero Av.");
        addresses.add("P.O. Box 775, 5302 Sit St.");

        List<String> zips = new ArrayList<>();
        zips.add("28164");
        zips.add("82641");
        zips.add("85522");
        zips.add("28164");
        zips.add("14662");
        zips.add("85522");
        zips.add("31424");
        zips.add("14631");
        zips.add("11256");
        zips.add("35373");
        zips.add("41614");
        zips.add("77472");
        zips.add("75625");
        zips.add("58671");
        zips.add("99657");
        zips.add("34486");
        zips.add("75097");
        zips.add("31424");
        zips.add("14662");
        zips.add("31424");


        for(int i = 1; i <= 100; i++) {
            Random rand = new Random();
            Order theOrder = new Order(i, addresses, zips);
            int numOI = rand.nextInt(4) + 1;
            Set<Integer> oiproducts = new HashSet<>();
            for(int j = 1; j <= numOI; j++) {
                int productID = rand.nextInt(10);
                if(!oiproducts.contains(productID)) {
                    OrderItem orderItem = new OrderItem(i, products.get(productID));
                    theOrder.add(orderItem);
                    oiproducts.add(productID);
                } else {
                    j--;
                }
            }
            orderList.add(theOrder);
        }
        for(Order oi : orderList) {
            System.out.println(oi.printOrder());
        }
        PrintStream output;
        try {
            output = new PrintStream("out.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        output.println("--------------DELIVERY---------------");
        for(Order theo : orderList) {
            output.println(theo.deliv + ",");
        }
        output.println("--------------ORDERS-----------------");
        for(Order theo : orderList) {
            output.println(theo + ",");
        }
        output.println("--------------ORDERITEM--------------");
        for(Order theo : orderList) {
            for(OrderItem oitem : theo.oi) {
                output.println(oitem + ", ");
            }
        }
    }
}
