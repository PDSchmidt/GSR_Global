package view.orderdb;

import control.DatabaseManager;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultFormatter;
import model.entity.NewOrderItem;
import model.entity.Product;

/**
 * Frame popup that lets a user select from available products and add them to
 * an order
 * @author Paul Schmidt
 */
public class ItemSelectFrame extends javax.swing.JFrame {
    /**
     * List of selected Product
     */
    private List<Product> selected;
    /**
     * The Collection of Products returned from a query search of the database
     */
    private Map<String, Product> searched;
    /**
     * The dbm that holds a connection to the database
     */
    private DatabaseManager dbm;
    /**
     * The focused Product to update the displayed information
     */
    private Product focused;
    /**
     * The subtotal for this selected Product and quantity
     */
    private BigDecimal subtotal;
    /**
     * The Parent of this frame
     */
    private NewOrderPanel parentPanel;

    /**
     * Creates new form ItemSelectFrame
     */
    public ItemSelectFrame() {
        selected = new ArrayList<>();
        searched = new HashMap<>();
        initComponents();
        setLocationRelativeTo(null);
        focused = null;
        subtotal = BigDecimal.ZERO;
        SubTotalLabel.setText(subtotal.toString());
        QuantitySpinner.setValue(0);
    }

    /**
     * Creates a new ItemSelectFrame with a connection to the database and
     * a reference to the Parent of this JFrame
     * @param dbm the DatabaseManager that holds the connection to the database
     * @param parent the Parent NewOrderPanel associated with this ItemSelectFrame
     */
    public ItemSelectFrame(final DatabaseManager dbm, NewOrderPanel parent) {
        this();
        this.dbm = dbm;
        this.parentPanel = parent;
    }

    /**
     * Lists the returned Products from the Database query so that the user can select
     * them to see more information about them
     */
    public void repopulateSearched(){
        Vector<String> names = new Vector<>();
        for(String name : searched.keySet()){
            names.add(name);
        }
        ProductListField.setListData(names);
    }

    /**
     * Updates the displayed Product Information based on the selected Product
     */
    public void updateProductInfo() {
        focused = searched.get(ProductListField.getSelectedValue());
        if(focused != null) {
            ProductNameField.setText(focused.getName());
            ProductDescriptionArea.setText(focused.getDescription());
            UnitPriceLabel.setText(focused.getUPtoString());
            if ((int)QuantitySpinner.getValue() == 0) {
                QuantitySpinner.setValue(1);
            }
            updateSubtotal(QuantitySpinner.getValue());
        } else {
            ProductNameField.setText("N/A");
            ProductDescriptionArea.setText("No Product Selected");
            UnitPriceLabel.setText("0.00");
            QuantitySpinner.setValue(0);
            updateSubtotal(0);
        }
        
    }

    /**
     * Updates the displayed subtotal based on the selected Product and quantity
     * @param quantity the quantity used to calculate the subtotal
     */
    public void updateSubtotal(final Object quantity) {
        try {
            Integer q = (Integer) quantity;
            System.out.println(q);
            if(focused != null) {
                subtotal = new BigDecimal(focused.getUPtoString()).multiply(new BigDecimal(q));
                SubTotalLabel.setText(subtotal.toString());
            } else {
                subtotal = BigDecimal.ZERO;
                SubTotalLabel.setText("0.00");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ProductListField = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        ProductDescriptionScrollPane = new javax.swing.JScrollPane();
        ProductDescriptionArea = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        QuantitySpinner = new javax.swing.JSpinner();
        UnitPriceLabel = new javax.swing.JLabel();
        ProductNameField = new javax.swing.JLabel();
        CancelButton = new javax.swing.JButton();
        AddButton = new javax.swing.JButton();
        SearchField = new javax.swing.JTextField();
        SearchButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        AllButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        SubTotalLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);

        jScrollPane1.setViewportView(ProductListField);
        ProductListField.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                System.out.println("PRESSED index: " + e.getLastIndex());
                updateProductInfo();
            }

        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Product Name:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Description");

        ProductDescriptionScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        ProductDescriptionArea.setEditable(false);
        ProductDescriptionArea.setColumns(20);
        ProductDescriptionArea.setLineWrap(true);
        ProductDescriptionArea.setRows(5);
        ProductDescriptionArea.setWrapStyleWord(true);
        ProductDescriptionArea.setEnabled(false);
        ProductDescriptionScrollPane.setViewportView(ProductDescriptionArea);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Unit Price: $");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("QTY:");

        UnitPriceLabel.setText("$$$.$$");

        ProductNameField.setText("(none selected)");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ProductDescriptionScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(UnitPriceLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(QuantitySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ProductNameField)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(ProductNameField))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ProductDescriptionScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(QuantitySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(UnitPriceLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JComponent comp = QuantitySpinner.getEditor();
        JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
        DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
        formatter.setCommitsOnValidEdit(true);
        QuantitySpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateSubtotal(QuantitySpinner.getValue());
            }
        });
        SpinnerNumberModel spinModel = (SpinnerNumberModel)QuantitySpinner.getModel();
        spinModel.setMinimum(new Integer(1));
        spinModel.setValue(1);

