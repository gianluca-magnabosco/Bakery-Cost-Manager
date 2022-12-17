package biscoitagem.domain;

import java.io.Serializable;
import static biscoitagem.utils.InputFormatHandler.formatToReais;
import static biscoitagem.utils.InputFormatHandler.getPriceAsDouble;


public class Eletrodomestico implements Serializable {
    private String nome;
    private int potencia;
    private double precoHora;
    
    public Eletrodomestico() {
        
    }
    
    
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public int getPotencia() {
        return this.potencia;
    }
    
    public String getPotenciaAndMetrica() {
        return this.potencia + "W";
    }
    
    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }
    
    public double getPrecoPorMinuto() {
        return (this.precoHora / 60);
    }    
    
    public String getPrecoPorMinutoInReais() {
        return formatToReais((double) Math.round((this.precoHora / 60) * 100.0) / 100.0);
    }
    
    public String getPrecoPorMinutoInReais(int tempo) {
        System.out.println((double) Math.round((this.getPrecoPorMinuto() * tempo) * 100.0) / 100.0);
        return formatToReais((double) Math.round((this.getPrecoPorMinuto() * tempo) * 100.0) / 100.0);
    }
    
    public double getPrecoPorHora() {
        return this.precoHora;
    }
    
    public void setPrecoPorMinuto(double valor) {
        this.precoHora = valor * 60;
    }
    
    public void setPrecoPorMinuto(String valor) {
        this.precoHora = getPriceAsDouble(valor) * 60;
    }
    
    public void setPrecoPorHora(double valor) {
        this.precoHora = valor;
    }
    
    public void setPrecoPorHora(String valor) {
        this.precoHora = getPriceAsDouble(valor);
    }
}
