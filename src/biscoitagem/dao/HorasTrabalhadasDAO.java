package biscoitagem.dao;

import biscoitagem.domain.HorasTrabalhadas;
import biscoitagem.exception.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HorasTrabalhadasDAO implements DAO<HorasTrabalhadas> {
    private static final String SEARCH_QUERY = "SELECT * FROM horastrabalhadas;";
    private static final String INSERT_QUERY = "INSERT INTO horastrabalhadas (valor) VALUES (?);";
    private static final String UPDATE_QUERY = "UPDATE horastrabalhadas SET valor = ?;";
    
    private Connection con = null;
    
    
    public HorasTrabalhadasDAO(Connection con) throws DAOException {
        if (con == null) {
            throw new DAOException("Conexão nula ao criar HorasTrabalhadasDAO.");
        }
        
        this.con = con;
    }
    
    
    @Override
    public List<HorasTrabalhadas> search() throws DAOException {
        List<HorasTrabalhadas> list = new ArrayList<>();
        try (PreparedStatement st = con.prepareStatement(SEARCH_QUERY); 
                ResultSet rs = st.executeQuery()) {
            
            while (rs.next()) {
                HorasTrabalhadas horasTrabalhadas = new HorasTrabalhadas();
                horasTrabalhadas.setValueAsDouble(rs.getDouble("valor"));
                
                list.add(horasTrabalhadas);
            }
            
            return list;
        }
        catch(SQLException e) {
            throw new DAOException("Erro ao buscar Valor da Hora Trabalhada: " + SEARCH_QUERY, e);
        }        
    }

    @Override
    public void insert(HorasTrabalhadas horasTrabalhadas) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(INSERT_QUERY)) { 
            st.setDouble(1, horasTrabalhadas.getValueAsDouble());
            
            st.executeUpdate();
        }
        catch(SQLException e) {
            throw new DAOException("Erro ao inserir Valor da Hora Trabalhada: " + INSERT_QUERY, e);
        }
    }

    
    @Override
    public void update(HorasTrabalhadas oldHorasTrabalhadas, HorasTrabalhadas newHorasTrabalhadas) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(UPDATE_QUERY)) {
            st.setDouble(1, oldHorasTrabalhadas.getValueAsDouble());

            st.executeUpdate();
        }
        catch(SQLException e) {
            throw new DAOException("Erro ao alterar Valor da Hora Trabalhada: " + UPDATE_QUERY, e);
        }
    }

    @Override
    public void delete(HorasTrabalhadas horasTrabalhadas) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
