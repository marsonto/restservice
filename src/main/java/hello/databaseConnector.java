package hello;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;

@Service
public class databaseConnector
{
    public Connection connectToData(){
        System.out.println("\n\n***** MySQL JDBC Connection Testing *****");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection ("jdbc:MySQL://localhost/mydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "marsonto", "123456");
            System.out.println ("\nDatabase Connection Established...");
        } catch (Exception ex) {
            System.err.println ("Cannot connect to database server");
            ex.printStackTrace();
        }
        return conn;
    }
}