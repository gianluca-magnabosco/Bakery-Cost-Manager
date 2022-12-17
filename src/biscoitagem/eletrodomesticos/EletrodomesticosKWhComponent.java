package biscoitagem.eletrodomesticos;

import biscoitagem.dao.ConnectionFactory;
import biscoitagem.dao.KWhDAO;
import biscoitagem.domain.KWh;
import biscoitagem.exception.DAOException;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class EletrodomesticosKWhComponent {
    private JLabel kwhLabel;
    private JPanel kwhPanel;
    private JLabel kwhPanelLabel;
    private JTextField kwhTextField;
    private JPanel panel;

    
    EletrodomesticosKWhComponent(JPanel panel) {
        this.panel = panel;
    }
    
    public String getTextFieldText() {
        return this.kwhTextField.getText();
    }
    
    public JPanel getButtonPanel() {
        return this.kwhPanel;
    }
    
    public JLabel getButtonLabel() {
        return this.kwhPanelLabel;
    }
    
    
    public void atualizarKWh() {
        try (ConnectionFactory factory = new ConnectionFactory()) {
            KWh oldKwh = new KWh();
            oldKwh.setKWh(this.kwhTextField.getText());

            KWh newKwh = new KWh();
            
            KWhDAO dao = new KWhDAO(factory.getConnection());
            dao.update(oldKwh, newKwh);
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }  
        
        JOptionPane.showMessageDialog(new JFrame(), "KWh alterado com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    public void showKWh() {
        try (ConnectionFactory factory = new ConnectionFactory()) {
            KWhDAO dao = new KWhDAO(factory.getConnection());
            
            List<KWh> list = dao.search();
            
            if (list.isEmpty()) {
                KWh kwh = new KWh(0.0);
                dao.insert(kwh);
                this.kwhTextField.setText(kwh.getKWhInReais());
                return;
            }
            
            for (KWh kwh : list) {
                this.kwhTextField.setText(kwh.getKWhInReais());
            }
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }     
    }   

    
    public void initComponents() {
        this.kwhLabel = new javax.swing.JLabel();
        this.kwhLabel.setFont(new java.awt.Font("Segoe UI", 1, 17)); 
        this.kwhLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/icons8-electricity-56.png")));
        this.kwhLabel.setText("KWh");
        this.panel.add(this.kwhLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(88, 90, 120, 60));
        
        
        this.kwhTextField = new javax.swing.JTextField();
        this.kwhTextField.setFont(new Font("Serif", Font.BOLD, 22));
        this.kwhTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        this.kwhTextField.addActionListener((ActionEvent e) -> {
            this.atualizarKWh();
        });
        this.panel.add(this.kwhTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 100, 90, 40));
        
        this.initButtonComponents();
    }
    
    
    private void initButtonComponents() {
        this.kwhPanel = new javax.swing.JPanel();
        this.kwhPanel.setBackground(new java.awt.Color(57, 180, 74));
        this.kwhPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(57, 170, 73), 5, true));
       
        javax.swing.GroupLayout kwhPanelLayout = new javax.swing.GroupLayout(this.kwhPanel);
        this.kwhPanel.setLayout(kwhPanelLayout);
        
        this.panel.add(this.kwhPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(295, 102, 84, 37));        

        this.kwhPanelLabel = new javax.swing.JLabel();
        this.kwhPanelLabel.setBackground(new java.awt.Color(255, 255, 255));
        this.kwhPanelLabel.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        this.kwhPanelLabel.setForeground(new java.awt.Color(255, 255, 255));
        this.kwhPanelLabel.setText(" Atualizar");
        kwhPanelLayout.setHorizontalGroup(
            kwhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kwhPanelLayout.createSequentialGroup()
                .addComponent(this.kwhPanelLabel)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        kwhPanelLayout.setVerticalGroup(
            kwhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(this.kwhPanelLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }
    
}