package biscoitagem.dao;

import biscoitagem.exception.DAOException;
import java.util.List;

public interface DAO<Table> {
    List<Table> search() throws DAOException;
    void insert(Table table) throws DAOException;
    void update(Table oldTable, Table newTable) throws DAOException;
    void delete(Table table) throws DAOException;
}