        CancelButton.setText("Close");
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });

        AddButton.setText("Add");
        AddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddButtonActionPerformed(evt);
            }
        });

        SearchButton.setText("Search");
        SearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Keyword");

        AllButton.setText("All");
        AllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AllButtonActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("SubTotal: $");

        SubTotalLabel.setText("$$$.$$");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(SearchButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AllButton, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(SearchField))
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SubTotalLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(AddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(CancelButton)
                                .addComponent(AddButton))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(SubTotalLabel))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(SearchButton)
                            .addComponent(AllButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Adds the current Product and quantity to the Order
     * @param evt the buttonclick event
     */
    private void AddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddButtonActionPerformed
        if((int)QuantitySpinner.getValue() > 0) {
            NewOrderItem theItem = new NewOrderItem(focused, (Integer)QuantitySpinner.getValue());
            parentPanel.addOrderItem(theItem);
        }
    }//GEN-LAST:event_AddButtonActionPerformed

    /**
     * Performs a query of the database for products fitting the criteria of the search field
     * @param evt the buttonclick event
     */
    private void SearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchButtonActionPerformed
        System.out.println("BUTTON CLICKED SEARCH");
        String query = "select * from product where ";
        String words = SearchField.getText();
        if(words == null || words.equals("")) {
            return;
        } else {
            query += "ProductName LIKE \"%" + words + "%\" OR ProductDescription"
                    + " LIKE \"%" + words + "%\";";
        }
        System.out.println(query);
        ResultSet rs = null;
        try {
            rs = dbm.executeQuery(query);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(query);
        }
        if(rs != null){
            System.out.println("DID THE THING");
            System.out.println(query);
            searched.clear();
            Product.generateProductsFromResult(searched, rs);
            repopulateSearched();
        }
    }//GEN-LAST:event_SearchButtonActionPerformed

    /**
     * Queries the database for all the Products available to offer
     * @param evt the buttonclick event
     */
    private void AllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AllButtonActionPerformed
        searched.clear();
        Product.generateProducts(searched, dbm);
        repopulateSearched();
    }//GEN-LAST:event_AllButtonActionPerformed

    /**
     * Closes and disposes the frame
     * @param evt the buttonclick event
     */
    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_CancelButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddButton;
    private javax.swing.JButton AllButton;
    private javax.swing.JButton CancelButton;
    private javax.swing.JTextArea ProductDescriptionArea;
    private javax.swing.JScrollPane ProductDescriptionScrollPane;
    private javax.swing.JList<String> ProductListField;
    private javax.swing.JLabel ProductNameField;
    private javax.swing.JSpinner QuantitySpinner;
    private javax.swing.JButton SearchButton;
    private javax.swing.JTextField SearchField;
    private javax.swing.JLabel SubTotalLabel;
    private javax.swing.JLabel UnitPriceLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
