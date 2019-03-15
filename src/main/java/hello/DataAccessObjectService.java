package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.*;
import java.util.*;

@Service
public class DataAccessObjectService {

    private final databaseConnector connector;
    private static final String QUERY = "select * from mydb.drinks where id=?";
    private static final String ALLQUERY = "select * from mydb.drinks";

    @Autowired
    public DataAccessObjectService(final databaseConnector connector){
        this.connector = connector;
    }

    public Optional<Drink> accessDrink(int id){
        Connection conn = connector.connectToData();
        try(PreparedStatement statement = conn.prepareStatement(QUERY)) {
            statement.setInt(1, id);
            try (ResultSet result  = statement.executeQuery()) {
                result.next();
                return Optional.of(new Drink(id, result.getString("name"), result.getDouble("price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Drink> accessAllDrinks(){
        Connection conn = connector.connectToData();
        ArrayList<Drink> drinkList = new ArrayList<>();

        try(Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(ALLQUERY)) {
            while (result.next()){
                drinkList.add(new Drink(result.getInt("id"), result.getString("name"), result.getDouble("price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drinkList;
    }
}
