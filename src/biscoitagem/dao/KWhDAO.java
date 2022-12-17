package biscoitagem.dao;

import biscoitagem.domain.KWh;
import biscoitagem.exception.DAOException;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class KWhDAO implements DAO<KWh> {
    private static final String SEARCH_QUERY = "SELECT * FROM kwh;";
    private static final String INSERT_QUERY = "INSERT INTO kwh (kwh) VALUES (?);";
    private static final String UPDATE_QUERY = "UPDATE kwh SET kwh = ?;";
    
    private Connection con = null;
    
    
    public KWhDAO(Connection con) throws DAOException {
        if (con == null) {
            throw new DAOException("Conexão nula ao criar KWhDAO.");
        }
        
        this.con = con;
    }
    
    
    @Override
    public List<KWh> search() throws DAOException {
        List<KWh> list = new ArrayList<>();
        try (PreparedStatement st = con.prepareStatement(SEARCH_QUERY); 
                ResultSet rs = st.executeQuery()) {
            
            while (rs.next()) {
                KWh kwh = new KWh();
                kwh.setKWh(rs.getDouble("kwh"));
                
                list.add(kwh);
            }
            
            return list;
        }
        catch(SQLException e) {
            throw new DAOException("Erro ao buscar KWh: " + SEARCH_QUERY, e);
        }        
    }

    @Override
    public void insert(KWh kwh) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(INSERT_QUERY)) { 
            st.setDouble(1, kwh.getKWhAsDouble());
            
            st.executeUpdate();
        }
        catch(SQLException e) {
            throw new DAOException("Erro ao inserir KWh: " + INSERT_QUERY, e);
        }
    }

    
    @Override
    public void update(KWh oldKwh, KWh newKwh) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(UPDATE_QUERY)) {
            st.setDouble(1, oldKwh.getKWhAsDouble());

            st.executeUpdate();
        }
        catch(SQLException e) {
            throw new DAOException("Erro ao alterar KWh: " + UPDATE_QUERY, e);
        }
    }

    @Override
    public void delete(KWh kwh) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
