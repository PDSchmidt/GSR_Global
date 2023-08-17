package model.entity;

/**
 * Represents the StoreLocation entity in the database. Used for placing new orders
 * and managing inventory.
 * @author Paul Schmidt
 */
public class StoreLocation {
    /**
     * The unique identifier for the Store
     */
    private int storeID;
    /**
     * The name of the Store
     */
    private String storeName;
    /**
     * The name of the Street the store is located at
     */
    private String street;
    /**
     * The zipcode of the Store
     */
    private String ZIPCODE;
    /**
     * The main phone number for the store
     */
    private String phone;
    /**
     * The city the Store is located in
     */
    private String city;
    /**
     * The State the Store is located in
     */
    private String state;

    /**
     * Constructs a store location entity with the given ID and attributes
     * @param id the unique ID of the store
     * @param attributes the array that holds the other attributes for the store.
     *                   Must be of length 6 with StoreName, Street, ZIPCODE, Phone,
     *                   City, State listed in that order
     */
    public StoreLocation(final int id, final String[] attributes) {
        storeID = id;
        storeName = attributes[0];
        street = attributes[1];
        ZIPCODE = attributes[2];
        phone = attributes[3];
        city = attributes[4];
        state = attributes[5];
    }

    /**
     * Returns the unique ID of this store
     * @return the unique ID of this store
     */
    public int getStoreID() {
        return storeID;
    }

    /**
     * Returns the Name of this Store
     * @return the Name of this Store
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * Returns the ZIPCODE of this store
     * @return the ZIPCODE of this store
     */
    public String getZIPCODE() {
        return ZIPCODE;
    }

    /**
     * Returns the Street of this store
     * @return the Street of this store
     */
    public String getStreet() {
        return street;
    }

    /**
     * Returns the Phone number for this store
     * @return the Phone number for this store
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Returns the City of this store
     * @return the City of this store
     */
    public String getCity() {
        return city;
    }

    /**
     * Returns the State of this store
     * @return the State of this store
     */
    public String getState() {
        return state;
    }
}
