package model.entity;
/**
 * Represents a Customer entity
 * @author Paul Schmidt
 */
public class Customer {
    /**
     * The customer's ID
     */
    private int cusID;
    /**
     * The customer's first name
     */
    private String first;
    /**
     * The customer's last name
     */
    private String last;
    /**
     * The customer's email
     */
    private String email;
    /**
     * The customer's home address
     */
    private String street;
    /**
     * The customer's Zipcode
     */
    private String ZIPCODE;
    /**
     * The customer's City
     */
    private String city;
    /**
     * The customer's State
     */
    private String state;
    /**
     * The customer's phone number
     */
    private String Phone;
    /**
     * The attributes associated with the customer entity
     */
    private String[] attributes;

    /**
     * Constructs a Customer object with the given ID and attributes
     * @param ID the ID of the Customer
     * @param attributes the attributes of the Customer
     */
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

    /**
     * Returns the Customer's Zipcode
     * @return the Customer's Zipcode
     */
    public String getZIPCODE() {
        return this.ZIPCODE;
    }
    /**
     * Returns the Customer's Street
     * @return the Customer's Street
     */
    public String getStreet() {
        return this.street;
    }
    /**
     * Returns the Customer's city
     * @return the Customer's city
     */
    public String getCity() {
        return this.city;
    }
    /**
     * Returns the Customer's State
     * @return the Customer's State
     */
    public String getState() {
        return this.state;
    }
    /**
     * Returns the Customer's phone number
     * @return the Customer's phone number
     */
    public String getPhone() {
        return this.Phone;
    }
    /**
     * Returns the Customer's first name
     * @return the Customer's first name
     */
    public String getFirst() {
        return this.first;
    }
    /**
     * Returns the Customer's last name
     * @return the Customer's last name
     */
    public String getLast() {
        return this.last;
    }
    /**
     * Returns the Customer's email
     * @return the Customer's email
     */
    public String getEmail() {
        return this.email;
    }
    /**
     * Returns the Customer's ID
     * @return the Customer's ID
     */
    public int getID(){
        return cusID;
    }
    /**
     * Returns the Customer's attributes
     * @return the Customer's attributes
     */
    public String[] getAttributes(){
        return attributes;
    }
}
