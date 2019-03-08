package hello;

import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.DriverManager;

@Service
public class databaseConnector
{
    public void connectToData(){
        System.out.println("\n\n***** MySQL JDBC Connection Testing *****");
        Connection conn = null;
        try {
            Class.forName ("com.MySQL.jdbc.Driver").newInstance ();
            conn = DriverManager.getConnection ("jdbc:MySQL://localhost/mydb", "marsonto", "123456");
            System.out.println ("\nDatabase Connection Established...");
        } catch (Exception ex) {
            System.err.println ("Cannot connect to database server");
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    System.out.println("\n***** Let terminate the Connection *****");
                    conn.close ();
                    System.out.println ("\nDatabase connection terminated...");
                }
                catch (Exception ex) {
                    System.out.println ("Error in connection termination!");
                }
            }
        }
    }
}