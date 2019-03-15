package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataAccessObjectService {

    private final databaseConnector connector;
    private final Connection conn;

    @Autowired
    public DataAccessObjectService(final databaseConnector connector){
        this.connector = connector;
        conn = connector.connectToData();
    }

    public Drink accessDrink(int id) throws SQLException {
        String query = "select * from mydb.drinks where id=" + id;
        Drink drink = null;
        Statement statement = null;

        try {
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);
            result.next();
            drink = new Drink(id, result.getString("name"), result.getDouble("price"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return drink;
    }

    public List<Drink> accessAllDrinks() throws SQLException{
        String query = "select * from mydb.drinks";
        Statement statement = null;
        ArrayList<Drink> drinkList = new ArrayList<>();

        try {
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()){
                drinkList.add(new Drink(result.getInt("id"), result.getString("name"), result.getDouble("price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return drinkList;
    }
}
