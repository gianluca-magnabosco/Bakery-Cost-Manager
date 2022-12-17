package biscoitagem.eletrodomesticos;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class EletrodomesticosTopPanel extends JPanel {
    
    private JTextField nomeTextField;
    private JTextField potenciaTextField;
    
    
    EletrodomesticosTopPanel() {
        super();
        this.initEletrodomesticosTopPanel();
    }

    
    public void setNomeTextFieldText(String text) {
        this.nomeTextField.setText(text);
    }
    
    public String getNomeTextFieldValue() {
        return this.nomeTextField.getText();
    }
    
    public void setPotenciaTextFieldText(String text) {
        this.potenciaTextField.setText(text);
    }
    
    public void setPotenciaTextFieldText(double valor) {
        this.potenciaTextField.setText(valor + "W");
    }
    
    public int getPotenciaTextFieldText() {
        return Integer.parseInt(this.potenciaTextField.getText().replaceAll("\\s", "").replaceAll("[Ww]", ""));
    }
    
    public String getPotenciaWithWatts() {
        return this.potenciaTextField.getText() + "W";
    }
    
    
        
    public void clearFields() {
        this.nomeTextField.setText("");
        this.potenciaTextField.setText("");
    }
    
    
    public boolean askForEletrodomesticoInput() {
        int result = JOptionPane.showConfirmDialog(null, this, "Inserir Eletrodoméstico", JOptionPane.OK_CANCEL_OPTION, 1, new ImageIcon(getClass().getResource("/images/icons/icons8-blender-40.png")));
        
        if (result != JOptionPane.OK_OPTION) {
            return false;
        }
        
        return this.validateInput();
    }
    
    
    private boolean validateInput() {
        if (!this.nomeTextField.getText().matches(".+")) {
            JOptionPane.showMessageDialog(new JFrame(), "Nome inválido!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!this.potenciaTextField.getText().matches("\\d+\\s*[Ww]?")) {
            JOptionPane.showMessageDialog(new JFrame(), "Potência inválida!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return false;
        }         
        
        return true;
    }
    
    
    private void initEletrodomesticosTopPanel() {
        this.nomeTextField = new JTextField(10);
        this.potenciaTextField = new JTextField(5);

        this.add(new JLabel("Nome:"));
        this.add(this.nomeTextField);
        
        this.add(Box.createHorizontalStrut(15));
        
        this.add(new JLabel("Potência:"));
        this.add(this.potenciaTextField);
    }
}
