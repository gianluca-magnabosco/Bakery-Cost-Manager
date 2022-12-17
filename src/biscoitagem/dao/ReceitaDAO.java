package biscoitagem.dao;

import biscoitagem.domain.Eletrodomestico;
import biscoitagem.domain.EletrodomesticoBSON;
import biscoitagem.domain.Insumo;
import biscoitagem.domain.InsumoBSON;
import biscoitagem.domain.Receita;
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


public class ReceitaDAO implements DAO<Receita> {
    
    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Receita> collection;
    
    public ReceitaDAO(MongoClient client) throws DAOException {
        if (client == null) {
            throw new DAOException("Conexão nula ao criar ReceitaDAO.");
        }
        
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        this.client = client;
        this.database = this.client.getDatabase("programamae").withCodecRegistry(pojoCodecRegistry);
        this.collection = this.database.getCollection("receitas", Receita.class).withCodecRegistry(pojoCodecRegistry);
    }

    
    public Receita searchByName(String name) throws DAOException {
        try {
            Bson filter = Filters.eq("nome", name);
            FindIterable<Receita> iterator = this.collection.find(filter);
            if (iterator == null) {
                return null;
            }
            
            return iterator.first();
        }
        catch (Exception e) {
            throw new DAOException("Erro ao buscar receita.", e);
        }
    }
    
    public Receita searchOne(Receita receita) throws DAOException {
        try {
            Bson filter = Filters.and(Filters.and(eq("nome", receita.getNome()), eq("rendimento", receita.getRendimento())), eq("metrica", receita.getMetrica()));
            FindIterable<Receita> iterator = this.collection.find(filter);
            if (iterator == null) {
                return null;
            }
            
            return iterator.first();
        }   
        catch (Exception e) {
            throw new DAOException("Erro ao buscar receita.", e);
        }        
    }
    
    
    @Override
    public List<Receita> search() throws DAOException {
        List<Receita> list = new ArrayList<>();
        try {
            FindIterable<Receita> iterator = this.collection.find();
            if (iterator == null) {
                return list;
            }

            for (Receita receita : iterator) {
                list.add(receita);
            }
        }
        catch (Exception e) {
            throw new DAOException("Erro ao buscar receitas.", e);
        }
        
        return list;
    }
    

    @Override
    public void insert(Receita receita) throws DAOException {
        try {
            this.collection.insertOne(receita);
        }   
        catch (Exception e) {
            throw new DAOException("Erro ao inserir receita.", e);
        }
    }
    

