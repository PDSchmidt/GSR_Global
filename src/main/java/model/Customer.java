/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Paul
 */
public class Customer {
    private int cusID;
    private String first;
    private String last;
    private String email;
    private String street;
    private String ZIPCODE;
    private String city;
    private String state;
    private String Phone;
    private String[] attributes;
    public Customer(int ID, String[] attributes) {
        cusID = ID;
        first = attributes[0];
        last = attributes[1];
        email = attributes[2];
        street = attributes[3];
        ZIPCODE = attributes[4];
        city = attributes[5];
        state = attributes[6];
        Phone = attributes[7];
        this.attributes = attributes;
    }
    public int getID(){
        return cusID;
    }
    public String[] getAttributes(){
        return attributes;
    }
}
