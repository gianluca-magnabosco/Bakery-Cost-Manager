package biscoitagem.insumos;

import biscoitagem.dao.ConnectionFactory;
import biscoitagem.dao.InsumoDAO;
import biscoitagem.domain.Insumo;
import biscoitagem.exception.DAOException;
import static biscoitagem.utils.InputFormatHandler.formatToReais;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class InsumosCalcularPrecoPorQuantidadeComponent {
    private JComboBox insumosComboBox;
    private JLabel calcularPrecoLabel;
    private JLabel selecionarInsumoLabel;
    private JLabel selecionarQuantidadeLabel;
    private JTextField quantidadeTextField;
    private JPanel calcularPrecoPanel;
    private JLabel calcularPrecoPanelLabel;
    private JTextField calculationResultTextField;
    private JLabel calculationResultLabel;
    
    private JPanel panel;
    
    
    InsumosCalcularPrecoPorQuantidadeComponent(JPanel panel) {
        this.panel = panel;
    }
    
    
    public JPanel getButtonPanel() {
        return this.calcularPrecoPanel;
    }
    
    public JLabel getButtonLabel() {
        return this.calcularPrecoPanelLabel;
    }    
    
    public void showPrecoPorQuantidade() {
        this.insumosComboBox.removeAllItems();
        this.quantidadeTextField.setText("0");
        this.calculationResultTextField.setText("");
        
        try (ConnectionFactory factory = new ConnectionFactory()) {
            InsumoDAO dao = new InsumoDAO(factory.getConnection());
            List<Insumo> list = dao.search();
            
            if (list.isEmpty()) {
                return;
            }
            
            for (Insumo insumo : list) {
                this.insumosComboBox.addItem(insumo.getNome());
            }
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }  
    }    
    
    
    public void calcularPrecoPorQuantidade() throws DAOException {
        if (!this.quantidadeTextField.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(new JFrame(), "Valor inválido!", "Erro!", JOptionPane.ERROR_MESSAGE);
            this.quantidadeTextField.setText("0");
            this.calculationResultTextField.setText("");
            return;
        }
        
        Object nome = this.insumosComboBox.getSelectedItem();
        if (nome == null) {
            JOptionPane.showMessageDialog(new JFrame(), "Nenhum insumo cadastrado!", "Erro!", JOptionPane.ERROR_MESSAGE);
            this.quantidadeTextField.setText("0");
            this.calculationResultTextField.setText("");
            return;
        }
        
        try (ConnectionFactory factory = new ConnectionFactory()) {
            int quantidade = Integer.parseInt(this.quantidadeTextField.getText().replaceAll("\\s", ""));
            InsumoDAO dao = new InsumoDAO(factory.getConnection());
            
            Insumo insumo = dao.searchByName(nome.toString());
            if (insumo == null) {
                this.calculationResultTextField.setText("R$ 0,00");
                return;
            }
            
            double result = (double) Math.round(((insumo.getPreco() / insumo.getQuantidade()) * quantidade) * 100.0) / 100.0;
            this.calculationResultTextField.setText(formatToReais(result));
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
    public void initComponents() {
        this.selecionarInsumoLabel = new JLabel();
        this.selecionarInsumoLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
        this.selecionarInsumoLabel.setText("Insumo:");
        this.panel.add(this.selecionarInsumoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 367, 200, 50));
        
        
        this.insumosComboBox = new JComboBox();
        this.panel.add(this.insumosComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 400, 240, 40));
                
        
        this.calcularPrecoLabel = new JLabel();
        this.calcularPrecoLabel.setFont(new java.awt.Font("Segoe UI", 1, 16));
        this.calcularPrecoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/icons8-estimate-50.png")));
        this.calcularPrecoLabel.setText("   Calcular Preço por Quantidade");
        this.panel.add(this.calcularPrecoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 335, 300, 50));
        
        
        this.selecionarQuantidadeLabel = new JLabel();
        this.selecionarQuantidadeLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
        this.selecionarQuantidadeLabel.setText("Quantidade:");
        this.panel.add(this.selecionarQuantidadeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(358, 367, 200, 50));
        
        
        this.quantidadeTextField = new JTextField();
        this.quantidadeTextField.setFont(new Font("Serif", Font.BOLD, 21));
        this.quantidadeTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        this.quantidadeTextField.addActionListener((ActionEvent e) -> {
            try {
                this.calcularPrecoPorQuantidade();
            } catch (DAOException ex) {
                System.out.println("### ERRO DE DAO: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        this.panel.add(this.quantidadeTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(341, 400, 100, 40));
        
        
        this.calcularPrecoPanel = new JPanel();
        this.calcularPrecoPanel.setBackground(new java.awt.Color(57, 180, 74));
        this.calcularPrecoPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(57, 170, 73), 5, true));
        this.calcularPrecoPanel.setPreferredSize(new java.awt.Dimension(90, 35));

        this.panel.add(this.calcularPrecoPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 455, 76, 35));
        
        
        this.calcularPrecoPanelLabel = new JLabel();
        this.calcularPrecoPanelLabel.setBackground(new java.awt.Color(255, 255, 255));
        this.calcularPrecoPanelLabel.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        this.calcularPrecoPanelLabel.setForeground(new java.awt.Color(255, 255, 255));
        this.calcularPrecoPanelLabel.setText("Calcular");
        
        javax.swing.GroupLayout calcularTempoPanelLayout = new javax.swing.GroupLayout(this.calcularPrecoPanel);
        this.calcularPrecoPanel.setLayout(calcularTempoPanelLayout);
        calcularTempoPanelLayout.setHorizontalGroup(
            calcularTempoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(this.calcularPrecoPanelLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );
        calcularTempoPanelLayout.setVerticalGroup(
            calcularTempoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(this.calcularPrecoPanelLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );
        
        this.calculationResultLabel = new JLabel();
        this.calculationResultLabel.setFont(new java.awt.Font("Segoe UI", 1, 14));
        this.calculationResultLabel.setText("Resultado:");
        this.panel.add(this.calculationResultLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(266, 453, 76, 35));
        
        
        
        this.calculationResultTextField = new JTextField();
        this.calculationResultTextField.setEditable(false);
        this.calculationResultTextField.setFont(new java.awt.Font("Segoe UI", 1, 18));
        this.panel.add(this.calculationResultTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(341, 452, 100, 40));      
    }    
    
}