    @Override
    public void update(Receita oldReceita, Receita newReceita) throws DAOException {
        try { 
            Bson filter = Filters.eq("_id", oldReceita.getId());
            this.collection.findOneAndReplace(filter, newReceita);
            
            if (!newReceita.getNome().equals(oldReceita.getNome()) || !newReceita.getMetrica().equals(oldReceita.getMetrica())) {
                try (MongoClientFactory factory = new MongoClientFactory()) {                    
                    ProdutoDAO produtoDao = new ProdutoDAO(factory.getClient());
                    produtoDao.updateReceitas(oldReceita, newReceita);
                }
            }
            
        }
        catch (Exception e) {
            throw new DAOException("Erro ao atualizar receita.", e);
        }
    }
    
    
    public void updateInsumos(Insumo oldInsumo, Insumo newInsumo) throws DAOException {
        try {
            Bson filter = Filters.and(eq("insumos.nome", oldInsumo.getNome()), eq("insumos.metrica", oldInsumo.getMetrica()));
            FindIterable<Receita> iterator = this.collection.find(filter);
            if (iterator == null) {
                return;
            }

            for (Receita receita : iterator) {
                List<InsumoBSON> insumos = receita.getInsumos();
                for (InsumoBSON insumo : insumos) {
                    if (insumo.getNome().equals(oldInsumo.getNome()) && insumo.getMetrica().equals(oldInsumo.getMetrica())) {
                        insumo.setNome(newInsumo.getNome());
                        insumo.setMetrica(newInsumo.getMetrica());
                    }
                }
                receita.setInsumos(insumos);
                Bson newFilter = Filters.eq("_id", receita.getId());
                this.collection.findOneAndReplace(newFilter, receita);
            } 
        }
        catch (Exception e) {
            throw new DAOException("Erro ao atualizar insumo.", e);
        }
    }
    
    
    public void deleteInsumos(Insumo insumo) throws DAOException {
        try {
            Bson filter = Filters.and(eq("insumos.nome", insumo.getNome()), eq("insumos.metrica", insumo.getMetrica()));
            FindIterable<Receita> iterator = this.collection.find(filter);
            if (iterator == null) {
                return;
            }

            for (Receita receita : iterator) {
                List<InsumoBSON> insumos = receita.getInsumos();
                for (int i = 0; i < insumos.size(); i++) {
                    if (insumos.get(i).getNome().equals(insumo.getNome()) && insumos.get(i).getMetrica().equals(insumo.getMetrica())) {
                        insumos.remove(i);
                    }
                }
                receita.setInsumos(insumos);
                Bson newFilter = Filters.eq("_id", receita.getId());
                this.collection.findOneAndReplace(newFilter, receita);
            } 
        }
        catch (Exception e) {
            throw new DAOException("Erro ao remover insumo.", e);
        }
    }
    
    
    public void updateEletrodomesticos(Eletrodomestico oldEletrodomestico, Eletrodomestico newEletrodomestico) throws DAOException {
        try {
            Bson filter = Filters.eq("eletrodomesticos.nome", oldEletrodomestico.getNome());
            FindIterable<Receita> iterator = this.collection.find(filter);
            if (iterator == null) {
                return;
            }

            for (Receita receita : iterator) {
                List<EletrodomesticoBSON> eletrodomesticos = receita.getEletrodomesticos();
                for (EletrodomesticoBSON eletrodomestico : eletrodomesticos) {
                    if (eletrodomestico.getNome().equals(oldEletrodomestico.getNome())) {
                        eletrodomestico.setNome(newEletrodomestico.getNome());
                    }
                }
                receita.setEletrodomesticos(eletrodomesticos);
                Bson newFilter = Filters.eq("_id", receita.getId());
                this.collection.findOneAndReplace(newFilter, receita);
            } 
        }
        catch (Exception e) {
            throw new DAOException("Erro ao atualizar eletrodoméstico.", e);
        }        
    }
    
    
    public void deleteEletrodomesticos(Eletrodomestico eletrodomestico) throws DAOException {
        try {
            Bson filter = Filters.eq("eletrodomesticos.nome", eletrodomestico.getNome());
            FindIterable<Receita> iterator = this.collection.find(filter);
            if (iterator == null) {
                return;
            }

            for (Receita receita : iterator) {
                List<EletrodomesticoBSON> eletrodomesticos = receita.getEletrodomesticos();
                for (int i = 0; i < eletrodomesticos.size(); i++) {
                    if (eletrodomesticos.get(i).getNome().equals(eletrodomestico.getNome())) {
                        eletrodomesticos.remove(i);
                    }
                }
                receita.setEletrodomesticos(eletrodomesticos);
                Bson newFilter = Filters.eq("_id", receita.getId());
                this.collection.findOneAndReplace(newFilter, receita);
            } 
        }
        catch (Exception e) {
            throw new DAOException("Erro ao remover eletrodoméstico.", e);
        }        
    }
    
    
    @Override
    public void delete(Receita receita) throws DAOException {
        try {
            Bson filter = Filters.and(Filters.and(eq("nome", receita.getNome()), eq("rendimento", receita.getRendimento())), eq("metrica", receita.getMetrica()));
            this.collection.deleteOne(filter);
            
            try (MongoClientFactory factory = new MongoClientFactory()) {                
                ProdutoDAO produtoDao = new ProdutoDAO(factory.getClient());
                produtoDao.deleteReceitas(receita);
            }            
            
        }
        catch (Exception e) {
            throw new DAOException("Erro ao remover receita.", e);
        }
    }
    
}
