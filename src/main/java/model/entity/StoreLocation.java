package model.entity;

public class StoreLocation {
    private int storeID;
    private String storeName;
    private String street;
    private String ZIPCODE;
    private String phone;
    private String city;
    private String state;
    public StoreLocation(final int id, final String[] attributes) {
        storeID = id;
        storeName = attributes[0];
        street = attributes[1];
        ZIPCODE = attributes[2];
        phone = attributes[3];
        city = attributes[4];
        state = attributes[5];
    }
    public int getStoreID() {
        return storeID;
    }
    public String getStoreName() {
        return storeName;
    }
    public String getZIPCODE() {
        return ZIPCODE;
    }
    public String getStreet() {
        return street;
    }
    public String getPhone() {
        return phone;
    }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
    }
}
