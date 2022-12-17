package biscoitagem.receitas;

import biscoitagem.dao.ConnectionFactory;
import biscoitagem.dao.EletrodomesticoDAO;
import biscoitagem.dao.InsumoDAO;
import biscoitagem.domain.Eletrodomestico;
import biscoitagem.domain.EletrodomesticoBSON;
import biscoitagem.domain.Insumo;
import biscoitagem.domain.InsumoBSON;
import biscoitagem.exception.DAOException;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class TopPanel extends JPanel {
    private JPanel topContainer;
    private JLabel produtoLabel;
    private JComboBox insumosComboBox = new JComboBox();
    private JComboBox eletrodomesticosComboBox = new JComboBox();
    private JPanel bottomContainer;
    private JLabel quantidadeLabel;
    private JTextField quantidadeTextField;    
    private String title;
    
    private List<Insumo> insumoList = new ArrayList<>();
    private List<String> insumoNameList;
    private List<Eletrodomestico> eletrodomesticoList = new ArrayList<>();
    private List<String> eletrodomesticoNameList;
    
    
    public TopPanel(String title) {
        super();
        this.title = title;
        this.initComponents();
        if (this.title.equals("Insumos")) {
            this.quantidadeLabel.setText("Quantidade: ");
        } else {
            this.quantidadeLabel.setText("Tempo: ");
        }
    }
    
    
    public void removeCheckBoxProductByName() {
        if (this.title.equals("Insumos")) {
            this.insumoNameList.remove(this.insumosComboBox.getSelectedIndex());
            this.insumosComboBox.setModel(new DefaultComboBoxModel(this.insumoNameList.toArray()));
            return;
        }
        
        this.eletrodomesticoNameList.remove(this.eletrodomesticosComboBox.getSelectedIndex());
        this.eletrodomesticosComboBox.setModel(new DefaultComboBoxModel(this.eletrodomesticoNameList.toArray()));
    }
    
    
    public void removeFromInsumoComboBox(List<InsumoBSON> insumos) {
        for (InsumoBSON insumo : insumos) {
            for (int i = 0; i < this.insumoNameList.size(); i++) {
                if (insumo.getNome().equals(this.insumoNameList.get(i))) {
                    this.insumoNameList.remove(i);
                }
            }
        }
        this.insumosComboBox.setModel(new DefaultComboBoxModel(this.insumoNameList.toArray()));
    }
    
    
    public void removeFromEletrodomesticoComboBox(List<EletrodomesticoBSON> eletrodomesticos) {
        for (EletrodomesticoBSON eletrodomestico : eletrodomesticos) {
            for (int i = 0; i < this.eletrodomesticoNameList.size(); i++) {
                if (eletrodomestico.getNome().equals(this.eletrodomesticoNameList.get(i))) {
                    this.eletrodomesticoNameList.remove(i);
                }
            }
        }
        this.eletrodomesticosComboBox.setModel(new DefaultComboBoxModel(this.eletrodomesticoNameList.toArray()));
    }
    
    
    public List<Insumo> getInsumoList() {
        return this.insumoList;
    }

    public List<String> getInsumoNameList() {
        return this.insumoNameList;
    }
   
    
    public List<String> getEletrodomesticoNameList() {
        return this.eletrodomesticoNameList;
    }
    
    public List<Eletrodomestico> getEletrodomesticoList() {
        return this.eletrodomesticoList;
    }
    
    public int getQuantidadeTextField() {
        return Integer.parseInt(this.quantidadeTextField.getText());
    }
    
    public JComboBox getInsumosComboBox() {
        return this.insumosComboBox;
    }
    
    public JComboBox getEletrodomesticosComboBox() {
        return this.eletrodomesticosComboBox;
    }
    
    public String getCheckBoxSelection() {
        if (this.title.equals("Insumos")) {
            if (this.insumosComboBox.getSelectedItem() == null) {
                return null;
            }
            return this.insumosComboBox.getSelectedItem().toString();
        }
        
        if (this.eletrodomesticosComboBox.getSelectedItem() == null) {
            return null;
        }
        return this.eletrodomesticosComboBox.getSelectedItem().toString();
    }
    
    
    public void resetComboBoxes() {
        this.insumosComboBox.setModel(new DefaultComboBoxModel(this.getInsumoComboBoxItems().toArray()));
        this.eletrodomesticosComboBox.setModel(new DefaultComboBoxModel(this.getEletrodomesticoComboBoxItems().toArray()));
    }
    
    
    public boolean askForInput() {
        this.quantidadeTextField.setText("");
        
        ImageIcon icon = null;
        if (this.title.equals("Insumos")) {
            icon = new ImageIcon(getClass().getResource("/images/icons/icons8-ingredients-100.png"));
        } else {
            icon = new ImageIcon(getClass().getResource("/images/icons/icons8-blender-100.png"));
        }
        int result = JOptionPane.showConfirmDialog(null, this, "Inserir " + this.title, JOptionPane.OK_CANCEL_OPTION, 1, icon);
        
        if (result != JOptionPane.OK_OPTION) {
            return false;
        } 
        
        if (!this.validateInput()) {
            return false;
        }
                
        return true;
    }      
    
    
    private boolean validateInput() {
        if (!this.quantidadeTextField.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(new JFrame(), "Quantidade inválida!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return false;
        }         
        
        if (this.getCheckBoxSelection() == null) {
            JOptionPane.showMessageDialog(new JFrame(), "Nenhum produto selecionado!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    
    private void initComponents() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.topContainer = new JPanel();
        this.bottomContainer = new JPanel();
        this.produtoLabel = new JLabel(this.title.substring(0, this.title.length() - 1) + ": ");
        this.quantidadeTextField = new JTextField(5);
        this.quantidadeLabel = new JLabel(); 
        
        this.topContainer.add(this.produtoLabel);
        if (this.title.equals("Insumos")) {
            this.insumosComboBox = new JComboBox();
            this.insumosComboBox.setModel(new DefaultComboBoxModel(this.getInsumoComboBoxItems().toArray()));
            this.insumosComboBox.setPreferredSize(new Dimension(190, 27));
            this.topContainer.add(this.insumosComboBox);
        } else {
            this.eletrodomesticosComboBox = new JComboBox();
            this.eletrodomesticosComboBox.setModel(new DefaultComboBoxModel(this.getEletrodomesticoComboBoxItems().toArray()));
            this.eletrodomesticosComboBox.setPreferredSize(new Dimension(190, 27));
            this.topContainer.add(this.eletrodomesticosComboBox);            
        }
        
        this.bottomContainer.add(quantidadeLabel);
        this.bottomContainer.add(this.quantidadeTextField);
        if (this.title.equals("Insumos")) {
            this.bottomContainer.add(Box.createHorizontalStrut(100));
        } else {
            this.bottomContainer.add(Box.createHorizontalStrut(168));
        }
                
        this.add(this.topContainer);
        this.add(this.bottomContainer);
    }
    
    
    public List<String> getInsumoComboBoxItems() {
        this.insumoNameList = new ArrayList<>();
        try (ConnectionFactory factory = new ConnectionFactory()) {
            InsumoDAO dao = new InsumoDAO(factory.getConnection());
            this.insumoList = dao.search();
            
            for (Insumo insumo : this.insumoList) {
                this.insumoNameList.add(insumo.getNome());
            }
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }
        
        return this.insumoNameList;
    }
    
    
    public List<String> getEletrodomesticoComboBoxItems() {
        this.eletrodomesticoNameList = new ArrayList<>();
        try (ConnectionFactory factory = new ConnectionFactory()) {
            EletrodomesticoDAO dao = new EletrodomesticoDAO(factory.getConnection());
            this.eletrodomesticoList = dao.search();
            
            for (Eletrodomestico eletrodomestico : this.eletrodomesticoList) {
                this.eletrodomesticoNameList.add(eletrodomestico.getNome());
            }
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }
        
        return this.eletrodomesticoNameList;        
    }
    
}
