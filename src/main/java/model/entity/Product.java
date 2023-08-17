package model.entity;

import control.DatabaseManager;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a Product that a customer can buy and is offered by the company.
 * @author Paul Schmidt
 */
public class Product implements Comparable<Product> {
    /**
     * The ID of the product
     */
    private int ID;
    /**
     * The name of the product
     */
    private String name;
    /**
     * The description of the product
     */
    private String desc;
    /**
     * The Price per unit of the product
     */
    private BigDecimal unitPrice;

    /**
     * Constructs a Product
     * @param id the ID unique to this product
     * @param name the name of this product
     * @param desc the description of this product
     * @param unitPrice the price per unit of this product
     */
    public Product(final int id, final String name, final String desc, final BigDecimal unitPrice) {
        ID = id;
        this.name = name;
        this.desc = desc;
        this.unitPrice = unitPrice;
    }

    /**
     * Queries the database for the list of available products and generates a collection of them
     * for use in the application
     * @param theProds the collection of products to populate
     * @param dbm the DatabaseManager that holds the connection to the database required to execute
     *            the query
     */
    public static void generateProducts(Map<String, Product> theProds, DatabaseManager dbm) {
        ResultSet rs = null;
        try {
            rs = dbm.executeQuery("select * from product;");
        } catch (SQLException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (rs != null) {
            try {
                while(rs.next()) {
                    Product curr = new Product(rs.getInt("ProductID"),
                            rs.getString("ProductName"),
                            rs.getString("ProductDescription"),
                        rs.getBigDecimal("UnitPrice"));
                    theProds.put(curr.getName(), curr);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Uses a result set from a database query in order to populate a collection with products
     * constructed from the result set. (This method is used when adding items to an order via
     * a search query executed on the database)
     * @param theProds the collection of products to populate
     * @param rs the result set used to create the Products
     */
    public static void generateProductsFromResult(Map<String, Product> theProds, ResultSet rs) {
        if (rs != null) {
            try {
                while(rs.next()) {
                    Product curr = new Product(rs.getInt("ProductID"),
                            rs.getString("ProductName"),
                            rs.getString("ProductDescription"),
                        rs.getBigDecimal("UnitPrice"));
                    theProds.put(curr.getName(), curr);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Returns the name of the Product
     * @return the name of the Product
     */
    public String getName(){
        return this.name;
    }
    /**
     * Returns the description of the Product
     * @return the description of the Product
     */
    public String getDescription(){
        return this.desc;
    }
    /**
     * Returns the unit price as a string of the Product
     * @return the unit price as a string of the Product
     */
    public String getUPtoString() {
        return unitPrice.toPlainString();
    }
    /**
     * Returns the unit price of the Product
     * @return the unit price of the Product
     */
    public BigDecimal getUP() {
        return this.unitPrice;
    }
    /**
     * Returns the unique ID of the Product
     * @return the unique ID of the Product
     */
    public int getID() {
        return this.ID;
    }

    /**
     * Makes Products sortable in collections by comparing their IDs
     * @param o the product to be compared.
     * @return negative integer if this is less than the other product, 0 if equal, positive otherwise
     */
    @Override
    public int compareTo(Product o) {
        return this.ID - o.ID;
    }
}
