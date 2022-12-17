package biscoitagem.domain;

import static biscoitagem.utils.InputFormatHandler.formatToReais;
import static biscoitagem.utils.InputFormatHandler.getPriceAsDouble;
import java.io.Serializable;

public class KWh implements Serializable {
    private double kwh;
    
    public KWh() {
        
    }
    
    public KWh(double kwh) {
        this.kwh = kwh;
    }
    
    public KWh(String kwh) {
        this.kwh = getPriceAsDouble(kwh);
    }
    
    public double getKWhAsDouble() {
        return this.kwh;
    }
    
    public String getKWhInReais() {
        return formatToReais(this.kwh);
    }
    
    public void setKWh(String valor) {
        this.kwh = getPriceAsDouble(valor);
    }
    
    public void setKWh(double valor) {
        this.kwh = valor;
    }
    
}
