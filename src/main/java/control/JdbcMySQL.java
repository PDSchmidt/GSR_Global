package control;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * NOTE: Need to wire in mysql jar file that provides ability to connect to database
 * 
 * You should first copy the mysql jar into your main project folder (Eclipse/IntelliJ). If you installed everything
 * when you installed MySQL community, the jar was installed on your system. If not, you'll need
 * to go download it at: https://dev.mysql.com/downloads/connector/j/
 * 
 * In MacOS, it should be somewhere in /usr/local/mysqlj
 * In Windows, it is in C:\Program Files(x86)\MySQL\Connector J 8.0
 * 
 * The name of the jar is: mysql-connector-java-8.0.28.jar
 * 
 * In Eclipse, go to Project, then Properties, then Java Build Path (on left), then Libraries tab,
 * then select Classpath, then click Add External JARs... then navigate to your jar file
 * (hopefully you copied it into project folder for portability) and select it.
 * 
 * Click all the Apply buttons there are to wire it into your project
 * 
 * 
 * 
 * 
 */

public class JdbcMySQL {
	

    public static void main(String[] args) {

    	//connecting to second database file loaded at end of first assignment
    	//update the url to your last and first name
        String url = "jdbc:mysql://localhost:3306/schmidtpaul_3";
        
        //if you created a separate account, use that, otherwise use root
        String user = "root";
        String password = "R0ck3tD0g!Ps";
        
        //quickly grab everything from Customers table and display to show everything worked
        String query = "SELECT * FROM Customers";

        try (Connection con = DriverManager.getConnection(url, user, password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query)) {
            System.out.println("CONNECTION OK");
            ResultSetMetaData resultMeta = rs.getMetaData();
            int columns = resultMeta.getColumnCount();
            for (int i = 1; i <= columns; i++) {
                System.out.print(resultMeta.getColumnName(i) + ", ");
            }
            System.out.println();
            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    System.out.print(rs.getObject(i) + ", ");
                }
                System.out.println();
            }

        } catch (SQLException ex) {
            
            Logger lgr = Logger.getLogger(JdbcMySQL.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } 
    }
    
   
}