package biscoitagem.domain;

import static biscoitagem.utils.InputFormatHandler.formatToReais;
import static biscoitagem.utils.InputFormatHandler.getPriceAsDouble;
import java.io.Serializable;


public class HorasTrabalhadas implements Serializable {
    private double value;
    
    public HorasTrabalhadas() {
        
    }
    
    public HorasTrabalhadas(double value) {
        this.value = value;
    }
    
    public void setValueAsDouble(double value) {
        this.value = value;
    }
    
    public double getValueAsDouble() {
        return this.value;
    }
    
    public void setValueInReais(String value) {
        this.value = getPriceAsDouble(value);
    }
    
    public String getValueInReais() {
        return formatToReais(this.value);
    }
}
