package control;


import Testing.ResultTableModel;
import model.NewOrder;

import javax.swing.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {
    private Connection con;
    private String url;
    private String user;
    private String password;
    public DatabaseManager() {
        url = "jdbc:mysql://localhost:3306/" + Credentials.db + "?allowMultiQueries=true";
        user = Credentials.user;
        password = Credentials.pw;
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(JdbcMySQL.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    public boolean addNewOrder(final NewOrder theOrder) {
        boolean result = true;
        int ID = -1;
        String query = theOrder.getInsertStatement();
        boolean supports = false;
        try {
            supports = con.getMetaData().supportsBatchUpdates();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("SUPPORTS BATCHES? : " + supports);
        String[] batches = query.split(";");
        for(int i = 0; i < batches.length; i++) {
            batches[i] = batches[i] + ";";
            System.out.print(batches[i]);
        }
        try {
            con.setAutoCommit(false);
        } catch (SQLException e) {
            result = false;
            throw new RuntimeException(e);
        }
        try (Statement st = con.createStatement()) {
            System.out.println();
            for(int i = 0; i < batches.length; i++) {
                st.addBatch(batches[i]);
                st.execute(batches[i]);
                System.out.println("EXECUTED: "  + batches[i]);
            }
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
            result = false;
            try {
                con.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            con.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    public JTable executeQueryGetTable(final String query) throws SQLException {
        return createTable(executeQuery(query));
    }
    public JTable createTable(final ResultSet result) throws SQLException {
        ResultSetMetaData resultMeta = result.getMetaData();
        int columns = resultMeta.getColumnCount();
        Object[] attributes = new Object[columns];
        for (int i = 1; i <= columns; i++) {
            attributes[i-1] = resultMeta.getColumnName(i);
            System.out.println(resultMeta.getColumnType(i));
        }
        result.last();
        int row = result.getRow();
        result.beforeFirst();
        Object[][] data = new Object[row][columns];
        int j = 0;
        while (result.next()) {
            for (int i = 1; i <= columns; i++) {
                data[j][i-1] = result.getObject(i);
            }
            j++;
        }
        ResultTableModel model = new ResultTableModel(data, attributes, row);
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return table;
    }
    public ResultSet executeQuery(final String query) throws SQLException {
        Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        return st.executeQuery(query);
    }
}
