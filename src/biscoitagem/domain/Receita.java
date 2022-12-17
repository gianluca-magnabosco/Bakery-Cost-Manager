package biscoitagem.domain;

import java.io.Serializable;
import java.util.List;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;


public class Receita implements Serializable {
    
    @BsonProperty("_id")
    private ObjectId id;
    private String nome;
    private int rendimento;
    private String metrica;
    private double custo;
    private List<InsumoBSON> insumos;
    private List<EletrodomesticoBSON> eletrodomesticos;
    
    public Receita() {
        
    }
    
    public Receita(String nome, int rendimento, String metrica, double custo) {
        this.nome = nome;
        this.rendimento = rendimento;
        this.metrica = metrica;
        this.custo = custo;
    }
    
    public ObjectId getId() {
        return this.id;
    }
    
    public void setId(ObjectId id) {
        this.id = id;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getMetrica() {
        return this.metrica;
    }
    
    public void setMetrica(String metrica) {
        this.metrica = metrica;
    }
    
    public int getRendimento() {
        return this.rendimento;
    } 
    
    public void setRendimento(int rendimento) {
        this.rendimento = rendimento;
    }
    
    public double getCusto() {
        return this.custo;
    }
    
    public void setCusto(double custo) {
        this.custo = custo;
    }
    
    
    public void setInsumos(List<InsumoBSON> insumos) {
        this.insumos = insumos;
    }
    
    
    public List<InsumoBSON> getInsumos() {
        return this.insumos;
    }

    
    public void setEletrodomesticos(List<EletrodomesticoBSON> eletrodomesticos) {
        this.eletrodomesticos = eletrodomesticos;
    }

    
    public List<EletrodomesticoBSON> getEletrodomesticos() {
        return this.eletrodomesticos;
    }
    
    
}
