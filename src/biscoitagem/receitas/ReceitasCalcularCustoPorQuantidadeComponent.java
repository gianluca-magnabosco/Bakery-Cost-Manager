package biscoitagem.receitas;

import biscoitagem.dao.ConnectionFactory;
import biscoitagem.dao.EletrodomesticoDAO;
import biscoitagem.dao.InsumoDAO;
import biscoitagem.dao.MongoClientFactory;
import biscoitagem.dao.ReceitaDAO;
import biscoitagem.domain.Eletrodomestico;
import biscoitagem.domain.EletrodomesticoBSON;
import biscoitagem.domain.Insumo;
import biscoitagem.domain.InsumoBSON;
import biscoitagem.domain.Receita;
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


public class ReceitasCalcularCustoPorQuantidadeComponent {
    private JComboBox receitasComboBox;
    private JLabel calcularCustoLabel;
    private JLabel selecionarReceitaLabel;
    private JLabel selecionarQuantidadeLabel;
    private JTextField quantidadeTextField;
    private JPanel calcularCustoPanel;
    private JLabel calcularCustoPanelLabel;
    private JTextField calculationResultTextField;
    private JLabel calculationResultLabel;
    
    private JPanel panel;
    
    
    ReceitasCalcularCustoPorQuantidadeComponent(JPanel panel) {
        this.panel = panel;
    }
    
    
    public JPanel getButtonPanel() {
        return this.calcularCustoPanel;
    }
    
    public JLabel getButtonLabel() {
        return this.calcularCustoPanelLabel;
    }    
    
