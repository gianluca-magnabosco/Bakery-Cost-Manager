package biscoitagem.domain;

import java.io.Serializable;

public class EletrodomesticoBSON implements Serializable {
    private String nome;
    private int tempo;
    
    public EletrodomesticoBSON() {
        
    }
    
    public EletrodomesticoBSON(String nome, int tempo) {
        this.nome = nome;
        this.tempo = tempo;
    }
    
    
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public int getTempo() {
        return this.tempo;
    }
    
    public void setTempo(int tempo) {
        this.tempo = tempo;
    }
    
    @Override
    public String toString() {
        return this.nome;
    }
}
