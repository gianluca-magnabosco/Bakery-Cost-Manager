package biscoitagem.produtos;

import biscoitagem.dao.ConnectionFactory;
import biscoitagem.dao.EletrodomesticoDAO;
import biscoitagem.dao.HorasTrabalhadasDAO;
import biscoitagem.dao.InsumoDAO;
import biscoitagem.dao.MongoClientFactory;
import biscoitagem.dao.ProdutoDAO;
import biscoitagem.dao.ReceitaDAO;
import biscoitagem.domain.Eletrodomestico;
import biscoitagem.domain.EletrodomesticoBSON;
import biscoitagem.domain.HorasTrabalhadas;
import biscoitagem.domain.Insumo;
import biscoitagem.domain.InsumoBSON;
import biscoitagem.domain.Produto;
import biscoitagem.domain.Receita;
import biscoitagem.domain.ReceitaBSON;
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


public class ProdutosCalcularCustoPorQuantidadeComponent {
    private JComboBox produtosComboBox;
    private JLabel calcularCustoLabel;
    private JLabel selecionarProdutoLabel;
    private JLabel selecionarQuantidadeLabel;
    private JTextField quantidadeTextField;
    private JPanel calcularCustoPanel;
    private JLabel calcularCustoPanelLabel;
    private JTextField calculationResultTextField;
    private JLabel calculationResultLabel;
    
    private JPanel panel;
    
    
    ProdutosCalcularCustoPorQuantidadeComponent(JPanel panel) {
        this.panel = panel;
    }
    
    
    public JPanel getButtonPanel() {
        return this.calcularCustoPanel;
    }
    
    public JLabel getButtonLabel() {
        return this.calcularCustoPanelLabel;
    }    
    
