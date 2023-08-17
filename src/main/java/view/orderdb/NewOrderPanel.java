package view.orderdb;

import control.DatabaseManager;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import model.*;
import model.entity.*;

/**
 * Custom JPanel that lets the user select a store location, customer, and products to generate a
 * new order for entry into the database
 * @author Paul Schmidt
 */
public class NewOrderPanel extends javax.swing.JPanel {
    /**
     * The selected Customer
     */
    private Customer selectedCustomer;
    /**
     * The selected Store
     */
    private StoreLocation selectedStore;
    /**
     * The DatabaseManager that holds a connection to the database
     */
    private DatabaseManager dbm;
    /**
     * The collection of Customers that match the search criteria
     */
    private Map<String,Customer> custs;
    /**
     * The collection of Products in the Order
     */
    private Map<String, Product> product;
    /**
     * The collection of Stores available to the User
     */
    private Map<String, StoreLocation> stores;
    /**
     * The Custom JFrame that the user can use to select Products
     */
    private ItemSelectFrame itemSelection;
    /**
     * The Collection of Order Items for the Order
     */
    private Map<Integer, NewOrderItem> orderItems;
    /**
     * Creates new form NewOrderPanel
     */
    public NewOrderPanel() {
        initComponents();
    }

    /**
     * Creates a NewOrderPanel with the given DatabaseManager
     * @param dbm the DatabaseManager that stores a connection to the database
     */
    public NewOrderPanel(final DatabaseManager dbm) {
        this.dbm = dbm;
        product = new HashMap<>();
        orderItems = new HashMap<>();
        stores = new HashMap<>();
        Product.generateProducts(product, dbm);
        itemSelection =null;
        selectedCustomer = null;
        selectedStore = null;
        initComponents();
        try {
            populateStoreComboBox();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("HERE");
    }

    /**
     * Adds a NewOrderItem to the order
     * @param theItem the Item to add to the order
     */
    public void addOrderItem(NewOrderItem theItem) {
        orderItems.put(theItem.getID(), theItem);
        updateOrderTable();
    }

    /**
     * Updates the information about the order including the items and total
     */
    public void updateOrderTable() {
        JTable temp = OrderItemsTable;
        NewOrderTableModel model = new NewOrderTableModel();
        for(NewOrderItem item : orderItems.values()) {
            model.addRow(new Object[]{item.getID(), item.getName(), item.getUPtoString(), item.getQuantity(), item.getSubtotal()});
        }
        OrderItemsTable = new JTable(model);
        OrderItemsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ItemsScrollPane.getViewport().remove(temp);
        ItemsScrollPane.getViewport().add(OrderItemsTable);
        updateTotal();
        revalidate();
        repaint();
    }

    /**
     * Updates the total cost of all order items
     */
    private void updateTotal() {
        BigDecimal total = new BigDecimal("0.00");
        for(NewOrderItem item : orderItems.values()) {
            total = total.add(item.getSubtotal());
        }
        OrderSubtotalLabel.setText(NumberFormat.getCurrencyInstance().format(total));
        OrderSubtotalLabel.setSize(133, 25);
    }

    /**
     * Uses a SQL query to populate a dropdown list of all available stores in which an
     * order can be placed with
     * @throws SQLException of there is an issue querying the database
     */
    private void populateStoreComboBox() throws SQLException {
        StoreLocationComboBox.removeAllItems();
        String query = "select * from storelocations natural join zip;";
        ResultSet rs = null;
        try {
            rs = dbm.executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if(rs != null) {
            while(rs.next()) {
                int id = rs.getInt("LocationID");
                String[] attr = new String[6];
                attr[0] = rs.getString("LocationName");
                attr[1] = rs.getString("Street");
                attr[2] = rs.getString("ZIPCODE");
                attr[3] = rs.getString("Phone");
                attr[4] = rs.getString("City");
                attr[5] = rs.getString("State");
                StoreLocation curr = new StoreLocation(id, attr);
                stores.put(curr.getStoreName(), curr);
            }
        }
        List<String> locnames = new ArrayList<>();
        for(String name : stores.keySet()) {
            locnames.add(name);
        }
        Collections.sort(locnames);
        for (int i = 0; i < locnames.size(); i++) {
            StoreLocationComboBox.addItem(locnames.get(i));
        }
    }

    /**
     * Uses a ResultSet to generate the list of customers that match the search criteria
     * set by the user and queried by the database
     * @param results the Results of the database query
     */
    private void populateComboBox(final ResultSet results) {
        CustomerComboBox.removeAllItems();
        try {
            custs = new HashMap<>();
            while(results.next()) {
                String person = results.getString("LastName") + ", " 
                        + results.getString("FirstName");
                String[] attributes = new String[8];
                attributes[0] = results.getString("FirstName");
                attributes[1] = results.getString("LastName");
                attributes[2] = results.getString("Email");
                attributes[3] = results.getString("Street");
                attributes[4] = results.getString("ZIPCODE");
                attributes[5] = results.getString("City");
                attributes[6] = results.getString("State");
                attributes[7] = results.getString("Phone");
                Customer curr = new Customer(results.getInt("CustomerID"), attributes);
                custs.put(person, curr);
                CustomerComboBox.addItem(person);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Updates the Customer information display based on the selected Customer
     * @param theName the Name of the Customer to display information about
     */
    private void updateCustomerInfoDisplay(final String theName) {
        selectedCustomer = custs.get(theName);
        String[] attributes = selectedCustomer.getAttributes();
        CustFirstName.setText(attributes[0]);
        CustLastName.setText(attributes[1]);
        CustEmail.setText(attributes[2]);
        CustStreet.setText(attributes[3]);
        CustZip.setText(attributes[4]);
        CustCity.setText(attributes[5]);
        CustState.setText(attributes[6]);
        CustPhone.setText(attributes[7]);
    }

    /**
     * Uses the selected store to update the displayed store information
     * @param theName the name of the selected store
     */
    private void updateLocationInfoDisplay(final String theName) {
        selectedStore = stores.get(theName);
        StoreNameLabel.setText(selectedStore.getStoreName());
        StoreStreetLabel.setText(selectedStore.getStreet());
        StoreZIPCODELabel.setText(selectedStore.getZIPCODE());
        StoreCityLabel.setText(selectedStore.getCity());
        StoreStateLabel.setText(selectedStore.getState());
        StorePhoneLabel.setText(selectedStore.getPhone());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        NewOrderPanel = new javax.swing.JPanel();
        CustSelectLabel = new javax.swing.JLabel();
        CustomerComboBox = new javax.swing.JComboBox<>();
        FirstNameField = new javax.swing.JTextField();
        LastNameField = new javax.swing.JTextField();
        FirstNameLabel = new javax.swing.JLabel();
        LastNameLabel = new javax.swing.JLabel();
        SearchForCustomerButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        CustInfoLabel = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        CustFirstNameLabel = new javax.swing.JLabel();
        CustLastNameLabel = new javax.swing.JLabel();
        CustFirstName = new javax.swing.JLabel();
        CustLastName = new javax.swing.JLabel();
        EmailLabel = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        CustEmail = new javax.swing.JLabel();
        PhoneLabel = new javax.swing.JLabel();
        CustPhone = new javax.swing.JLabel();
        AddressLabel = new javax.swing.JLabel();
        StreetLabel = new javax.swing.JLabel();
        CustStreet = new javax.swing.JLabel();
        CityLabel = new javax.swing.JLabel();
        CustCity = new javax.swing.JLabel();
        StateLabel = new javax.swing.JLabel();
        CustState = new javax.swing.JLabel();
        ZipLabel = new javax.swing.JLabel();
        CustZip = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        OrderItemsLabel = new javax.swing.JLabel();
        DeleteItemButton = new javax.swing.JButton();
        EditItemButton = new javax.swing.JButton();
        AddItemButton = new javax.swing.JButton();
        SubmitOrderButton = new javax.swing.JButton();
        ItemsScrollPane = new javax.swing.JScrollPane();
        OrderItemsTable = new javax.swing.JTable();
        SubtotalLabel = new javax.swing.JLabel();
        OrderSubtotalLabel = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        StoreLocationComboBox = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        CustSelectLabel1 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        CustFirstNameLabel1 = new javax.swing.JLabel();
        CustInfoLabel1 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        StoreNameLabel = new javax.swing.JLabel();
        PhoneLabel1 = new javax.swing.JLabel();
        StorePhoneLabel = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        AddressLabel1 = new javax.swing.JLabel();
        StreetLabel1 = new javax.swing.JLabel();
        StoreStreetLabel = new javax.swing.JLabel();
        StoreCityLabel = new javax.swing.JLabel();
        CityLabel1 = new javax.swing.JLabel();
        StateLabel1 = new javax.swing.JLabel();
        StoreStateLabel = new javax.swing.JLabel();
        ZipLabel1 = new javax.swing.JLabel();
        StoreZIPCODELabel = new javax.swing.JLabel();

        CustSelectLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        CustSelectLabel.setText("Customer Selection");

        FirstNameLabel.setText("First Name");

        LastNameLabel.setText("Last Name");

        SearchForCustomerButton.setText("Search");
        SearchForCustomerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchForCustomerButtonActionPerformed(evt);
            }
        });

        CustInfoLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        CustInfoLabel.setText("Customer Info");

        CustFirstNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        CustFirstNameLabel.setText("First Name");

        CustLastNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        CustLastNameLabel.setText("Last Name");

        CustFirstName.setText("(none selected)");

        CustLastName.setText("(none selected)");

        EmailLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        EmailLabel.setText("Email:");

        CustEmail.setText("(none selected)");

        PhoneLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        PhoneLabel.setText("Phone:");

        CustPhone.setText("(###-###-####)");

        AddressLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        AddressLabel.setText("Address");

        StreetLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        StreetLabel.setText("Street:");

        CustStreet.setText("(street address-----------------------)");

        CityLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        CityLabel.setText("City:");

        CustCity.setText("(city---------)");

        StateLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        StateLabel.setText("| State:");

        CustState.setText("##");

        ZipLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ZipLabel.setText("| ZIP:");

        CustZip.setText("#####");

        OrderItemsLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        OrderItemsLabel.setText("Order Items");

        DeleteItemButton.setText("Delete Selected");
        DeleteItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteItemButtonActionPerformed(evt);
            }
        });

