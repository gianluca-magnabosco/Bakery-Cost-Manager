package biscoitagem.dao;

import biscoitagem.exception.DAOException;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;


public class MongoClientFactory implements AutoCloseable {
    private static final ConnectionString URL = new ConnectionString("mongodb://localhost:27017");
    
    private MongoClient client = null;
    
    
    public MongoClientFactory() {
        super();
    }
    
    public MongoClient getClient() throws DAOException {
        if (client == null) {
            try {
                client = MongoClients.create(URL);
            }
            catch (Exception e) {
                throw new DAOException("Erro de conexão ao banco de dados", e);
            }
        }
        
        return client;
    }

    @Override
    public void close() {
        if (client != null) {
            try {
                client.close();
                client = null;
            }
            catch (Exception e) {
                System.out.println("Erro fechando a conexão. IGNORADO");
                e.printStackTrace();
            }
            finally {
                client = null;
            }
        }
    }
}