    public void showCustoPorQuantidade() {
        this.produtosComboBox.removeAllItems();
        this.quantidadeTextField.setText("0");
        this.calculationResultTextField.setText("");
        
        try (MongoClientFactory factory = new MongoClientFactory()) {
            ProdutoDAO dao = new ProdutoDAO(factory.getClient());
            List<Produto> list = dao.search();
            
            if (list.isEmpty()) {
                return;
            }
            
            for (Produto produto : list) {
                this.produtosComboBox.addItem(produto.getNome());
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
        
        Object nome = this.produtosComboBox.getSelectedItem();
        if (nome == null) {
            JOptionPane.showMessageDialog(new JFrame(), "Nenhum produto cadastrado!", "Erro!", JOptionPane.ERROR_MESSAGE);
            this.quantidadeTextField.setText("0");
            this.calculationResultTextField.setText("");
            return;
        }
        
        try (MongoClientFactory mongoFactory = new MongoClientFactory()) {
            int quantidade = Integer.parseInt(this.quantidadeTextField.getText().replaceAll("\\s", ""));
            ProdutoDAO dao = new ProdutoDAO(mongoFactory.getClient());
            
            Produto produto = dao.searchByName(nome.toString());
            if (produto == null) {
                this.calculationResultTextField.setText("R$ 0,00");
                return;
            }


            double custo = 0;
            try (ConnectionFactory factory = new ConnectionFactory()) {
                ReceitaDAO receitaDao = new ReceitaDAO(mongoFactory.getClient());
                List<Receita> receitas = receitaDao.search();
                List<ReceitaBSON> receitasBson = produto.getReceitas();
                for (Receita receita : receitas) {
                    for (ReceitaBSON receitaBson : receitasBson) { 
                        if (receita.getNome().equals(receitaBson.getNome())) {
                            double custoReceita = 0.0;
                            
                            InsumoDAO insumoDao = new InsumoDAO(factory.getConnection());
                            List<Insumo> insumos = insumoDao.search();
                            List<InsumoBSON> insumosBson = receita.getInsumos();
                            for (Insumo insumo : insumos) {
                                for (InsumoBSON insumoBson : insumosBson) {
                                    if (insumo.getNome().equals(insumoBson.getNome())) {
                                        custoReceita += (insumo.getPreco() / insumo.getQuantidade()) * insumoBson.getQuantidade();
                                    }
                                }
                            }         

                            EletrodomesticoDAO eletrodomesticoDao = new EletrodomesticoDAO(factory.getConnection());
                            List<Eletrodomestico> eletrodomesticos = eletrodomesticoDao.search();
                            List<EletrodomesticoBSON> eletrodomesticosBson = receita.getEletrodomesticos();
                            for (Eletrodomestico eletrodomestico : eletrodomesticos) {
                                for (EletrodomesticoBSON eletrodomesticoBson : eletrodomesticosBson) { 
                                    if (eletrodomestico.getNome().equals(eletrodomesticoBson.getNome())) {
                                        custoReceita += eletrodomestico.getPrecoPorMinuto() * eletrodomesticoBson.getTempo();
                                    }
                                }
                            }
                            
                            custo += (custoReceita / receita.getRendimento()) * receitaBson.getQuantidade();
                        }
                    }
                }
                InsumoDAO insumoDao = new InsumoDAO(factory.getConnection());
                List<Insumo> insumos = insumoDao.search();
                List<InsumoBSON> insumosBson = produto.getInsumos();
                for (Insumo insumo : insumos) {
                    for (InsumoBSON insumoBson : insumosBson) {
                        if (insumo.getNome().equals(insumoBson.getNome())) {
                            custo += (insumo.getPreco() / insumo.getQuantidade()) * insumoBson.getQuantidade();
                        }
                    }
                }         
                double precoHoraTrabalhada = 0.0;
                HorasTrabalhadasDAO horasTrabalhadasDao = new HorasTrabalhadasDAO(factory.getConnection());
                List<HorasTrabalhadas> list = horasTrabalhadasDao.search();
                if (!list.isEmpty()) {
                    precoHoraTrabalhada = list.get(0).getValueAsDouble();
                }

                double custoHorasTrabalhadas = produto.getHorasTrabalhadas() * precoHoraTrabalhada;
                int complexidade = produto.getComplexidade();

                custo += custoHorasTrabalhadas + (custoHorasTrabalhadas * (complexidade - 1) * complexidade * 0.005);

            } catch (Exception e) {
                throw new DAOException("Erro ao calcular custo do produto", e);
            }            
            

            double result = (double) Math.round(((custo / produto.getRendimento()) * quantidade) * 100.0) / 100.0;
            this.calculationResultTextField.setText(formatToReais(result));
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
    public void initComponents() {
        this.selecionarProdutoLabel = new JLabel();
        this.selecionarProdutoLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
        this.selecionarProdutoLabel.setText("Produto:");
        this.panel.add(this.selecionarProdutoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 222, 200, 50));
        
        
        this.produtosComboBox = new JComboBox();
        this.panel.add(this.produtosComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 256, 240, 40));
                
        
        this.calcularCustoLabel = new JLabel();
        this.calcularCustoLabel.setFont(new java.awt.Font("Segoe UI", 1, 16));
        this.calcularCustoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/icons8-cost-per-mille-64.png")));
        this.calcularCustoLabel.setText("   Calcular Custo por Quantidade");
        this.panel.add(this.calcularCustoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 340, 64));
        
        
        this.selecionarQuantidadeLabel = new JLabel();
        this.selecionarQuantidadeLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
        this.selecionarQuantidadeLabel.setText("Quantidade:");
        this.panel.add(this.selecionarQuantidadeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(363, 222, 200, 50));
        
        
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
        this.panel.add(this.quantidadeTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(346, 255, 100, 40));
        
        
        this.calcularCustoPanel = new JPanel();
        this.calcularCustoPanel.setBackground(new java.awt.Color(57, 180, 74));
        this.calcularCustoPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(57, 170, 73), 5, true));
        this.calcularCustoPanel.setPreferredSize(new java.awt.Dimension(90, 35));

        this.panel.add(this.calcularCustoPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 310, 76, 35));
        
        
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
        this.panel.add(this.calculationResultLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(271, 308, 76, 35));
        
        
        
        this.calculationResultTextField = new JTextField();
        this.calculationResultTextField.setEditable(false);
        this.calculationResultTextField.setFont(new java.awt.Font("Segoe UI", 1, 18));
        this.panel.add(this.calculationResultTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(346, 307, 100, 40));      
    }    
    
}
