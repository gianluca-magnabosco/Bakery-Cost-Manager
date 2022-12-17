package biscoitagem.domain;

import java.io.Serializable;


public class InsumoBSON implements Serializable {
    private String nome;
    private int quantidade;
    private String metrica;
    
    
    public InsumoBSON() {
        
    }
    
    
    public InsumoBSON(String nome, int quantidade, String metrica) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.metrica = metrica;
    }
    
    
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public int getQuantidade() {
        return this.quantidade;
    }
    
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    public String getMetrica() {
        return this.metrica;
    }
    
    public void setMetrica(String metrica) {
        this.metrica = metrica;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
