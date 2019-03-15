package hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.DriverManager;

@Service
public class databaseConnector
{
    @Value("${user}")
    private String user;
    @Value("${password}")
    private String password;

    public Connection connectToData(){
        System.out.println("\n\n***** MySQL JDBC Connection Testing *****");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection ("jdbc:MySQL://localhost/mydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, password);
            System.out.println ("\nDatabase Connection Established...");
        } catch (Exception ex) {
            System.err.println ("Cannot connect to database server");
            ex.printStackTrace();
        }
        return conn;
    }
}