    public void showCustoPorQuantidade() {
        this.receitasComboBox.removeAllItems();
        this.quantidadeTextField.setText("0");
        this.calculationResultTextField.setText("");
        
        try (MongoClientFactory factory = new MongoClientFactory()) {
            ReceitaDAO dao = new ReceitaDAO(factory.getClient());
            List<Receita> list = dao.search();
            
            if (list.isEmpty()) {
                return;
            }
            
            for (Receita receita : list) {
                this.receitasComboBox.addItem(receita.getNome());
            }
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }  
    }    
    
    
    public void calcularCustoPorQuantidade() throws DAOException {
        if (!this.quantidadeTextField.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(new JFrame(), "Valor inválido!", "Erro!", JOptionPane.ERROR_MESSAGE);
            this.quantidadeTextField.setText("0");
            this.calculationResultTextField.setText("");
            return;
        }
        
        Object nome = this.receitasComboBox.getSelectedItem();
        if (nome == null) {
            JOptionPane.showMessageDialog(new JFrame(), "Nenhuma receita cadastrada!", "Erro!", JOptionPane.ERROR_MESSAGE);
            this.quantidadeTextField.setText("0");
            this.calculationResultTextField.setText("");
            return;
        }
        
        try (MongoClientFactory mongoFactory = new MongoClientFactory()) {
            int quantidade = Integer.parseInt(this.quantidadeTextField.getText().replaceAll("\\s", ""));
            ReceitaDAO dao = new ReceitaDAO(mongoFactory.getClient());
            
            Receita receita = dao.searchByName(nome.toString());
            if (receita == null) {
                this.calculationResultTextField.setText("R$ 0,00");
                return;
            }
            
            double custo = 0;
            try (ConnectionFactory factory = new ConnectionFactory()) {
                InsumoDAO insumoDao = new InsumoDAO(factory.getConnection());
                List<Insumo> insumos = insumoDao.search();
                List<InsumoBSON> insumosBson = receita.getInsumos();
                for (Insumo insumo : insumos) {
                    for (InsumoBSON insumoBson : insumosBson) {
                        if (insumo.getNome().equals(insumoBson.getNome())) {
                            custo += (insumo.getPreco() / insumo.getQuantidade()) * insumoBson.getQuantidade();
                        }
                    }
                }         

                EletrodomesticoDAO eletrodomesticoDao = new EletrodomesticoDAO(factory.getConnection());
                List<Eletrodomestico> eletrodomesticos = eletrodomesticoDao.search();
                List<EletrodomesticoBSON> eletrodomesticosBson = receita.getEletrodomesticos();
                for (Eletrodomestico eletrodomestico : eletrodomesticos) {
                    for (EletrodomesticoBSON eletrodomesticoBson : eletrodomesticosBson) { 
                        if (eletrodomestico.getNome().equals(eletrodomesticoBson.getNome())) {
                            custo += eletrodomestico.getPrecoPorMinuto() * eletrodomesticoBson.getTempo();
                        }
                    }
                }
            } catch (Exception e) {
                throw new DAOException("Erro ao calcular custo da receita", e);
            }       
            
            double result = (double) Math.round(((custo / receita.getRendimento()) * quantidade) * 100.0) / 100.0;
            this.calculationResultTextField.setText(formatToReais(result));
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
    public void initComponents() {
        this.selecionarReceitaLabel = new JLabel();
        this.selecionarReceitaLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
        this.selecionarReceitaLabel.setText("Receita:");
        this.panel.add(this.selecionarReceitaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 227, 200, 50));
        
        
        this.receitasComboBox = new JComboBox();
        this.panel.add(this.receitasComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 260, 240, 40));
                
        
        this.calcularCustoLabel = new JLabel();
        this.calcularCustoLabel.setFont(new java.awt.Font("Segoe UI", 1, 16));
        this.calcularCustoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/icons8-receipt-50.png")));
        this.calcularCustoLabel.setText("   Calcular Custo por Quantidade");
        this.panel.add(this.calcularCustoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 195, 300, 50));
        
        
        this.selecionarQuantidadeLabel = new JLabel();
        this.selecionarQuantidadeLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
        this.selecionarQuantidadeLabel.setText("Quantidade:");
        this.panel.add(this.selecionarQuantidadeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(358, 227, 200, 50));
        
        
        this.quantidadeTextField = new JTextField();
        this.quantidadeTextField.setFont(new Font("Serif", Font.BOLD, 21));
        this.quantidadeTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        this.quantidadeTextField.addActionListener((ActionEvent e) -> {
            try {
                this.calcularCustoPorQuantidade();
            } catch (DAOException ex) {
                System.out.println("### ERRO DE DAO: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        this.panel.add(this.quantidadeTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(341, 260, 100, 40));
        
        
        this.calcularCustoPanel = new JPanel();
        this.calcularCustoPanel.setBackground(new java.awt.Color(57, 180, 74));
        this.calcularCustoPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(57, 170, 73), 5, true));
        this.calcularCustoPanel.setPreferredSize(new java.awt.Dimension(90, 35));

        this.panel.add(this.calcularCustoPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 315, 76, 35));
        
        
        this.calcularCustoPanelLabel = new JLabel();
        this.calcularCustoPanelLabel.setBackground(new java.awt.Color(255, 255, 255));
        this.calcularCustoPanelLabel.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        this.calcularCustoPanelLabel.setForeground(new java.awt.Color(255, 255, 255));
        this.calcularCustoPanelLabel.setText("Calcular");
        
        javax.swing.GroupLayout calcularTempoPanelLayout = new javax.swing.GroupLayout(this.calcularCustoPanel);
        this.calcularCustoPanel.setLayout(calcularTempoPanelLayout);
        calcularTempoPanelLayout.setHorizontalGroup(
            calcularTempoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(this.calcularCustoPanelLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );
        calcularTempoPanelLayout.setVerticalGroup(
            calcularTempoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(this.calcularCustoPanelLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );
        
        this.calculationResultLabel = new JLabel();
        this.calculationResultLabel.setFont(new java.awt.Font("Segoe UI", 1, 14));
        this.calculationResultLabel.setText("Resultado:");
        this.panel.add(this.calculationResultLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(266, 313, 76, 35));
        
        
        
        this.calculationResultTextField = new JTextField();
        this.calculationResultTextField.setEditable(false);
        this.calculationResultTextField.setFont(new java.awt.Font("Segoe UI", 1, 18));
        this.panel.add(this.calculationResultTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(341, 312, 100, 40));      
    }    
    
}
