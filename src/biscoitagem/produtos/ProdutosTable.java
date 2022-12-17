package biscoitagem.produtos;

import biscoitagem.PageTable;
import biscoitagem.TableComboModel;
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
import static biscoitagem.utils.InputFormatHandler.getAmountFromTableRow;
import static biscoitagem.utils.InputFormatHandler.getMetricaFromTableRow;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;


public class ProdutosTable extends PageTable {
    
    private final JPanel panel;
    private ProdutosTopPanel topPanel;
    private String[] columnNames = {"Nome", "Rendimento", "Horas Trabalhadas", "Complexidade", "Custo", "Receitas", "Insumos"};
    private List<TableCellEditor> receitaEditors;
    private List<TableCellEditor> insumoEditors;
    
    ProdutosTable(JPanel panel, ProdutosTopPanel topPanel) {
        super(panel);
        this.panel = panel;
        this.topPanel = topPanel;
    }
    
    
    public void initComponents() {
        this.setTableColumnNames(columnNames);
    }
    
    @Override
    public TableCellEditor getCellEditor(int row, int column) {
        int modelColumn = convertColumnIndexToModel(column);

        if (modelColumn == 5)
            return this.receitaEditors.get(row);
        else if (modelColumn == 6) 
            return this.insumoEditors.get(row);
        else
            return super.getCellEditor(row, column);
    }    
    
