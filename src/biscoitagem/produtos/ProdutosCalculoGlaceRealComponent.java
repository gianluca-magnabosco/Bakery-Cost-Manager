package biscoitagem.produtos;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ProdutosCalculoGlaceRealComponent {
    private JLabel quantidadeProdutoLabel;
    private JTextField quantidadeProdutoTextField;
    private JLabel quantidadeGlaceRealLabel;
    private JTextField quantidadeGlaceRealTextField;
    
    private JLabel quantidadeResultLabel;   
    private JTextField quantidadeResultTextField;
    private JLabel calcularMediaLabel;
    private JPanel calcularMediaPanel;
    private JLabel calcularMediaPanelLabel;
    
    private JPanel panel;
    
    public ProdutosCalculoGlaceRealComponent(JPanel panel) {
        this.panel = panel;
    }
    
    public JPanel getButtonPanel() {
        return this.calcularMediaPanel;
    }
    
    public JLabel getButtonLabel() {
        return this.calcularMediaPanelLabel;
    }

    
    public void clearTextFields() {
        this.quantidadeProdutoTextField.setText("");
        this.quantidadeGlaceRealTextField.setText("");
        this.quantidadeResultTextField.setText("0g");
    }
    
    
    public void calcular() {
        if (!validateInputs()) {
            this.quantidadeResultTextField.setText("0g");
            return;
        }
        
        int quantidadeProduto = Integer.parseInt(this.quantidadeProdutoTextField.getText());
        int quantidadeGlaceReal = Integer.parseInt(this.quantidadeGlaceRealTextField.getText());

        int precoResultValue = quantidadeProduto * quantidadeGlaceReal;
        
        this.quantidadeResultTextField.setText(Integer.toString(precoResultValue) + "g");
    }    
    
    
    
    public void initComponents() {
        this.calcularMediaLabel = new javax.swing.JLabel();
        this.calcularMediaLabel.setFont(new java.awt.Font("Segoe UI", 1, 18));
        this.calcularMediaLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/icons8-pastry-bag-64.png")));
        this.calcularMediaLabel.setText("Cálculo de Glacê Real");
        this.panel.add(this.calcularMediaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 390, 360, 70));
        
        initAmountComponents();

        initButtonComponents();
    }

    
    
    private void initAmountComponents() {
        this.quantidadeProdutoLabel = new javax.swing.JLabel();
        this.quantidadeProdutoLabel.setText("Quantidade de Biscoitos");
        this.panel.add(this.quantidadeProdutoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 510, -1, -1));
        
        this.quantidadeProdutoTextField = new javax.swing.JTextField();  
        this.quantidadeProdutoTextField.setHorizontalAlignment(JTextField.CENTER);
        this.panel.add(this.quantidadeProdutoTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 475, 100, 35));
        
        
        
        this.quantidadeGlaceRealLabel = new javax.swing.JLabel();
        this.quantidadeGlaceRealLabel.setText("Quantidade de Glacê por Biscoito");
        this.panel.add(this.quantidadeGlaceRealLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(257, 510, -1, -1));

        this.quantidadeGlaceRealTextField = new javax.swing.JTextField();
        this.quantidadeGlaceRealTextField.setHorizontalAlignment(JTextField.CENTER);
        this.panel.add(this.quantidadeGlaceRealTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 475, 100, 35));

        
        
        this.quantidadeResultLabel = new javax.swing.JLabel();
        this.quantidadeResultLabel.setText("Quantidade Total");
        this.panel.add(this.quantidadeResultLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 580, -1, -1));

        this.quantidadeResultTextField = new javax.swing.JTextField();
        this.quantidadeResultTextField.setText("0g");
        this.quantidadeResultTextField.setHorizontalAlignment(JTextField.CENTER);
        this.quantidadeResultTextField.setFont(new Font("Segoe UI", 1, 17));
        this.quantidadeResultTextField.setEditable(false);
        this.panel.add(this.quantidadeResultTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 545, 75, 35));
    }
    
    
    
    private void initButtonComponents() {
        this.calcularMediaPanelLabel = new javax.swing.JLabel();
        this.calcularMediaPanelLabel.setBackground(new java.awt.Color(255, 255, 255));
        this.calcularMediaPanelLabel.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        this.calcularMediaPanelLabel.setForeground(new java.awt.Color(255, 255, 255));
        this.calcularMediaPanelLabel.setText("Calcular");

        this.calcularMediaPanel = new javax.swing.JPanel();
        this.calcularMediaPanel.setBackground(new java.awt.Color(57, 180, 74));
        this.calcularMediaPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(57, 170, 73), 5, true));
        
        javax.swing.GroupLayout calcularMediaPanelLayout = new javax.swing.GroupLayout(this.calcularMediaPanel);
        this.calcularMediaPanel.setLayout(calcularMediaPanelLayout);
        calcularMediaPanelLayout.setHorizontalGroup(
            calcularMediaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(calcularMediaPanelLayout.createSequentialGroup()
                .addComponent(this.calcularMediaPanelLabel)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        calcularMediaPanelLayout.setVerticalGroup(
            calcularMediaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(this.calcularMediaPanelLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        this.panel.add(this.calcularMediaPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 610, 75, 35));
    }
    
    
    private boolean checkAmount(String amount) {
        return amount.matches("\\d+");
    }
    
    
    private boolean validateInputs() {

        if (!validateAmounts()) {
            return false;
        }
        
        return true;
    }
    
    
    private boolean validateAmounts() {
        if (!checkAmount(this.quantidadeProdutoTextField.getText())) {
            JOptionPane.showMessageDialog(new JFrame(), "Quantidade de Biscoitos Inválida!", "Erro!", JOptionPane.ERROR_MESSAGE);
            this.quantidadeProdutoTextField.setText("");
            return false;            
        }         
        
        if (!checkAmount(this.quantidadeGlaceRealTextField.getText())) {
            JOptionPane.showMessageDialog(new JFrame(), "Quantidade de Glacê Real por Biscoito Inválida!", "Erro!", JOptionPane.ERROR_MESSAGE);
            this.quantidadeGlaceRealTextField.setText("");
            return false;            
        }      
        
        return true;
    }
}
