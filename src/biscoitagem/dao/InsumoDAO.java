package biscoitagem.dao;

import biscoitagem.domain.Insumo;
import biscoitagem.exception.DAOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class InsumoDAO implements DAO<Insumo> {
    private static final String SEARCH_QUERY_BY_NAME = "SELECT nome, quantidade, metrica, preco FROM Insumos WHERE nome = ?;";
    private static final String SEARCH_QUERY = "SELECT nome, quantidade, metrica, preco FROM Insumos ORDER BY nome ASC;";
    private static final String INSERT_QUERY = "INSERT INTO Insumos (nome, quantidade, metrica, preco) VALUES (?, ?, ?, ?);";
    private static final String UPDATE_QUERY = "UPDATE Insumos SET nome = ?, quantidade = ?, metrica = ?, preco = ? WHERE nome = ? AND quantidade = ? AND metrica = ? AND preco = ?;";
    private static final String DELETE_QUERY = "DELETE FROM Insumos WHERE nome = ? AND quantidade = ? AND metrica = ? AND preco = ?;";
    
    private Connection con = null;
    
    
    public InsumoDAO(Connection con) throws DAOException {
        if (con == null) {
            throw new DAOException("Conexão nula ao criar InsumoDAO.");
        }
        
        this.con = con;
    }
    

    public Insumo searchByName(String nome) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(SEARCH_QUERY_BY_NAME)) {
            st.setString(1, nome);
            try (ResultSet rs = st.executeQuery()) {
            
                while (rs.next()) {
                    Insumo insumo = new Insumo();
                    insumo.setNome(rs.getString("nome"));
                    insumo.setQuantidade(rs.getInt("quantidade"));
                    insumo.setMetrica(rs.getString("metrica"));
                    insumo.setPreco(rs.getDouble("preco"));

                    return insumo;
                }
            }
        }
        catch(SQLException e) {
            throw new DAOException("Erro ao buscar insumo: " + SEARCH_QUERY_BY_NAME, e);
        }
        
        return null;
    }

    
    @Override
    public List<Insumo> search() throws DAOException {
        List<Insumo> list = new ArrayList<>();
        try (PreparedStatement st = con.prepareStatement(SEARCH_QUERY); 
                ResultSet rs = st.executeQuery()) {
            
            while (rs.next()) {
                Insumo insumo = new Insumo();
                insumo.setNome(rs.getString("nome"));
                insumo.setQuantidade(rs.getInt("quantidade"));
                insumo.setMetrica(rs.getString("metrica"));
                insumo.setPreco(rs.getDouble("preco"));
                
                list.add(insumo);
            }
            
            return list;
        }
        catch(SQLException e) {
            throw new DAOException("Erro ao buscar insumos: " + SEARCH_QUERY, e);
        }
    }
    
    
    @Override
    public void insert(Insumo insumo) throws DAOException {
        
        try (PreparedStatement st = con.prepareStatement(INSERT_QUERY)) { 
            st.setString(1, insumo.getNome());
            st.setInt(2, insumo.getQuantidade());
            st.setString(3, insumo.getMetrica());
            st.setDouble(4, insumo.getPreco());
            
            st.executeUpdate();
        }
        catch(SQLException e) {
            throw new DAOException("Erro ao inserir Insumo: " + INSERT_QUERY, e);
        }
    }
    
    
    @Override
    public void update(Insumo oldInsumo, Insumo newInsumo) throws DAOException {
        
        try (PreparedStatement st = con.prepareStatement(UPDATE_QUERY)) {
            st.setString(1, newInsumo.getNome());
            st.setInt(2, newInsumo.getQuantidade());
            st.setString(3, newInsumo.getMetrica());
            st.setDouble(4, newInsumo.getPreco());
            
            st.setString(5, oldInsumo.getNome());
            st.setInt(6, oldInsumo.getQuantidade());
            st.setString(7, oldInsumo.getMetrica());
            st.setDouble(8, oldInsumo.getPreco());
            
            st.executeUpdate();
            
            if (!newInsumo.getNome().equals(oldInsumo.getNome()) || !newInsumo.getMetrica().equals(oldInsumo.getMetrica())) {
                try (MongoClientFactory factory = new MongoClientFactory()) {
                    ReceitaDAO receitaDao = new ReceitaDAO(factory.getClient());
                    receitaDao.updateInsumos(oldInsumo, newInsumo);
                    
                    ProdutoDAO produtoDao = new ProdutoDAO(factory.getClient());
                    produtoDao.updateInsumos(oldInsumo, newInsumo);
                }
            }
        }
        catch(SQLException e) {
            throw new DAOException("Erro ao alterar Insumo: " + UPDATE_QUERY, e);
        }
    }
    
    
    @Override
    public void delete(Insumo insumo) throws DAOException {
        
        try (PreparedStatement st = con.prepareStatement(DELETE_QUERY)) {
            st.setString(1, insumo.getNome());
            st.setInt(2, insumo.getQuantidade());
            st.setString(3, insumo.getMetrica());
            st.setDouble(4, insumo.getPreco());
            
            st.executeUpdate();
            
            try (MongoClientFactory factory = new MongoClientFactory()) {
                ReceitaDAO receitaDao = new ReceitaDAO(factory.getClient());
                receitaDao.deleteInsumos(insumo);
                
                ProdutoDAO produtoDao = new ProdutoDAO(factory.getClient());
                produtoDao.deleteInsumos(insumo);
            }
        }
        catch(SQLException e) {
            throw new DAOException("Erro ao remover Insumo: " + DELETE_QUERY, e);
        }
    }
}
