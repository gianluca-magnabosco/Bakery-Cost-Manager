package biscoitagem.produtos;

import biscoitagem.domain.InsumoBSON;
import biscoitagem.domain.ReceitaBSON;
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


public class ProdutosTopPanel extends JPanel {
    private JTextField nomeTextField;
    private JTextField rendimentoTextField;
    private JTextField horasTrabalhadasTextField;
    private JTextField complexidadeTextField;
    private JComboBox metricaComboBox;
    private List<String> metricaComboBoxItems;
    private JPanel nomePanel;
    private JPanel rendimentoPanel;
    private JPanel horasTrabalhadasPanel;
    private JPanel complexidadePanel;
    
    private JPanel receitaPanel;
    private JComboBox receitaComboBox;
    private List<String> receitaComboBoxItems;
    private List<ReceitaBSON> receitas;
    private JButton receitaButton;
    
    private JPanel insumoPanel;
    private JComboBox insumoComboBox;
    private List<String> insumoComboBoxItems;
    private List<InsumoBSON> insumos;
    private JButton insumoButton;
    
    private TabledTopPanel receitasTopPanel;
    private TabledTopPanel insumosTopPanel;
    
    
    public ProdutosTopPanel() {
        super();
        this.initComponents();
        this.addMouseListeners();
        this.insumosTopPanel = new TabledTopPanel("Insumos");
        this.receitasTopPanel = new TabledTopPanel("Receitas");
    }
    
    
    public List<ReceitaBSON> getReceitas() {
        return this.receitas;
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
        
    public void setHorasTrabalhadasTextField(int horasTrabalhadas) {
        this.horasTrabalhadasTextField.setText(Integer.toString(horasTrabalhadas));
    }
    
    public int getHorasTrabalhadasTextField() {
        return Integer.parseInt(this.horasTrabalhadasTextField.getText());
    }
    
    public void setComplexidadeTextField(int complexidade) {
        this.complexidadeTextField.setText(Integer.toString(complexidade));
    }
    
    public int getComplexidadeTextField() {
        return Integer.parseInt(this.complexidadeTextField.getText());
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
        this.horasTrabalhadasTextField.setText("");
        this.complexidadeTextField.setText("");
        
        this.insumos = new ArrayList();
        this.insumoComboBoxItems = new ArrayList<>();
        this.insumoComboBox.setModel(new DefaultComboBoxModel(this.insumoComboBoxItems.toArray()));
        
        this.receitas = new ArrayList();
        this.receitaComboBoxItems = new ArrayList<>();
        this.receitaComboBox.setModel(new DefaultComboBoxModel(this.receitaComboBoxItems.toArray()));

        this.insumosTopPanel.clearFields();
        this.receitasTopPanel.clearFields();
    }
    
    
    public boolean askForProdutoInput() {
        int result = JOptionPane.showConfirmDialog(null, this, "Inserir Produto", JOptionPane.OK_CANCEL_OPTION, 1, new ImageIcon(getClass().getResource("/images/icons/icons8-product-64.png")));
        
        if (result != JOptionPane.OK_OPTION) {
            return false;
        }
        
        if (!this.validateInput()) {
            return false;
        }

        return true;
    }
    
    
    public void updateTopPanel(List<ReceitaBSON> receitas, List<InsumoBSON> insumos) {
        if (receitas != null) {
            this.receitas = receitas;
        } else { 
            this.receitas = new ArrayList<>();
        }
        this.receitaComboBoxItems = new ArrayList<>();
        if (!this.receitas.isEmpty()) {
            this.receitas.forEach(receita -> this.receitaComboBoxItems.add(receita.getNome()));
        }
        this.receitaComboBox.setModel(new DefaultComboBoxModel(this.receitaComboBoxItems.toArray()));
        this.receitasTopPanel.setProductReceitas(this.receitas);
        
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
        this.insumosTopPanel.setProductInsumos(this.insumos);
        
        this.receitasTopPanel.getTopPanel().resetComboBoxes();
        this.insumosTopPanel.getTopPanel().resetComboBoxes();
        
        this.receitasTopPanel.adjustReceitaTopPanelComboBox(this.receitas);
        this.insumosTopPanel.adjustInsumoTopPanelComboBox(this.insumos);
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
        
        if (!this.complexidadeTextField.getText().matches("\\d+") || (Integer.parseInt(this.complexidadeTextField.getText()) < 0) || Integer.parseInt(this.complexidadeTextField.getText()) > 10) {
            JOptionPane.showMessageDialog(new JFrame(), "Complexidade inválida - Deve ser um valor entre 0 e 10!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!this.horasTrabalhadasTextField.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(new JFrame(), "Horas trabalhadas inválidas!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    
    private void addMouseListeners() {
        this.insumoButton.addMouseListener(new ProdutosTopPanelMouseListener(this.insumoButton, "Insumos"));
        this.receitaButton.addMouseListener(new ProdutosTopPanelMouseListener(this.receitaButton, "Receitas"));
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
        this.horasTrabalhadasTextField = new JTextField(5);
        this.complexidadeTextField = new JTextField(5);
        this.nomePanel = new JPanel();
        this.rendimentoPanel = new JPanel();
        this.horasTrabalhadasPanel = new JPanel();
        this.complexidadePanel = new JPanel();
        
        this.receitas = new ArrayList<>();
        this.receitaPanel = new JPanel();
        this.receitaButton = new JButton("Alterar");
        this.receitaComboBox = new JComboBox();    
        this.receitaComboBoxItems = new ArrayList<String>();        
        
        this.insumos = new ArrayList<>();
        this.insumoPanel = new JPanel();
        this.insumoButton = new JButton("Alterar");
        this.insumoComboBox = new JComboBox();    
        this.insumoComboBoxItems = new ArrayList<String>();
        
        
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
        
        this.add(Box.createVerticalStrut(5));
        
        this.horasTrabalhadasPanel.setLayout(new BoxLayout(this.horasTrabalhadasPanel, BoxLayout.X_AXIS));
        this.horasTrabalhadasPanel.add(Box.createHorizontalStrut(167));
        this.horasTrabalhadasPanel.add(new JLabel("Horas Trabalhadas:"));
        this.horasTrabalhadasPanel.add(Box.createHorizontalStrut(5));
        this.horasTrabalhadasPanel.add(this.horasTrabalhadasTextField);
        this.add(this.horasTrabalhadasPanel);
        
        this.add(Box.createVerticalStrut(5));
        
        this.complexidadePanel.setLayout(new BoxLayout(this.complexidadePanel, BoxLayout.X_AXIS));
        this.complexidadePanel.add(Box.createHorizontalStrut(160));
        this.complexidadePanel.add(new JLabel("Complexidade [0-10]:"));
        this.complexidadePanel.add(Box.createHorizontalStrut(5));
        this.complexidadePanel.add(this.complexidadeTextField);
        this.add(this.complexidadePanel);
        
        
        this.add(Box.createVerticalStrut(2));
        
        this.receitaPanel.add(Box.createHorizontalStrut(30));
        this.receitaPanel.add(new JLabel("Receitas: "));
        for (ReceitaBSON receita : this.receitas) {
            this.receitaComboBoxItems.add(receita.getNome());
        }
        this.receitaComboBox.setModel(new DefaultComboBoxModel(this.receitaComboBoxItems.toArray()));
        this.receitaComboBox.setPreferredSize(new Dimension(190, 27));
        this.receitaPanel.add(this.receitaComboBox);        
        this.receitaPanel.add(this.receitaButton);
        this.add(this.receitaPanel);        
        
        
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
    }    

    
    public class ProdutosTopPanelMouseListener extends java.awt.event.MouseAdapter {
        JButton button;
        String text;
        
        
        ProdutosTopPanelMouseListener(JButton button, String text) {
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
                case "Receitas" -> {
                    receitasTopPanel.setColumnNames(new String[]{"Nome", "Quantidade"});
                    receitasTopPanel.askForInput();
                    receitas = receitasTopPanel.getProductReceitas();
                    receitaComboBoxItems = new ArrayList<>();
                    
                    for (ReceitaBSON receita : receitas) {
                        receitaComboBoxItems.add(receita.getNome());
                    }
                    receitaComboBox.setModel(new DefaultComboBoxModel(receitaComboBoxItems.toArray()));
                }
                
                case "Insumos" -> {
                    insumosTopPanel.setColumnNames(new String[]{"Nome", "Quantidade"});
                    insumosTopPanel.askForInput();
                    insumos = insumosTopPanel.getProductInsumos();
                    insumoComboBoxItems = new ArrayList<>();
                    
                    for (InsumoBSON insumo : insumos) {
                        insumoComboBoxItems.add(insumo.getNome());
                    }
                    insumoComboBox.setModel(new DefaultComboBoxModel(insumoComboBoxItems.toArray()));
                }
                
                default -> {
                }
            }
        } 
    }
}
