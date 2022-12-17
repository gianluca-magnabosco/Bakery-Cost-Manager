package biscoitagem.insumos;

import static biscoitagem.utils.InputFormatHandler.getPriceAsDouble;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InsumosAverageComponent {
    private JLabel preco1Label;
    private JTextField preco1;
    private JLabel quantidade1Label;
    private JTextField quantidade1;
    
    private JLabel plusSignLabel;
    
    private JLabel preco2Label;
    private JTextField preco2;
    private JLabel quantidade2Label;
    private JTextField quantidade2;
    private JLabel precoResultLabel;
    private JTextField precoResult;
    private JLabel quantidadeResultLabel;   
    private JTextField quantidadeResult;
    private JLabel calcularMediaLabel;
    private JPanel calcularMediaPanel;
    private JLabel calcularMediaPanelLabel;
    
    private JPanel panel;
    
    public InsumosAverageComponent(JPanel panel) {
        this.panel = panel;
    }
    
    public JPanel getButtonPanel() {
        return this.calcularMediaPanel;
    }
    
    public JLabel getButtonLabel() {
        return this.calcularMediaPanelLabel;
    }

    
    public void clearTextFields() {
        this.preco1.setText("");
        this.preco2.setText("");
        this.precoResult.setText("");
        this.quantidade1.setText("");
        this.quantidade2.setText("");
        this.quantidadeResult.setText("1000");
    }
    
    
    public void calcularMedia() {
        if (!validateInputs()) {
            this.precoResult.setText("");
            return;
        }
        
        double preco1Value = getPriceAsDouble(this.preco1.getText());
        int quantidade1Value = Integer.parseInt(this.quantidade1.getText());
        double preco2Value = getPriceAsDouble(this.preco2.getText());
        int quantidade2Value = Integer.parseInt(this.quantidade2.getText());
        
        int quantidadeDesejada = Integer.parseInt(this.quantidadeResult.getText());

        double precoResultValue = ((preco1Value + preco2Value) / (quantidade1Value + quantidade2Value)) * quantidadeDesejada;
        
        this.precoResult.setText("R$ " + Double.toString(Math.round(precoResultValue * 100.0) / 100.0).replaceAll("\\.", "\\,"));
    }    
    
    
    
    public void initComponents() {
        this.calcularMediaLabel = new javax.swing.JLabel();
        this.calcularMediaLabel.setFont(new java.awt.Font("Segoe UI", 1, 14));
        this.calcularMediaLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/icons8-estimate-75.png")));
        this.calcularMediaLabel.setText("Calcular Média");
        this.panel.add(this.calcularMediaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 65, 200, 70));
        
        initPriceComponents();
        initAmountComponents();
        
        this.plusSignLabel = new javax.swing.JLabel();        
        this.plusSignLabel.setText("+");
        this.panel.add(this.plusSignLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(251, 135, 10, 10));

        initButtonComponents();
    }
    
    
    private void initPriceComponents() {
        this.preco1Label = new javax.swing.JLabel();
        this.preco1Label.setText("Preço");
        this.panel.add(this.preco1Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 155, -1, -1));
        
        this.preco1 = new javax.swing.JTextField();
        this.preco1.setHorizontalAlignment(JTextField.CENTER);
        this.panel.add(this.preco1, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 125, 50, 30));
        
        
        
        this.preco2Label = new javax.swing.JLabel();
        this.preco2Label.setText("Preço");
        this.panel.add(this.preco2Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(275, 155, -1, -1));

        this.preco2 = new javax.swing.JTextField();
        this.preco2.setHorizontalAlignment(JTextField.CENTER);
        this.panel.add(this.preco2, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 125, 50, 30));

        
        
        this.precoResultLabel = new javax.swing.JLabel();
        this.precoResultLabel.setText("Preço");
        this.panel.add(this.precoResultLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 270, -1, -1));
    
        this.precoResult = new javax.swing.JTextField();
        this.precoResult.setEditable(false);
        this.precoResult.setHorizontalAlignment(JTextField.CENTER);
        this.precoResult.setFont(new Font("Segoe UI", 1, 17));
        this.panel.add(this.precoResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 235, 120, 35));
    }
    
    
    
    private void initAmountComponents() {
        this.quantidade1Label = new javax.swing.JLabel();
        this.quantidade1Label.setText("Qntd.");
        this.panel.add(this.quantidade1Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 155, -1, -1));
        
        this.quantidade1 = new javax.swing.JTextField();  
        this.quantidade1.setHorizontalAlignment(JTextField.CENTER);
        this.panel.add(this.quantidade1, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 125, 50, 30));
        
        
        
        this.quantidade2Label = new javax.swing.JLabel();
        this.quantidade2Label.setText("Qntd.");
        this.panel.add(this.quantidade2Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 155, -1, -1));

        this.quantidade2 = new javax.swing.JTextField();
        this.quantidade2.setHorizontalAlignment(JTextField.CENTER);
        this.panel.add(this.quantidade2, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 125, 50, 30));

        
        
        this.quantidadeResultLabel = new javax.swing.JLabel();
        this.quantidadeResultLabel.setText("Qntd.");
        this.panel.add(this.quantidadeResultLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 270, -1, -1));

        this.quantidadeResult = new javax.swing.JTextField();
        this.quantidadeResult.setText("1000");
        this.quantidadeResult.setHorizontalAlignment(JTextField.CENTER);
        this.quantidadeResult.setFont(new Font("Segoe UI", 1, 17));
        this.panel.add(this.quantidadeResult, new org.netbeans.lib.awtextra.AbsoluteConstraints(285, 235, 75, 35));
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

        this.panel.add(this.calcularMediaPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 190, 75, 35));
    }
    
    
    
    private boolean checkPrice(String price) {
        return price.matches("[Rr]?\\$?\\s*\\d+([\\,\\.]\\d{1,2})?");
    }
    
    private boolean checkAmount(String amount) {
        return amount.matches("\\d+");
    }
    
    
    private boolean validateInputs() {
        if (!validatePrices()) {
            return false;
        }
        
        if (!validateAmounts()) {
            return false;
        }
        
        return true;
    }
    
    
    private boolean validatePrices() {
        if (!checkPrice(this.preco1.getText())) {
            JOptionPane.showMessageDialog(new JFrame(), "Preço 1 Inválido!", "Erro!", JOptionPane.ERROR_MESSAGE);
            this.preco1.setText("");
            return false;
        }

        if (!checkPrice(this.preco2.getText())) {
            JOptionPane.showMessageDialog(new JFrame(), "Preço 2 Inválido!", "Erro!", JOptionPane.ERROR_MESSAGE);
            this.preco2.setText("");
            return false;
        }  
        
        return true;
    }
    
    
    private boolean validateAmounts() {
        if (!checkAmount(this.quantidade1.getText())) {
            JOptionPane.showMessageDialog(new JFrame(), "Quantidade 1 Inválida!", "Erro!", JOptionPane.ERROR_MESSAGE);
            this.quantidade1.setText("");
            return false;            
        }         
        
        if (!checkAmount(this.quantidade2.getText())) {
            JOptionPane.showMessageDialog(new JFrame(), "Quantidade 2 Inválida!", "Erro!", JOptionPane.ERROR_MESSAGE);
            this.quantidade2.setText("");
            return false;            
        }      
        
        if (!checkAmount(this.quantidadeResult.getText())) { 
            JOptionPane.showMessageDialog(new JFrame(), "Quantidade Desejada Inválida!", "Erro!", JOptionPane.ERROR_MESSAGE);
            this.quantidadeResult.setText("1000");
            return false;   
        }
        
        return true;
    }
}