    private void initTableComponents(ProdutosTableModel model) {
        this.setModel(model);
        this.getTableHeader().setReorderingAllowed(false);
        this.tableScrollPane.setViewportView(this);
        if (this.getColumnModel().getColumnCount() > 0) {
            this.getColumnModel().getColumn(0).setPreferredWidth(120);
            this.getColumnModel().getColumn(1).setPreferredWidth(30);
            this.getColumnModel().getColumn(2).setPreferredWidth(35);
            this.getColumnModel().getColumn(3).setPreferredWidth(40);
            this.getColumnModel().getColumn(4).setPreferredWidth(50);
            this.getColumnModel().getColumn(5).setPreferredWidth(85);
            this.getColumnModel().getColumn(6).setPreferredWidth(85);
        }
        
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer centeredRenderer = new DefaultTableCellRenderer();
        centeredRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        this.getColumnModel().getColumn(1).setCellRenderer(centeredRenderer);
        this.getColumnModel().getColumn(2).setCellRenderer(centeredRenderer);
        this.getColumnModel().getColumn(3).setCellRenderer(centeredRenderer);
        this.getColumnModel().getColumn(4).setCellRenderer(centeredRenderer);
        this.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor( new JComboBox<ReceitaBSON>()));
        this.getColumnModel().getColumn(5).setCellRenderer(new ComboTableCellRenderer( new JComboBox<ReceitaBSON>()));
        this.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(new JComboBox<InsumoBSON>()));
        this.getColumnModel().getColumn(6).setCellRenderer(new ComboTableCellRenderer(new JComboBox<InsumoBSON>()));
        this.panel.add(this.tableScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 40, 584, 519));
    }  
    
    
    public void showTableItems() {

        try (MongoClientFactory factory = new MongoClientFactory()) {
            this.receitaEditors = new ArrayList<>();
            this.insumoEditors = new ArrayList<>();
            
            ProdutosTableModel model = null;
            
            ProdutoDAO dao = new ProdutoDAO(factory.getClient());
            List<Produto> list = dao.search();
            
            if (list.isEmpty()) {
                model = new ProdutosTableModel(new Object [][] {{null, null, null, null, null, null, null}}, this.columnNames);
                this.initTableComponents(model); 
                return;
            }
            
            JComboBox<ReceitaBSON> receitaComboBox = null;
            JComboBox<InsumoBSON> insumoComboBox = null;
            
            int i = -1;
            for (Produto produto : list) {
                i++;
                
                String nome = produto.getNome();
                String rendimento = Integer.toString(produto.getRendimento()) + produto.getMetrica();
                int horasTrabalhadas = produto.getHorasTrabalhadas();
                int complexidade = produto.getComplexidade();
                double custo = this.getCusto(produto);
                List<ReceitaBSON> receitas = produto.getReceitas();
                List<InsumoBSON> insumos = produto.getInsumos();
                receitas.add(0, new ReceitaBSON());
                insumos.add(0, new InsumoBSON());
                
                receitaComboBox = new JComboBox<ReceitaBSON> (new TableComboModel<ReceitaBSON>(receitas));
                this.receitaEditors.add(new DefaultCellEditor(receitaComboBox));
                
                insumoComboBox = new JComboBox<InsumoBSON> (new TableComboModel<InsumoBSON>(insumos));
                this.insumoEditors.add(new DefaultCellEditor(insumoComboBox));
                
                if (i == 0) {
                    model = new ProdutosTableModel(new Object [][] {{nome, rendimento, horasTrabalhadas, complexidade, formatToReais(custo), receitaComboBox, insumoComboBox}}, this.columnNames);
                    continue;
                }
                
                model.addRow(new Object[] {nome, rendimento, horasTrabalhadas, complexidade, formatToReais(custo), receitaComboBox, insumoComboBox});
            }
            
            this.initTableComponents(model);
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    
    private double getCusto(Produto produto) throws DAOException {
        double custo = 0;
        try (ConnectionFactory factory = new ConnectionFactory()) {
            
            try (MongoClientFactory mongoFactory = new MongoClientFactory()) {
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
        
        return (double) Math.round(custo * 100.0) / 100.0;
    }
    
    
    
    public void insertProdutos() {
        
        this.topPanel.clearFields();
        
        if (!this.topPanel.askForProdutoInput()) {
            return;
        }
        
        
        try (MongoClientFactory factory = new MongoClientFactory()) {
           
            Produto produto = new Produto();
            produto.setNome(this.topPanel.getNomeTextField());
            produto.setRendimento(this.topPanel.getRendimentoTextField());
            produto.setMetrica(this.topPanel.getComboBoxSelection());
            produto.setHorasTrabalhadas(this.topPanel.getHorasTrabalhadasTextField());
            produto.setComplexidade(this.topPanel.getComplexidadeTextField());
            produto.setReceitas(this.topPanel.getReceitas());
            produto.setInsumos(this.topPanel.getInsumos());

            ProdutoDAO dao = new ProdutoDAO(factory.getClient());
            dao.insert(produto);
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }  
        
        JOptionPane.showMessageDialog(new JFrame(), "Produto adicionado com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    public void updateProdutos() {
        if (!this.validateSelectedRows()) {
            return;
        }

        int[] selectedRows = this.getSelectedRows();
        if (selectedRows.length > 1) {
            JOptionPane.showMessageDialog(new JFrame(), "Selecione apenas um produto por vez!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        
        Produto produto = new Produto();
        produto.setNome(this.getValueAt(selectedRows[0], 0).toString());
        produto.setRendimento(Integer.parseInt(getAmountFromTableRow(this.getValueAt(selectedRows[0], 1).toString())));
        produto.setMetrica(getMetricaFromTableRow(this.getValueAt(selectedRows[0], 1).toString()));
        
        Produto oldProduto = new Produto();
        try (MongoClientFactory factory = new MongoClientFactory()) {
            ProdutoDAO dao = new ProdutoDAO(factory.getClient());
            oldProduto = dao.searchOne(produto);
            this.topPanel.setNomeTextField(oldProduto.getNome());
            this.topPanel.setRendimentoTextField(Integer.toString(oldProduto.getRendimento()));
            this.topPanel.setComboBoxSelection(oldProduto.getMetrica());
            this.topPanel.setHorasTrabalhadasTextField(oldProduto.getHorasTrabalhadas());
            this.topPanel.setComplexidadeTextField(oldProduto.getComplexidade());
            this.topPanel.updateTopPanel(oldProduto.getReceitas(), oldProduto.getInsumos());
        } 
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }  
        
        
        if (!this.topPanel.askForProdutoInput()) {
            return;
        }        
        
        try (MongoClientFactory factory = new MongoClientFactory()) {
            Produto newProduto = new Produto();
            newProduto.setNome(this.topPanel.getNomeTextField());
            newProduto.setRendimento(this.topPanel.getRendimentoTextField());
            newProduto.setMetrica(this.topPanel.getComboBoxSelection());
            newProduto.setHorasTrabalhadas(this.topPanel.getHorasTrabalhadasTextField());
            newProduto.setComplexidade(this.topPanel.getComplexidadeTextField());
            
            newProduto.setInsumos(this.topPanel.getInsumos());
            newProduto.setReceitas(this.topPanel.getReceitas());

            ProdutoDAO dao = new ProdutoDAO(factory.getClient());
            dao.update(oldProduto, newProduto);
        } 
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }  

        JOptionPane.showMessageDialog(new JFrame(), "Produto alterado com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    public void deleteProdutos() {
        if (!this.validateSelectedRows()) {
            return;
        }
        
        int[] selectedRows = this.getSelectedRows();
        int result = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover " + selectedRows.length + " produto(s)?", "Remover Produto", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }
        
        try (MongoClientFactory factory = new MongoClientFactory()) {
            for (int i : selectedRows) {
                Produto produto = new Produto();
                produto.setNome(this.getValueAt(i, 0).toString());
                produto.setRendimento(Integer.parseInt(getAmountFromTableRow(this.getValueAt(i, 1).toString())));
                produto.setMetrica(getMetricaFromTableRow(this.getValueAt(i, 1).toString()));
                
                ProdutoDAO produtoDAO = new ProdutoDAO(factory.getClient());
                produtoDAO.delete(produto);
            }
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }  
        
        JOptionPane.showMessageDialog(new JFrame(), "Produto(s) removido(s) com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);          
    }
    
    
    
    private boolean validateSelectedRows() {
        if (this.getRowCount() == 0 || this.getValueAt(0, 0) == null) {
            JOptionPane.showMessageDialog(new JFrame(), "Nenhum produto cadastrado!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (this.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(new JFrame(), "Nenhum produto selecionado!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }    
    
    
    public class ProdutosTableModel extends DefaultTableModel {
        Class[] types = new Class [] {
            java.lang.String.class, java.lang.String.class, java.lang.Object.class
        };

        boolean[] canEdit = new boolean [] {
            false, false, false, false, false, true, true
        };

        @Override
        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }


        ProdutosTableModel(Object[][] data, String[] columnNames) {
            super(data, columnNames);
        }
    }

      
    class ComboTableCellRenderer implements TableCellRenderer {
        JComboBox combo;
        
        public ComboTableCellRenderer(JComboBox comboBox) {
            this.combo = new JComboBox();
            for (int i = 0; i < comboBox.getItemCount(); i++){
                this.combo.addItem(comboBox.getItemAt(i));
            }
        }
        
        public Component getTableCellRendererComponent(JTable jtable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            this.combo.setSelectedItem(value);
            return this.combo;
        }
    }
}
