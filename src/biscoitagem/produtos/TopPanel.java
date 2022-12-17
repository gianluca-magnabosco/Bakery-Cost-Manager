package biscoitagem.produtos;

import biscoitagem.dao.ConnectionFactory;
import biscoitagem.dao.InsumoDAO;
import biscoitagem.dao.MongoClientFactory;
import biscoitagem.dao.ReceitaDAO;
import biscoitagem.domain.Insumo;
import biscoitagem.domain.InsumoBSON;
import biscoitagem.domain.Receita;
import biscoitagem.domain.ReceitaBSON;
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
    private JComboBox receitasComboBox = new JComboBox();
    private JComboBox insumosComboBox = new JComboBox();
    private JPanel bottomContainer;
    private JLabel quantidadeLabel;
    private JTextField quantidadeTextField;    
    private String title;
    
    private List<Receita> receitaList = new ArrayList<>();
    private List<String> receitaNameList;
    private List<Insumo> insumoList = new ArrayList<>();
    private List<String> insumoNameList;
    
    
    public TopPanel(String title) {
        super();
        this.title = title;
        this.initComponents();
        this.quantidadeLabel.setText("Quantidade: ");
    }
    
    
    public void removeCheckBoxProductByName() {
        if (this.title.equals("Receitas")) {
            this.receitaNameList.remove(this.receitasComboBox.getSelectedIndex());
            this.receitasComboBox.setModel(new DefaultComboBoxModel(this.receitaNameList.toArray()));
            return;
        }
        
        this.insumoNameList.remove(this.insumosComboBox.getSelectedIndex());
        this.insumosComboBox.setModel(new DefaultComboBoxModel(this.insumoNameList.toArray()));
    }
    
    
    public void removeFromReceitaComboBox(List<ReceitaBSON> receitas) {
        for (ReceitaBSON receita : receitas) {
            for (int i = 0; i < this.receitaNameList.size(); i++) {
                if (receita.getNome().equals(this.receitaNameList.get(i))) {
                    this.receitaNameList.remove(i);
                }
            }
        }
        this.receitasComboBox.setModel(new DefaultComboBoxModel(this.receitaNameList.toArray()));
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
    
    
 
    public List<String> getReceitaNameList() {
        return this.receitaNameList;
    }
    
    public List<Receita> getReceitaList() {
        return this.receitaList;
    }
    
    public List<Insumo> getInsumoList() {
        return this.insumoList;
    }

    public List<String> getInsumoNameList() {
        return this.insumoNameList;
    }
    
    public int getQuantidadeTextField() {
        return Integer.parseInt(this.quantidadeTextField.getText());
    }
    
    public JComboBox getReceitasComboBox() {
        return this.receitasComboBox;
    }
    
    public JComboBox getInsumosComboBox() {
        return this.insumosComboBox;
    }
    
    public String getCheckBoxSelection() {
        if (this.title.equals("Receitas")) {
            if (this.receitasComboBox.getSelectedItem() == null) {
                return null;
            }
            return this.receitasComboBox.getSelectedItem().toString();
        }
        
        if (this.insumosComboBox.getSelectedItem() == null) {
            return null;
        }
        return this.insumosComboBox.getSelectedItem().toString();
    }
    
    
    public void resetComboBoxes() {
        this.receitasComboBox.setModel(new DefaultComboBoxModel(this.getReceitaComboBoxItems().toArray()));
        this.insumosComboBox.setModel(new DefaultComboBoxModel(this.getInsumoComboBoxItems().toArray()));
    }
    
    
    public boolean askForInput() {
        this.quantidadeTextField.setText("");
        
        ImageIcon icon = null;
        if (this.title.equals("Receitas")) {
            icon = new ImageIcon(getClass().getResource("/images/icons/icons8-recipe-book-100.png"));
        } else {
            icon = new ImageIcon(getClass().getResource("/images/icons/icons8-wrapping-100.png"));
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
        if (this.title.equals("Receitas")) {
            this.receitasComboBox = new JComboBox();
            this.receitasComboBox.setModel(new DefaultComboBoxModel(this.getReceitaComboBoxItems().toArray()));
            this.receitasComboBox.setPreferredSize(new Dimension(190, 27));
            this.topContainer.add(this.receitasComboBox);            
        } else {
            this.insumosComboBox = new JComboBox();
            this.insumosComboBox.setModel(new DefaultComboBoxModel(this.getInsumoComboBoxItems().toArray()));
            this.insumosComboBox.setPreferredSize(new Dimension(190, 27));
            this.topContainer.add(this.insumosComboBox);
        }
        
        this.bottomContainer.add(quantidadeLabel);
        this.bottomContainer.add(this.quantidadeTextField);
        if (this.title.equals("Receitas")) {
            this.bottomContainer.add(Box.createHorizontalStrut(168));
        } else {
            this.bottomContainer.add(Box.createHorizontalStrut(100));
        }
                
        this.add(this.topContainer);
        this.add(this.bottomContainer);
    }
    
    
    public List<String> getReceitaComboBoxItems() {
        this.receitaNameList = new ArrayList<>();
        try (MongoClientFactory factory = new MongoClientFactory()) {
            ReceitaDAO dao = new ReceitaDAO(factory.getClient());
            this.receitaList = dao.search();
            
            for (Receita receita : this.receitaList) {
                this.receitaNameList.add(receita.getNome());
            }
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }
        
        return this.receitaNameList;        
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
}
