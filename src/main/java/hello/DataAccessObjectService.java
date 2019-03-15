package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;

@Service
public class DataAccessObjectService {

    private final databaseConnector connector;
    private final Connection conn;

    @Autowired
    public DataAccessObjectService(final databaseConnector connector){
        this.connector = connector;
        conn = connector.connectToData();
    }

    

}