        EditItemButton.setText("Edit Selected");
        EditItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditItemButtonActionPerformed(evt);
            }
        });

        AddItemButton.setText("Add Item");
        AddItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddItemButtonActionPerformed(evt);
            }
        });

        SubmitOrderButton.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        SubmitOrderButton.setText("Submit Order");
        SubmitOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitOrderButtonActionPerformed(evt);
            }
        });

        OrderItemsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ProductID", "ProductName", "UnitPrice", "Quantity", "SubTotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        OrderItemsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        OrderItemsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ItemsScrollPane.setViewportView(OrderItemsTable);

        SubtotalLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        SubtotalLabel.setText("Total:   ");

        OrderSubtotalLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        OrderSubtotalLabel.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        OrderSubtotalLabel.setText("$0.00");
        OrderSubtotalLabel.setBorder(null);

        jLabel1.setText("Select a location:");

        CustSelectLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        CustSelectLabel1.setText("Fulfilment Selection");

        CustFirstNameLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        CustFirstNameLabel1.setText("Name:");

        CustInfoLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        CustInfoLabel1.setText("Store Info");

        StoreNameLabel.setText("(none selected)");

        PhoneLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        PhoneLabel1.setText("Phone:");

        StorePhoneLabel.setText("(###-###-####)");

        AddressLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        AddressLabel1.setText("Address");

        StreetLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        StreetLabel1.setText("Street:");

        StoreStreetLabel.setText("(street address-----------------------)");

        StoreCityLabel.setText("(city---------)");

        CityLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        CityLabel1.setText("City:");

        StateLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        StateLabel1.setText("| State:");

        StoreStateLabel.setText("##");

        ZipLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ZipLabel1.setText("| ZIP:");

        StoreZIPCODELabel.setText("#####");

        javax.swing.GroupLayout NewOrderPanelLayout = new javax.swing.GroupLayout(NewOrderPanel);
        NewOrderPanel.setLayout(NewOrderPanelLayout);
        NewOrderPanelLayout.setHorizontalGroup(
            NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, NewOrderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(NewOrderPanelLayout.createSequentialGroup()
                        .addComponent(jSeparator8, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jSeparator2)
                                        .addComponent(jSeparator1)
                                        .addGroup(NewOrderPanelLayout.createSequentialGroup()
                                            .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(CustSelectLabel)
                                                .addGroup(NewOrderPanelLayout.createSequentialGroup()
                                                    .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(FirstNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(FirstNameLabel)
                                                        .addGroup(NewOrderPanelLayout.createSequentialGroup()
                                                            .addGap(21, 21, 21)
                                                            .addComponent(SearchForCustomerButton)))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(CustomerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(LastNameLabel)
                                                        .addComponent(LastNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(NewOrderPanelLayout.createSequentialGroup()
                                                    .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(CustFirstNameLabel)
                                                        .addComponent(CustFirstName))
                                                    .addGap(48, 48, 48)
                                                    .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(CustLastName)
                                                        .addComponent(CustLastNameLabel)))
                                                .addGroup(NewOrderPanelLayout.createSequentialGroup()
                                                    .addComponent(PhoneLabel)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(CustPhone))
                                                .addComponent(AddressLabel)
                                                .addGroup(NewOrderPanelLayout.createSequentialGroup()
                                                    .addGap(6, 6, 6)
                                                    .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(NewOrderPanelLayout.createSequentialGroup()
                                                            .addComponent(EmailLabel)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(CustEmail))
                                                        .addGroup(NewOrderPanelLayout.createSequentialGroup()
                                                            .addGap(12, 12, 12)
                                                            .addComponent(CityLabel)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(CustCity, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(StateLabel)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(CustState)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(ZipLabel)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(CustZip))
                                                        .addGroup(NewOrderPanelLayout.createSequentialGroup()
                                                            .addComponent(StreetLabel)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(CustStreet, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                            .addGap(12, 12, 12)))
                                    .addComponent(CustInfoLabel))
                                .addComponent(jSeparator3)
                                .addComponent(jSeparator6)
                                .addComponent(jSeparator7)
                                .addComponent(jSeparator9)
                                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(CustSelectLabel1)
                            .addGroup(NewOrderPanelLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(StoreLocationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(CustInfoLabel1)
                            .addGroup(NewOrderPanelLayout.createSequentialGroup()
                                .addComponent(CustFirstNameLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(StoreNameLabel))
                            .addGroup(NewOrderPanelLayout.createSequentialGroup()
                                .addComponent(PhoneLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(StorePhoneLabel))
                            .addComponent(AddressLabel1))
                        .addGap(18, 18, Short.MAX_VALUE))
                    .addGroup(NewOrderPanelLayout.createSequentialGroup()
                        .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(NewOrderPanelLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(StreetLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(StoreStreetLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(NewOrderPanelLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(CityLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(StoreCityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(StateLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(StoreStateLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ZipLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(StoreZIPCODELabel)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(OrderItemsLabel)
                    .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, NewOrderPanelLayout.createSequentialGroup()
                            .addComponent(AddItemButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(EditItemButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(DeleteItemButton)
                            .addGap(307, 307, 307)
                            .addComponent(SubtotalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(OrderSubtotalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(SubmitOrderButton))
                        .addComponent(ItemsScrollPane, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.LEADING)))
                .addGap(29, 29, 29))
        );
        NewOrderPanelLayout.setVerticalGroup(
            NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NewOrderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CustSelectLabel)
                    .addComponent(OrderItemsLabel))
                .addGap(3, 3, 3)
                .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(NewOrderPanelLayout.createSequentialGroup()
                        .addComponent(ItemsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AddItemButton)
                            .addComponent(EditItemButton)
                            .addComponent(DeleteItemButton)
                            .addComponent(SubmitOrderButton)
                            .addComponent(SubtotalLabel)
                            .addComponent(OrderSubtotalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(NewOrderPanelLayout.createSequentialGroup()
                        .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(NewOrderPanelLayout.createSequentialGroup()
                                .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(FirstNameLabel)
                                    .addComponent(LastNameLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(FirstNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LastNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(7, 7, 7)
                                .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CustomerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SearchForCustomerButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CustInfoLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CustFirstNameLabel)
                                    .addComponent(CustLastNameLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CustFirstName)
                                    .addComponent(CustLastName))
                                .addGap(9, 9, 9)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(EmailLabel)
                                    .addComponent(CustEmail))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(PhoneLabel)
                                    .addComponent(CustPhone))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(AddressLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(StreetLabel)
                                    .addComponent(CustStreet))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CityLabel)
                                    .addComponent(CustCity)
                                    .addComponent(StateLabel)
                                    .addComponent(CustState)
                                    .addComponent(ZipLabel)
                                    .addComponent(CustZip))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CustSelectLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(StoreLocationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(8, 8, 8)
                                .addComponent(CustInfoLabel1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CustFirstNameLabel1)
                            .addComponent(StoreNameLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(PhoneLabel1)
                            .addComponent(StorePhoneLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(AddressLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(StoreStreetLabel)
                            .addComponent(StreetLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(NewOrderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CityLabel1)
                            .addComponent(StoreCityLabel)
                            .addComponent(StateLabel1)
                            .addComponent(StoreStateLabel)
                            .addComponent(ZipLabel1)
                            .addComponent(StoreZIPCODELabel))))
                .addGap(47, 47, 47))
        );

        CustomerComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent arg) {
                if(arg.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println((String)arg.getItem());
                    updateCustomerInfoDisplay((String)arg.getItem());
                }
            }

        });
        OrderSubtotalLabel.setEditable(false);
        OrderSubtotalLabel.setFocusable(false);
        StoreLocationComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent arg) {
                if(arg.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println((String)arg.getItem());
                    updateLocationInfoDisplay((String)arg.getItem());
                }
            }

        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(NewOrderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NewOrderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Uses the values in the first name and last name search fields to query the database for
     * customers matching the search criteria
     * @param evt the buttonclick event
     */
    private void SearchForCustomerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchForCustomerButtonActionPerformed
        String query = "select * from customers natural join zip where ";
        String first = FirstNameField.getText();
        String last = LastNameField.getText();
        if(first.equals("") && last.equals("")) {
            return;
        }
        if(first != null && !first.equals("")) {
            query += "FirstName LIKE \"%" + first + "%\"";
        }
        if(last != null && !last.equals("")) {
            if(first != null && !first.equals("")) {
                query += " and ";
            }
            query += "LastName LIKE \"%" + last + "%\"";
        }
        query += ";";
        ResultSet rs = null;
        try {
            rs = dbm.executeQuery(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if(rs != null){
            populateComboBox(rs);
        }
    }//GEN-LAST:event_SearchForCustomerButtonActionPerformed

    /**
     * Deletes the currently selected Order item from the order
     * @param evt the buttonclick event
     */
    private void DeleteItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteItemButtonActionPerformed
        int row = OrderItemsTable.getSelectedRow();
        if (row > -1) {
            int selectedID = (int)OrderItemsTable.getModel().getValueAt(row, 0);
            orderItems.remove(selectedID);
            ((NewOrderTableModel)OrderItemsTable.getModel()).removeRow(row);
            updateTotal();

        }
    }//GEN-LAST:event_DeleteItemButtonActionPerformed

    /**
     * Generates a new ItemSelectFrame for adding products to the order
     * @param evt the buttonclick event
     */
    private void AddItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddItemButtonActionPerformed
        itemSelection = new ItemSelectFrame(dbm, this);
        itemSelection.setAlwaysOnTop(true);
        itemSelection.setVisible(true);
    }//GEN-LAST:event_AddItemButtonActionPerformed

    /**
     * Submits the order to the database. A customer and a store must be selected, as well as
     * the order needs to be populated with at least one order item
     * @param evt the buttonclick event
     */
    private void SubmitOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitOrderButtonActionPerformed
        if (selectedCustomer != null && selectedStore != null && !orderItems.isEmpty()) {
            List<NewOrderItem> items = new ArrayList<>();
            for(int productID : orderItems.keySet()) {
                items.add(orderItems.get(productID));
            }
            NewOrder theOrder = new NewOrder(selectedCustomer.getID(), new NewDelivery(selectedCustomer.getZIPCODE(), selectedCustomer.getStreet()), selectedStore.getStoreID(), items);
            boolean success = dbm.addNewOrder(theOrder);
            if(success) {
                orderItems.clear();
                updateOrderTable();
                CustomerComboBox.removeAllItems();
                custs.clear();
                selectedCustomer = null;
                CustFirstName.setText("");
                CustLastName.setText("");
                CustEmail.setText("");
                CustStreet.setText("");
                CustZip.setText("");
                CustCity.setText("");
                CustState.setText("");
                CustPhone.setText("");
                JOptionPane.showMessageDialog(this,"Order Submitted Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Error Submitting Order!");
            }
        }
    }//GEN-LAST:event_SubmitOrderButtonActionPerformed

    /**
     * Lets the user edit the quantity of the selected order item
     * @param evt the buttonclick event
     */
    private void EditItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditItemButtonActionPerformed
        int row = OrderItemsTable.getSelectedRow();
        if (row > -1) {
            int selectedID = (int)OrderItemsTable.getModel().getValueAt(row, 0);
            System.out.println(selectedID);
            NewOrderItem selected = orderItems.get(selectedID);
            new EditOrderItemFrame(selected, this);
        }

    }//GEN-LAST:event_EditItemButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddItemButton;
    private javax.swing.JLabel AddressLabel;
    private javax.swing.JLabel AddressLabel1;
    private javax.swing.JLabel CityLabel;
    private javax.swing.JLabel CityLabel1;
    private javax.swing.JLabel CustCity;
    private javax.swing.JLabel CustEmail;
    private javax.swing.JLabel CustFirstName;
    private javax.swing.JLabel CustFirstNameLabel;
    private javax.swing.JLabel CustFirstNameLabel1;
    private javax.swing.JLabel CustInfoLabel;
    private javax.swing.JLabel CustInfoLabel1;
    private javax.swing.JLabel CustLastName;
    private javax.swing.JLabel CustLastNameLabel;
    private javax.swing.JLabel CustPhone;
    private javax.swing.JLabel CustSelectLabel;
    private javax.swing.JLabel CustSelectLabel1;
    private javax.swing.JLabel CustState;
    private javax.swing.JLabel CustStreet;
    private javax.swing.JLabel CustZip;
    private javax.swing.JComboBox<String> CustomerComboBox;
    private javax.swing.JButton DeleteItemButton;
    private javax.swing.JButton EditItemButton;
    private javax.swing.JLabel EmailLabel;
    private javax.swing.JTextField FirstNameField;
    private javax.swing.JLabel FirstNameLabel;
    private javax.swing.JScrollPane ItemsScrollPane;
    private javax.swing.JTextField LastNameField;
    private javax.swing.JLabel LastNameLabel;
    private javax.swing.JPanel NewOrderPanel;
    private javax.swing.JLabel OrderItemsLabel;
    private javax.swing.JTable OrderItemsTable;
    private javax.swing.JTextField OrderSubtotalLabel;
    private javax.swing.JLabel PhoneLabel;
    private javax.swing.JLabel PhoneLabel1;
    private javax.swing.JButton SearchForCustomerButton;
    private javax.swing.JLabel StateLabel;
    private javax.swing.JLabel StateLabel1;
    private javax.swing.JLabel StoreCityLabel;
    private javax.swing.JComboBox<String> StoreLocationComboBox;
    private javax.swing.JLabel StoreNameLabel;
    private javax.swing.JLabel StorePhoneLabel;
    private javax.swing.JLabel StoreStateLabel;
    private javax.swing.JLabel StoreStreetLabel;
    private javax.swing.JLabel StoreZIPCODELabel;
    private javax.swing.JLabel StreetLabel;
    private javax.swing.JLabel StreetLabel1;
    private javax.swing.JButton SubmitOrderButton;
    private javax.swing.JLabel SubtotalLabel;
    private javax.swing.JLabel ZipLabel;
    private javax.swing.JLabel ZipLabel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    // End of variables declaration//GEN-END:variables
}
