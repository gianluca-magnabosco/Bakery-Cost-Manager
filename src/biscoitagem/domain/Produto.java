package biscoitagem.domain;

import java.io.Serializable;
import java.util.List;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;


public class Produto implements Serializable {
    
    @BsonProperty("_id")
    private ObjectId id;
    private String nome;
    private int rendimento;
    private String metrica;
    private int horasTrabalhadas;
    private int complexidade;
    private double custo;
    private List<ReceitaBSON> receitas;
    private List<InsumoBSON> insumos;
    
    public Produto() {
        
    }
    
    public Produto(String nome, int rendimento, String metrica, int horasTrabalhadas, int complexidade, double custo) {
        this.nome = nome;
        this.rendimento = rendimento;
        this.metrica = metrica;
        this.horasTrabalhadas = horasTrabalhadas;
        this.complexidade = complexidade;
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
    
    public int getRendimento() {
        return this.rendimento;
    }
    
    public void setRendimento(int rendimento) {
        this.rendimento = rendimento;
    }
    
    public String getMetrica() {
        return this.metrica;
    }
    
    public void setMetrica(String metrica) {
        this.metrica = metrica;
    }
    
    public int getHorasTrabalhadas() {
        return this.horasTrabalhadas;
    }
    
    public void setHorasTrabalhadas(int horasTrabalhadas) {
        this.horasTrabalhadas = horasTrabalhadas;
    }
    
    public int getComplexidade() {
        return this.complexidade;
    }
    
    public void setComplexidade(int complexidade) {
        this.complexidade = complexidade;
    }
    
    public double getCusto() {
        return this.custo;
    }
    
    public void setCusto(double custo) {
        this.custo = custo;
    }
    
    public List<ReceitaBSON> getReceitas() {
        return this.receitas;
    } 
    
    public void setReceitas(List<ReceitaBSON> receitas) {
        this.receitas = receitas;
    }
    
    public List<InsumoBSON> getInsumos() {
        return this.insumos;
    }
    
    public void setInsumos(List<InsumoBSON> insumos) {
        this.insumos = insumos;
    }
}
