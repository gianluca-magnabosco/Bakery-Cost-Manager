package biscoitagem.insumos;

import static biscoitagem.utils.InputFormatHandler.getPriceAsDouble;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class InsumosTopPanel extends JPanel {
    
    private JTextField nomeTextField;
    private JTextField quantidadeTextField;
    private JTextField precoTextField;
    private JComboBox comboBox;
    private List<String> comboBoxItems;
    
    InsumosTopPanel() {
        super();
        this.initInsumosTopPanel();
    }

    
    public void setNomeTextFieldText(String text) {
        this.nomeTextField.setText(text);
    }
    
    public String getNomeTextFieldValue() {
        return this.nomeTextField.getText();
    }
    
    public void setQuantidadeTextFieldText(String text) {
        this.quantidadeTextField.setText(text);
    }
    
    public int getQuantidadeTextFieldValue() {
        return Integer.parseInt(this.quantidadeTextField.getText());
    }
        
    public void setPrecoTextFieldText(String text) {
        this.precoTextField.setText(text);
    }
    
    public double getPrecoTextFieldValue() {
        return getPriceAsDouble(this.precoTextField.getText());
    }
    
    public void setComboBoxSelection(String item) {
        int i = 0;
        for (String value : this.comboBoxItems) {
            if (value.equals(item)) {
                this.comboBox.setSelectedIndex(i);
            }
            i++;
        }
    }
    
    public String getComboBoxSelection() {
        return this.comboBoxItems.get(this.comboBox.getSelectedIndex());
    }
    
        
    public void clearFields() {
        this.nomeTextField.setText("");
        this.quantidadeTextField.setText("");
        this.precoTextField.setText("");
        this.comboBox.setSelectedIndex(0);
    }
    
    
    public boolean askForInsumoInput() {
        int result = JOptionPane.showConfirmDialog(null, this, "Inserir Insumo", JOptionPane.OK_CANCEL_OPTION, 1, new ImageIcon(getClass().getResource("/images/icons/icons8-flour-40.png")));
        
        if (result != JOptionPane.OK_OPTION) {
            return false;
        }
        
        if (!this.validateInput()) {
            return false;
        }

        return true;
    }
    
    
    private boolean validateInput() {
        if (!this.nomeTextField.getText().matches(".+")) {
            JOptionPane.showMessageDialog(new JFrame(), "Nome inválido!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!this.quantidadeTextField.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(new JFrame(), "Quantidade inválida!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return false;
        }         

        if (getPriceAsDouble(this.precoTextField.getText()) == 0) {
            JOptionPane.showMessageDialog(new JFrame(), "Preço inválido!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    
    private void initInsumosTopPanel() {
        this.nomeTextField = new JTextField(10);
        this.quantidadeTextField = new JTextField(5);
        this.precoTextField = new JTextField(5);
        this.comboBox = new JComboBox();    
        this.comboBoxItems = new ArrayList<String>();
        this.comboBoxItems.add("g");
        this.comboBoxItems.add("ml");
        this.comboBoxItems.add("un");
        this.comboBox.setModel(new DefaultComboBoxModel(comboBoxItems.toArray()));
        
        this.add(new JLabel("Nome:"));
        this.add(this.nomeTextField);
        
        this.add(Box.createHorizontalStrut(15));
        
        this.add(new JLabel("Quantidade:"));
        this.add(this.quantidadeTextField);
        this.add(this.comboBox);
        
        this.add(Box.createHorizontalStrut(15));
        
        this.add(new JLabel("Preço:"));
        this.add(this.precoTextField);
    }
}
