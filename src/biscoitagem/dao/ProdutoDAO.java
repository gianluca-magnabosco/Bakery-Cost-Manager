package biscoitagem.dao;

import biscoitagem.domain.Insumo;
import biscoitagem.domain.InsumoBSON;
import biscoitagem.domain.Produto;
import biscoitagem.domain.Receita;
import biscoitagem.domain.ReceitaBSON;
import biscoitagem.exception.DAOException;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.List;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;


public class ProdutoDAO implements DAO<Produto> {
    
    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Produto> collection;
    
    
    public ProdutoDAO(MongoClient client) throws DAOException {
        if (client == null) {
            throw new DAOException("Conexão nula ao criar ProdutoDAO.");
        }
        
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        
        this.client = client;
        this.database = this.client.getDatabase("programamae").withCodecRegistry(pojoCodecRegistry);
        this.collection = this.database.getCollection("produtos", Produto.class).withCodecRegistry(pojoCodecRegistry);        
    }
    

    public Produto searchByName(String name) throws DAOException {
        try {
            Bson filter = Filters.eq("nome", name);
            FindIterable<Produto> iterator = this.collection.find(filter);
            if (iterator == null) {
                return null;
            }
            
            return iterator.first();
        }
        catch (Exception e) {
            throw new DAOException("Erro ao buscar produto.", e);
        }
    }
    
    
    public Produto searchOne(Produto produto) throws DAOException {
        try {
            Bson filter = Filters.and(Filters.and(eq("nome", produto.getNome()), eq("rendimento", produto.getRendimento())), eq("metrica", produto.getMetrica()));
            FindIterable<Produto> iterator = this.collection.find(filter);
            if (iterator == null) {
                return null;
            }
            
            return iterator.first();
        }
        catch (Exception e) {
            throw new DAOException("Erro ao buscar produto.", e);
        }
    }
    
    
    
    @Override
    public List<Produto> search() throws DAOException {
        List<Produto> list = new ArrayList<>();
        try {
            FindIterable<Produto> iterator = this.collection.find();
            if (iterator == null) {
                return list;
            }
            
            for (Produto produto : iterator) {
                list.add(produto);
            }
        }
        catch (Exception e) {
            throw new DAOException("Erro ao buscar produtos.", e);
        }
        
        return list;
    }

    
    @Override
    public void insert(Produto produto) throws DAOException {
        try {
            this.collection.insertOne(produto);
        }
        catch (Exception e) {
            throw new DAOException("Erro ao inserir produto.", e);
        }
    }

    
    @Override
    public void update(Produto oldProduto, Produto newProduto) throws DAOException {
        try {
            Bson filter = Filters.eq("_id", oldProduto.getId());
            this.collection.findOneAndReplace(filter, newProduto);
        }
        catch (Exception e) {
            throw new DAOException("Erro ao atualizar produto.", e);
        }
    }
    
    
    public void updateReceitas(Receita oldReceita, Receita newReceita) throws DAOException {
        try {
            Bson filter = Filters.and(eq("receitas.nome", oldReceita.getNome()), eq("receitas.metrica", oldReceita.getMetrica()));
            FindIterable<Produto> iterator = this.collection.find(filter);
            if (iterator == null) {
                return;
            }

            for (Produto produto : iterator) {
                List<ReceitaBSON> receitas = produto.getReceitas();
                for (ReceitaBSON receita : receitas) {
                    if (receita.getNome().equals(oldReceita.getNome()) && receita.getMetrica().equals(oldReceita.getMetrica())) {
                        receita.setNome(newReceita.getNome());
                        receita.setMetrica(newReceita.getMetrica());
                    }
                }
                produto.setReceitas(receitas);
                Bson newFilter = Filters.eq("_id", produto.getId());
                this.collection.findOneAndReplace(newFilter, produto);
            } 
        }
        catch (Exception e) {
            throw new DAOException("Erro ao atualizar receita.", e);
        }          
    }
    
    
    public void deleteReceitas(Receita receita) throws DAOException {
        try {
            Bson filter = Filters.and(eq("receitas.nome", receita.getNome()), eq("receitas.metrica", receita.getMetrica()));
            FindIterable<Produto> iterator = this.collection.find(filter);
            if (iterator == null) {
                return;
            }

            for (Produto produto : iterator) {
                List<ReceitaBSON> receitas = produto.getReceitas();
                for (int i = 0; i < receitas.size(); i++) {
                    if (receitas.get(i).getNome().equals(receita.getNome()) && receitas.get(i).getMetrica().equals(receita.getMetrica())) {
                        receitas.remove(i);
                    }
                }
                produto.setReceitas(receitas);
                Bson newFilter = Filters.eq("_id", produto.getId());
                this.collection.findOneAndReplace(newFilter, produto);
            } 
        }
        catch (Exception e) {
            throw new DAOException("Erro ao remover receita.", e);
        }           
    }
    
    
    public void updateInsumos(Insumo oldInsumo, Insumo newInsumo) throws DAOException {
        try {
            Bson filter = Filters.and(eq("insumos.nome", oldInsumo.getNome()), eq("insumos.metrica", oldInsumo.getMetrica()));
            FindIterable<Produto> iterator = this.collection.find(filter);
            if (iterator == null) {
                return;
            }

            for (Produto produto : iterator) {
                List<InsumoBSON> insumos = produto.getInsumos();
                for (InsumoBSON insumo : insumos) {
                    if (insumo.getNome().equals(oldInsumo.getNome()) && insumo.getMetrica().equals(oldInsumo.getMetrica())) {
                        insumo.setNome(newInsumo.getNome());
                        insumo.setMetrica(newInsumo.getMetrica());
                    }
                }
                produto.setInsumos(insumos);
                Bson newFilter = Filters.eq("_id", produto.getId());
                this.collection.findOneAndReplace(newFilter, produto);
            } 
        }
        catch (Exception e) {
            throw new DAOException("Erro ao atualizar insumo.", e);
        }        
    }
    
    
    public void deleteInsumos(Insumo insumo) throws DAOException {
        try {
            Bson filter = Filters.and(eq("insumos.nome", insumo.getNome()), eq("insumos.metrica", insumo.getMetrica()));
            FindIterable<Produto> iterator = this.collection.find(filter);
            if (iterator == null) {
                return;
            }

            for (Produto produto : iterator) {
                List<InsumoBSON> insumos = produto.getInsumos();
                for (int i = 0; i < insumos.size(); i++) {
                    if (insumos.get(i).getNome().equals(insumo.getNome()) && insumos.get(i).getMetrica().equals(insumo.getMetrica())) {
                        insumos.remove(i);
                    }
                }
                produto.setInsumos(insumos);
                Bson newFilter = Filters.eq("_id", produto.getId());
                this.collection.findOneAndReplace(newFilter, produto);
            } 
        }
        catch (Exception e) {
            throw new DAOException("Erro ao remover insumo.", e);
        }        
    }
    
    
    @Override
    public void delete(Produto produto) throws DAOException {
        try {
            Bson filter = Filters.and(Filters.and(eq("nome", produto.getNome()), eq("rendimento", produto.getRendimento())), eq("metrica", produto.getMetrica()));
            this.collection.deleteOne(filter);
        }
        catch (Exception e) {
            throw new DAOException("Erro ao remover produto.", e);
        }
    }
    
}
