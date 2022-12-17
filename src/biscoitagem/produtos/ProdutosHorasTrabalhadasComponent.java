package biscoitagem.produtos;

import biscoitagem.dao.ConnectionFactory;
import biscoitagem.dao.HorasTrabalhadasDAO;
import biscoitagem.domain.HorasTrabalhadas;
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


public class ProdutosHorasTrabalhadasComponent {
    private JLabel horasTrabalhadasLabel;
    private JPanel horasTrabalhadasPanel;
    private JLabel horasTrabalhadasPanelLabel;
    private JTextField horasTrabalhadasTextField;
    private JPanel panel;

    
    ProdutosHorasTrabalhadasComponent(JPanel panel) {
        this.panel = panel;
    }
    
    public String getTextFieldText() {
        return this.horasTrabalhadasTextField.getText();
    }
    
    public JPanel getButtonPanel() {
        return this.horasTrabalhadasPanel;
    }
    
    public JLabel getButtonLabel() {
        return this.horasTrabalhadasPanelLabel;
    }
    
    
    public void atualizarValorHorasTrabalhadas() {
        try (ConnectionFactory factory = new ConnectionFactory()) {
            HorasTrabalhadas oldHorasTrabalhadas = new HorasTrabalhadas();
            oldHorasTrabalhadas.setValueInReais(this.horasTrabalhadasTextField.getText());

            HorasTrabalhadas newHorasTrabalhadas = new HorasTrabalhadas();
            
            HorasTrabalhadasDAO dao = new HorasTrabalhadasDAO(factory.getConnection());
            dao.update(oldHorasTrabalhadas, newHorasTrabalhadas);
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }  
        
        JOptionPane.showMessageDialog(new JFrame(), "Valor da Hora Trabalhada alterado com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    public void showValorHorasTrabalhadas() {
        try (ConnectionFactory factory = new ConnectionFactory()) {
            HorasTrabalhadasDAO dao = new HorasTrabalhadasDAO(factory.getConnection());
            
            List<HorasTrabalhadas> list = dao.search();
            
            if (list.isEmpty()) {
                HorasTrabalhadas horasTrabalhadas = new HorasTrabalhadas(0.0);
                dao.insert(horasTrabalhadas);
                this.horasTrabalhadasTextField.setText(horasTrabalhadas.getValueInReais());
                return;
            }
            
            for (HorasTrabalhadas horasTrabalhadas : list) {
                this.horasTrabalhadasTextField.setText(horasTrabalhadas.getValueInReais());
            }
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }     
    }   

    
    public void initComponents() {
        this.horasTrabalhadasLabel = new javax.swing.JLabel();
        this.horasTrabalhadasLabel.setFont(new java.awt.Font("Segoe UI", 1, 17)); 
        this.horasTrabalhadasLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/icons8-time-is-money-56.png")));
        this.horasTrabalhadasLabel.setText("Valor da Hora Trabalhada");
        this.panel.add(this.horasTrabalhadasLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 35, 400, 60));
        
        
        this.horasTrabalhadasTextField = new javax.swing.JTextField();
        this.horasTrabalhadasTextField.setFont(new Font("Serif", Font.BOLD, 22));
        this.horasTrabalhadasTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        this.horasTrabalhadasTextField.addActionListener((ActionEvent e) -> {
            this.atualizarValorHorasTrabalhadas();
        });
        this.panel.add(this.horasTrabalhadasTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 85, 97, 40));
        
        this.initButtonComponents();
    }
    
    
    private void initButtonComponents() {
        this.horasTrabalhadasPanel = new javax.swing.JPanel();
        this.horasTrabalhadasPanel.setBackground(new java.awt.Color(57, 180, 74));
        this.horasTrabalhadasPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(57, 170, 73), 5, true));
       
        javax.swing.GroupLayout kwhPanelLayout = new javax.swing.GroupLayout(this.horasTrabalhadasPanel);
        this.horasTrabalhadasPanel.setLayout(kwhPanelLayout);
        
        this.panel.add(this.horasTrabalhadasPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(275, 87, 84, 37));        

        this.horasTrabalhadasPanelLabel = new javax.swing.JLabel();
        this.horasTrabalhadasPanelLabel.setBackground(new java.awt.Color(255, 255, 255));
        this.horasTrabalhadasPanelLabel.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        this.horasTrabalhadasPanelLabel.setForeground(new java.awt.Color(255, 255, 255));
        this.horasTrabalhadasPanelLabel.setText(" Atualizar");
        kwhPanelLayout.setHorizontalGroup(
            kwhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kwhPanelLayout.createSequentialGroup()
                .addComponent(this.horasTrabalhadasPanelLabel)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        kwhPanelLayout.setVerticalGroup(
            kwhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(this.horasTrabalhadasPanelLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }
        
}
