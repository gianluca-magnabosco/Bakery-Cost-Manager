package biscoitagem.eletrodomesticos;

import biscoitagem.dao.ConnectionFactory;
import biscoitagem.dao.EletrodomesticoDAO;
import biscoitagem.domain.Eletrodomestico;
import biscoitagem.exception.DAOException;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class EletrodomesticosCalcularCustoTempoComponent {
    private JComboBox eletrodomesticosComboBox;
    private JLabel calcularTempoLabel;
    private JLabel selecionarEletrodomesticoLabel;
    private JLabel selecionarTempoLabel;
    private JTextField tempoTextField;
    private JPanel calcularTempoPanel;
    private JLabel calcularTempoPanelLabel;
    private JTextField calculationResultTextField;
    private JLabel calculationResultLabel;
    
    private JPanel panel;
    
    
    EletrodomesticosCalcularCustoTempoComponent(JPanel panel) {
        this.panel = panel;
    }
    
    
    public JPanel getButtonPanel() {
        return this.calcularTempoPanel;
    }
    
    public JLabel getButtonLabel() {
        return this.calcularTempoPanelLabel;
    }
    
    public void showPrecoPorTempo() {
        this.eletrodomesticosComboBox.removeAllItems();
        this.tempoTextField.setText("0 min");
        this.calculationResultTextField.setText("");
        
        try (ConnectionFactory factory = new ConnectionFactory()) {
            EletrodomesticoDAO dao = new EletrodomesticoDAO(factory.getConnection());
            List<Eletrodomestico> list = dao.search();
            
            if (list.isEmpty()) {
                return;
            }
            
            for (Eletrodomestico eletrodomestico : list) {
                this.eletrodomesticosComboBox.addItem(eletrodomestico.getNome());
            }
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }  
    }    
    
    
    public void calcularPrecoPorTempo() throws DAOException {
        if (!this.tempoTextField.getText().matches("\\d+\\s*[mM]?[iI]?[nN]?")) {
            JOptionPane.showMessageDialog(new JFrame(), "Valor inválido!", "Erro!", JOptionPane.ERROR_MESSAGE);
            this.tempoTextField.setText("0 min");
            this.calculationResultTextField.setText("");
            return;
        }
        
        Object nome = this.eletrodomesticosComboBox.getSelectedItem();
        if (nome == null) {
            JOptionPane.showMessageDialog(new JFrame(), "Nenhum eletrodoméstico cadastrado!", "Erro!", JOptionPane.ERROR_MESSAGE);
            this.tempoTextField.setText("0 min");
            this.calculationResultTextField.setText("");
            return;
        }
        
        try (ConnectionFactory factory = new ConnectionFactory()) {
            int tempo = Integer.parseInt(this.tempoTextField.getText().replaceAll("\\s", "").replaceAll("[minMIN]", ""));
            EletrodomesticoDAO dao = new EletrodomesticoDAO(factory.getConnection());
            
            Eletrodomestico eletrodomestico = dao.searchByName(nome.toString());
            if (eletrodomestico == null) {
                this.calculationResultTextField.setText("R$ 0,00");
                return;
            }
            
            this.calculationResultTextField.setText(eletrodomestico.getPrecoPorMinutoInReais(tempo));
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
    public void initComponents() {
        this.selecionarEletrodomesticoLabel = new JLabel();
        this.selecionarEletrodomesticoLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
        this.selecionarEletrodomesticoLabel.setText("Eletrodoméstico:");
        this.panel.add(this.selecionarEletrodomesticoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 287, 200, 50));
        
        
        this.eletrodomesticosComboBox = new JComboBox();
        this.panel.add(this.eletrodomesticosComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 320, 250, 40));
                
        
        this.calcularTempoLabel = new JLabel();
        this.calcularTempoLabel.setFont(new java.awt.Font("Segoe UI", 1, 16));
        this.calcularTempoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/icons8-time-is-money-50.png")));
        this.calcularTempoLabel.setText("   Calcular Custo por Tempo");
        this.panel.add(this.calcularTempoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 245, 300, 50));
        
        
        this.selecionarTempoLabel = new JLabel();
        this.selecionarTempoLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
        this.selecionarTempoLabel.setText("Tempo:");
        this.panel.add(this.selecionarTempoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(353, 287, 200, 50));
        
        
        this.tempoTextField = new JTextField();
        this.tempoTextField.setFont(new Font("Serif", Font.BOLD, 21));
        this.tempoTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        this.tempoTextField.addActionListener((ActionEvent e) -> {
            try {
                this.calcularPrecoPorTempo();
            } catch (DAOException ex) {
                Logger.getLogger(EletrodomesticosCalcularCustoTempoComponent.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.panel.add(this.tempoTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 320, 86, 40));
        
        
        this.calcularTempoPanel = new JPanel();
        this.calcularTempoPanel.setBackground(new java.awt.Color(57, 180, 74));
        this.calcularTempoPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(57, 170, 73), 5, true));
        this.calcularTempoPanel.setPreferredSize(new java.awt.Dimension(90, 35));

        this.panel.add(this.calcularTempoPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 375, 76, 35));
        
        
        this.calcularTempoPanelLabel = new JLabel();
        this.calcularTempoPanelLabel.setBackground(new java.awt.Color(255, 255, 255));
        this.calcularTempoPanelLabel.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        this.calcularTempoPanelLabel.setForeground(new java.awt.Color(255, 255, 255));
        this.calcularTempoPanelLabel.setText("Calcular");
        
        javax.swing.GroupLayout calcularTempoPanelLayout = new javax.swing.GroupLayout(this.calcularTempoPanel);
        this.calcularTempoPanel.setLayout(calcularTempoPanelLayout);
        calcularTempoPanelLayout.setHorizontalGroup(
            calcularTempoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(this.calcularTempoPanelLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );
        calcularTempoPanelLayout.setVerticalGroup(
            calcularTempoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(this.calcularTempoPanelLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );
        
        this.calculationResultLabel = new JLabel();
        this.calculationResultLabel.setFont(new java.awt.Font("Segoe UI", 1, 14));
        this.calculationResultLabel.setText("Resultado:");
        this.panel.add(this.calculationResultLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(279, 373, 76, 35));
        
        
        
        this.calculationResultTextField = new JTextField();
        this.calculationResultTextField.setEditable(false);
        this.calculationResultTextField.setFont(new java.awt.Font("Segoe UI", 1, 18));
        this.panel.add(this.calculationResultTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(354, 372, 80, 40));      
    }

}
