package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class DataAccessObjectService {

    private final databaseConnector connector;
    private final Connection conn;

    @Autowired
    public DataAccessObjectService(final databaseConnector connector){
        this.connector = connector;
        conn = connector.connectToData();
    }

    public drink accessDrink(int id) throws SQLException {
        String query = "select * from mydb.drinks where id=" + id;
        drink drink = null;
        Statement statement = null;

        try {
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);
            result.next();
            drink = new drink(id, result.getString("name"), result.getDouble("price"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return drink;
    }

}


//select * from mydb.drinks where id=1