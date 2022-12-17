
package biscoitagem.domain;

import static biscoitagem.utils.InputFormatHandler.formatToReais;
import static biscoitagem.utils.InputFormatHandler.getPriceAsDouble;
import java.io.Serializable;

public class Insumo implements Serializable {
    private String nome;
    private int quantidade;
    private String metrica;
    private double preco;
    
    
    public Insumo() { 
    
    }
    
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    public void setQuantidade(String quantidade) {
        this.quantidade = Integer.parseInt(quantidade);
    }
    
    public int getQuantidade() {
        return this.quantidade;
    }
    
    public String getQuantidadeAndMetrica() {
        return this.quantidade + this.metrica;
    }
    
    public void setMetrica(String metrica) {
        this.metrica = metrica;
    }
    
    public String getMetrica() {
        return this.metrica;
    }
    
    
    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    public void setPreco(String preco) {
        this.preco = getPriceAsDouble(preco);
    }
    
    public double getPreco() {
        return this.preco;
    }
    
    public String getPrecoInReais() {
        return formatToReais(this.preco);
    }
}
