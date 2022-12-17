package biscoitagem.receitas;

import biscoitagem.domain.EletrodomesticoBSON;
import biscoitagem.domain.InsumoBSON;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ReceitasTopPanel extends JPanel {
    private JTextField nomeTextField;
    private JTextField rendimentoTextField;
    private JComboBox metricaComboBox;
    private List<String> metricaComboBoxItems;
    private JPanel nomePanel;
    private JPanel rendimentoPanel;
    
    private JPanel insumoPanel;
    private JComboBox insumoComboBox;
    private List<String> insumoComboBoxItems;
    private List<InsumoBSON> insumos;
    private JButton insumoButton;
    
    private JPanel eletrodomesticoPanel;
    private JComboBox eletrodomesticoComboBox;
    private List<String> eletrodomesticoComboBoxItems;
    private List<EletrodomesticoBSON> eletrodomesticos;
    private JButton eletrodomesticoButton;
    
    private TabledTopPanel insumosTopPanel;
    private TabledTopPanel eletrodomesticosTopPanel;
    
    
    public ReceitasTopPanel() {
        super();
        this.initComponents();
        this.addMouseListeners();
        this.insumosTopPanel = new TabledTopPanel("Insumos");
        this.eletrodomesticosTopPanel = new TabledTopPanel("Eletrodomésticos");
    }
    
    
    public List<EletrodomesticoBSON> getEletrodomesticos() {
        return this.eletrodomesticos;
    }
    
    public List<InsumoBSON> getInsumos() {
        return this.insumos;
    }
    
    public void setNomeTextField(String text) {
        this.nomeTextField.setText(text);
    }
    
    public String getNomeTextField() {
        return this.nomeTextField.getText();
    }
    
    public void setRendimentoTextField(String text) {
        this.rendimentoTextField.setText(text);
    }
    
    public int getRendimentoTextField() {
        return Integer.parseInt(this.rendimentoTextField.getText());
    }
        
    
    public void setComboBoxSelection(String item) {
        int i = 0;
        for (String value : this.metricaComboBoxItems) {
            if (value.equals(item)) {
                this.metricaComboBox.setSelectedIndex(i);
            }
            i++;
        }
    }
    
    public String getComboBoxSelection() {
        return this.metricaComboBoxItems.get(this.metricaComboBox.getSelectedIndex());
    }
    
        
    public void clearFields() {
        this.nomeTextField.setText("");
        this.rendimentoTextField.setText("");
        this.metricaComboBox.setSelectedIndex(0);
        this.insumos = new ArrayList();
        this.insumoComboBoxItems = new ArrayList<>();
        this.insumoComboBox.setModel(new DefaultComboBoxModel(this.insumoComboBoxItems.toArray()));
        
        this.eletrodomesticos = new ArrayList();
        this.eletrodomesticoComboBoxItems = new ArrayList<>();
        this.eletrodomesticoComboBox.setModel(new DefaultComboBoxModel(this.eletrodomesticoComboBoxItems.toArray()));

        this.insumosTopPanel.clearFields();
        this.eletrodomesticosTopPanel.clearFields();
    }
    
    
    public boolean askForReceitaInput() {
        int result = JOptionPane.showConfirmDialog(null, this, "Inserir Receita", JOptionPane.OK_CANCEL_OPTION, 1, new ImageIcon(getClass().getResource("/images/icons/icons8-recipe-book-64.png")));
        
        if (result != JOptionPane.OK_OPTION) {
            return false;
        }
        
        if (!this.validateInput()) {
            return false;
        }

        return true;
    }
    
    
    public void updateTopPanel(List<InsumoBSON> insumos, List<EletrodomesticoBSON> eletrodomesticos) {
        
        if (insumos != null) {
            this.insumos = insumos;
        } else {
            this.insumos = new ArrayList<>();
        }
        this.insumoComboBoxItems = new ArrayList<>();
        if (!this.insumos.isEmpty()) {
            this.insumos.forEach(insumo -> this.insumoComboBoxItems.add(insumo.getNome()));
        }
        this.insumoComboBox.setModel(new DefaultComboBoxModel(this.insumoComboBoxItems.toArray()));
        this.insumosTopPanel.setRecipeInsumos(this.insumos);
        
        
        if (eletrodomesticos != null) {
            this.eletrodomesticos = eletrodomesticos;
        } else { 
            this.eletrodomesticos = new ArrayList<>();
        }
        this.eletrodomesticoComboBoxItems = new ArrayList<>();
        if (!this.eletrodomesticos.isEmpty()) {
            this.eletrodomesticos.forEach(eletrodomestico -> this.eletrodomesticoComboBoxItems.add(eletrodomestico.getNome()));
        }
        this.eletrodomesticoComboBox.setModel(new DefaultComboBoxModel(this.eletrodomesticoComboBoxItems.toArray()));
        this.eletrodomesticosTopPanel.setRecipeEletrodomesticos(this.eletrodomesticos);
        
        this.insumosTopPanel.getTopPanel().resetComboBoxes();
        this.eletrodomesticosTopPanel.getTopPanel().resetComboBoxes();
        
        this.insumosTopPanel.adjustInsumoTopPanelComboBox(this.insumos);
        this.eletrodomesticosTopPanel.adjustEletrodomesticoTopPanelComboBox(this.eletrodomesticos);
    }
    
    
    private boolean validateInput() {
        if (!this.nomeTextField.getText().matches(".+")) {
            JOptionPane.showMessageDialog(new JFrame(), "Nome inválido!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!this.rendimentoTextField.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(new JFrame(), "Rendimento inválido!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return false;
        }         
        
        return true;
    }
    
    
    private void addMouseListeners() {
        this.insumoButton.addMouseListener(new ReceitasTopPanelMouseListener(this.insumoButton, "Insumos"));
        this.eletrodomesticoButton.addMouseListener(new ReceitasTopPanelMouseListener(this.eletrodomesticoButton, "Eletrodomesticos"));
    }
    
    
    private void initComponents() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.nomeTextField = new JTextField(20);
        this.rendimentoTextField = new JTextField(5);
        this.metricaComboBox = new JComboBox();    
        this.metricaComboBoxItems = new ArrayList<String>();
        this.metricaComboBoxItems.add("g");
        this.metricaComboBoxItems.add("ml");
        this.metricaComboBoxItems.add("un");
        this.metricaComboBox.setModel(new DefaultComboBoxModel(this.metricaComboBoxItems.toArray()));
        this.nomePanel = new JPanel();
        this.rendimentoPanel = new JPanel();
        
        this.insumos = new ArrayList<>();
        this.insumoPanel = new JPanel();
        this.insumoButton = new JButton("Alterar");
        this.insumoComboBox = new JComboBox();    
        this.insumoComboBoxItems = new ArrayList<String>();
        
        this.eletrodomesticos = new ArrayList<>();
        this.eletrodomesticoPanel = new JPanel();
        this.eletrodomesticoButton = new JButton("Alterar");
        this.eletrodomesticoComboBox = new JComboBox();    
        this.eletrodomesticoComboBoxItems = new ArrayList<String>();        
        
        
        this.nomePanel.setLayout(new BoxLayout(this.nomePanel, BoxLayout.X_AXIS));
        this.nomePanel.add(new JLabel("Nome:"));
        this.nomePanel.add(Box.createHorizontalStrut(5));
        this.nomePanel.add(this.nomeTextField);
        this.add(this.nomePanel);
        
        this.add(Box.createVerticalStrut(5));
        
        this.rendimentoPanel.setLayout(new BoxLayout(this.rendimentoPanel, BoxLayout.X_AXIS));
        this.rendimentoPanel.add(Box.createHorizontalStrut(150));
        this.rendimentoPanel.add(new JLabel("Rendimento:"));
        this.rendimentoPanel.add(Box.createHorizontalStrut(5));
        this.rendimentoPanel.add(this.rendimentoTextField);
        this.rendimentoPanel.add(this.metricaComboBox);
        this.add(this.rendimentoPanel);
        
        this.add(Box.createVerticalStrut(1));
        
        this.insumoPanel.add(Box.createHorizontalStrut(30));
        this.insumoPanel.add(new JLabel("Insumos: "));
        for (InsumoBSON insumo : this.insumos) {
            this.insumoComboBoxItems.add(insumo.getNome());
        }
        this.insumoComboBox.setModel(new DefaultComboBoxModel(this.insumoComboBoxItems.toArray()));
        this.insumoComboBox.setPreferredSize(new Dimension(190, 27));
        this.insumoPanel.add(this.insumoComboBox);
        this.insumoPanel.add(this.insumoButton);
        this.add(this.insumoPanel);
        
        
        this.eletrodomesticoPanel.add(new JLabel("Eletrodomésticos: "));
        for (EletrodomesticoBSON eletrodomestico : this.eletrodomesticos) {
            this.eletrodomesticoComboBoxItems.add(eletrodomestico.getNome());
        }
        this.eletrodomesticoComboBox.setModel(new DefaultComboBoxModel(this.eletrodomesticoComboBoxItems.toArray()));
        this.eletrodomesticoComboBox.setPreferredSize(new Dimension(177, 27));
        this.eletrodomesticoPanel.add(this.eletrodomesticoComboBox);        
        this.eletrodomesticoPanel.add(this.eletrodomesticoButton);
        this.add(this.eletrodomesticoPanel);        
    }    

    
    public class ReceitasTopPanelMouseListener extends java.awt.event.MouseAdapter {
        JButton button;
        String text;
        
        
        ReceitasTopPanelMouseListener(JButton button, String text) {
            this.button = button;
            this.text = text;
        }
        
        @Override
        public void mouseEntered(MouseEvent event) {

        }
        
        @Override
        public void mouseExited(MouseEvent event) {  

        }   
        
        @Override
        public void mouseClicked(MouseEvent event) {   
            switch (this.text) {
                case "Insumos" -> {
                    insumosTopPanel.setColumnNames(new String[]{"Nome", "Quantidade"});
                    insumosTopPanel.askForInput();
                    insumos = insumosTopPanel.getRecipeInsumos();
                    insumoComboBoxItems = new ArrayList<>();
                    
                    for (InsumoBSON insumo : insumos) {
                        insumoComboBoxItems.add(insumo.getNome());
                    }
                    insumoComboBox.setModel(new DefaultComboBoxModel(insumoComboBoxItems.toArray()));
                }
                
                case "Eletrodomesticos" -> {
                    eletrodomesticosTopPanel.setColumnNames(new String[]{"Nome", "Tempo"});
                    eletrodomesticosTopPanel.askForInput();
                    eletrodomesticos = eletrodomesticosTopPanel.getRecipeEletrodomesticos();
                    eletrodomesticoComboBoxItems = new ArrayList<>();
                    
                    for (EletrodomesticoBSON eletrodomestico : eletrodomesticos) {
                        eletrodomesticoComboBoxItems.add(eletrodomestico.getNome());
                    }
                    eletrodomesticoComboBox.setModel(new DefaultComboBoxModel(eletrodomesticoComboBoxItems.toArray()));
                }

                default -> {
                }
            }
        } 
    }
}
