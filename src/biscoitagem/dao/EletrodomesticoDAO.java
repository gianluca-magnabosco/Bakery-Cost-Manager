package biscoitagem.dao;

import biscoitagem.domain.Eletrodomestico;
import biscoitagem.exception.DAOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class EletrodomesticoDAO implements DAO<Eletrodomestico> {
    private static final String SEARCH_QUERY_BY_NAME = "SELECT nome, potencia, preco_hora FROM Eletrodomesticos WHERE nome = ?;";
    private static final String SEARCH_QUERY = "SELECT nome, potencia, preco_hora FROM Eletrodomesticos ORDER BY nome ASC;";
    private static final String INSERT_QUERY = "INSERT INTO Eletrodomesticos (nome, potencia, preco_hora) VALUES (?, ?, ?);";
    private static final String UPDATE_QUERY = "UPDATE Eletrodomesticos SET nome = ?, potencia = ? WHERE nome = ? AND potencia = ?;";
    private static final String UPDATE_PRECO_QUERY = "UPDATE Eletrodomesticos SET preco_hora = CAST((1.0 * potencia / 1000.0) as DECIMAL(10, 2)) * ?;";
    private static final String DELETE_QUERY = "DELETE FROM Eletrodomesticos WHERE nome = ? AND potencia = ?;";
    
    private Connection con = null;
    
    
    public EletrodomesticoDAO(Connection con) throws DAOException {
        if (con == null) {
            throw new DAOException("Conexão nula ao criar EletrodomésticoDAO.");
        }
        
        this.con = con;
    }
    
    
    public Eletrodomestico searchByName(String nome) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(SEARCH_QUERY_BY_NAME)) {
            st.setString(1, nome);
            try (ResultSet rs = st.executeQuery()) {
            
                while (rs.next()) {
                    Eletrodomestico eletrodomestico = new Eletrodomestico();
                    eletrodomestico.setNome(rs.getString("nome"));
                    eletrodomestico.setPotencia(rs.getInt("potencia"));
                    eletrodomestico.setPrecoPorHora(rs.getDouble("preco_hora"));

                    return eletrodomestico;
                }
            }
        }
        catch(SQLException e) {
            throw new DAOException("Erro ao buscar eletrodoméstico: " + SEARCH_QUERY_BY_NAME, e);
        }
        
        return null;
    }
    
    
    @Override
    public List<Eletrodomestico> search() throws DAOException {
        List<Eletrodomestico> list = new ArrayList<>();
        try (PreparedStatement st = con.prepareStatement(SEARCH_QUERY);
                ResultSet rs = st.executeQuery()) {
            
            while (rs.next()) {
                Eletrodomestico eletrodomestico = new Eletrodomestico();
                eletrodomestico.setNome(rs.getString("nome"));
                eletrodomestico.setPotencia(rs.getInt("potencia"));
                eletrodomestico.setPrecoPorHora(rs.getDouble("preco_hora"));
                
                list.add(eletrodomestico);
            }
            
            return list;
        }
        catch(SQLException e) {
            throw new DAOException("Erro ao buscar eletrodomésticos: " + SEARCH_QUERY, e);
        }
    }

    @Override
    public void insert(Eletrodomestico eletrodomestico) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(INSERT_QUERY)) { 
            st.setString(1, eletrodomestico.getNome());
            st.setInt(2, eletrodomestico.getPotencia());
            st.setDouble(3, eletrodomestico.getPrecoPorHora());
            
            st.executeUpdate();
        }
        catch(SQLException e) {
            throw new DAOException("Erro ao inserir eletrodoméstico: " + INSERT_QUERY, e);
        }
    }

    @Override
    public void update(Eletrodomestico oldEletrodomestico, Eletrodomestico newEletrodomestico) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(UPDATE_QUERY)) {
            st.setString(1, newEletrodomestico.getNome());
            st.setInt(2, newEletrodomestico.getPotencia());
            
            st.setString(3, oldEletrodomestico.getNome());
            st.setInt(4, oldEletrodomestico.getPotencia());
            
            st.executeUpdate();
            
            if (!newEletrodomestico.getNome().equals(oldEletrodomestico.getNome())) {
                try (MongoClientFactory factory = new MongoClientFactory()) {
                    ReceitaDAO dao = new ReceitaDAO(factory.getClient());
                    dao.updateEletrodomesticos(oldEletrodomestico, newEletrodomestico);
                }
            }
        }
        catch(SQLException e) {
            throw new DAOException("Erro ao alterar eletrodoméstico: " + UPDATE_QUERY, e);
        }
    }

    
    public void updatePreco(double kwh) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(UPDATE_PRECO_QUERY)) {
            st.setDouble(1, kwh);
            st.executeUpdate();
        }
        catch(SQLException e) {
            throw new DAOException("Erro ao alterar eletrodomésticos: " + UPDATE_PRECO_QUERY, e);
        }
    }
    
    
    @Override
    public void delete(Eletrodomestico eletrodomestico) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(DELETE_QUERY)) {
            st.setString(1, eletrodomestico.getNome());
            st.setInt(2, eletrodomestico.getPotencia());

            st.executeUpdate();
            
            try (MongoClientFactory factory = new MongoClientFactory()) {
                ReceitaDAO dao = new ReceitaDAO(factory.getClient());
                dao.deleteEletrodomesticos(eletrodomestico);
            }
        }
        catch(SQLException e) {
            throw new DAOException("Erro ao remover eletrodoméstico: " + DELETE_QUERY, e);
        }
    }
    
}
