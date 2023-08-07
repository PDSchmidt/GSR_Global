/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import control.DatabaseManager;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Paul
 */
public class Product implements Comparable<model.Product> {
    private int ID;
    private String name;
    private String desc;
    private BigDecimal unitPrice;
    public Product(final int id, final String name, final String desc, final BigDecimal unitPrice) {
        ID = id;
        this.name = name;
        this.desc = desc;
        this.unitPrice = unitPrice;
    }
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
    public String getName(){
        return this.name;
    }
    public String getDescription(){
        return this.desc;
    }
    public String getUPtoString() {
        return unitPrice.toPlainString();
    }
    public BigDecimal getUP() {
        return this.unitPrice;
    }
    public int getID() {
        return this.ID;
    }

    @Override
    public int compareTo(Product o) {
        return this.ID - o.ID;
    }
}
