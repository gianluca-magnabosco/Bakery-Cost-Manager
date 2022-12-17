package biscoitagem.receitas;

import biscoitagem.PageTable;
import biscoitagem.TableComboModel;
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


public class ReceitasTable extends PageTable {
    
    private final JPanel panel;
    private ReceitasTopPanel topPanel;
    private String[] columnNames = {"Nome", "Rendimento", "Custo", "Insumos", "Eletrodomésticos"};
    private List<TableCellEditor> insumoEditors;
    private List<TableCellEditor> eletrodomesticoEditors;
    
    ReceitasTable(JPanel panel, ReceitasTopPanel topPanel) {
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

        if (modelColumn == 3)
            return this.insumoEditors.get(row);
        else if (modelColumn == 4) 
            return this.eletrodomesticoEditors.get(row);
        else
            return super.getCellEditor(row, column);
    }    
    
    private void initTableComponents(ReceitasTableModel model) {
        this.setModel(model);
        this.getTableHeader().setReorderingAllowed(false);
        this.tableScrollPane.setViewportView(this);
        if (this.getColumnModel().getColumnCount() > 0) {
            this.getColumnModel().getColumn(0).setPreferredWidth(120);
            this.getColumnModel().getColumn(1).setPreferredWidth(30);
            this.getColumnModel().getColumn(2).setPreferredWidth(30);
            this.getColumnModel().getColumn(3).setPreferredWidth(70);
            this.getColumnModel().getColumn(4).setPreferredWidth(70);
        }
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer centeredRenderer = new DefaultTableCellRenderer();
        centeredRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        this.getColumnModel().getColumn(1).setCellRenderer(centeredRenderer);
        this.getColumnModel().getColumn(2).setCellRenderer(centeredRenderer);
        this.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(new JComboBox<InsumoBSON>()));
        this.getColumnModel().getColumn(3).setCellRenderer(new ComboTableCellRenderer(new JComboBox<InsumoBSON>()));
        this.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor( new JComboBox<EletrodomesticoBSON>()));
        this.getColumnModel().getColumn(4).setCellRenderer(new ComboTableCellRenderer( new JComboBox<EletrodomesticoBSON>()));
        this.panel.add(this.tableScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 40, 584, 519));
    }  
    
    
    public void showTableItems() {

        try (MongoClientFactory factory = new MongoClientFactory()) {
            this.insumoEditors = new ArrayList<>();
            this.eletrodomesticoEditors = new ArrayList<>();
            
            ReceitasTableModel model = null;
            
            ReceitaDAO dao = new ReceitaDAO(factory.getClient());
            List<Receita> list = dao.search();
            
            if (list.isEmpty()) {
                model = new ReceitasTableModel(new Object [][] {{null, null, null, null, null}}, this.columnNames);
                this.initTableComponents(model); 
                return;
            }
            
            JComboBox<InsumoBSON> insumoComboBox = null;
            JComboBox<EletrodomesticoBSON> eletrodomesticoComboBox = null;
            
            int i = -1;
            for (Receita receita : list) {
                i++;
                
                String nome = receita.getNome();
                String rendimento = Integer.toString(receita.getRendimento()) + receita.getMetrica();
                double custo = this.getCusto(receita);
                List<InsumoBSON> insumos = receita.getInsumos();
                List<EletrodomesticoBSON> eletrodomesticos = receita.getEletrodomesticos();
                insumos.add(0, new InsumoBSON());
                eletrodomesticos.add(0, new EletrodomesticoBSON());
                
                insumoComboBox = new JComboBox<InsumoBSON> (new TableComboModel<InsumoBSON>(insumos));
                this.insumoEditors.add(new DefaultCellEditor(insumoComboBox));
                
                eletrodomesticoComboBox = new JComboBox<EletrodomesticoBSON> (new TableComboModel<EletrodomesticoBSON>(eletrodomesticos));
                this.eletrodomesticoEditors.add(new DefaultCellEditor(eletrodomesticoComboBox));
                
                if (i == 0) {
                    model = new ReceitasTableModel(new Object [][] {{nome, rendimento, formatToReais(custo), insumoComboBox, eletrodomesticoComboBox}}, this.columnNames);
                    continue;
                }
                
                model.addRow(new Object[] {nome, rendimento, formatToReais(custo), insumoComboBox, eletrodomesticoComboBox});
            }
            
            this.initTableComponents(model);
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    
    private double getCusto(Receita receita) throws DAOException {
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
        
        return (double) Math.round(custo * 100.0) / 100.0;
    }
    
    
    
    public void insertReceitas() {
        
        this.topPanel.clearFields();
        
        if (!this.topPanel.askForReceitaInput()) {
            return;
        }
        
        
        try (MongoClientFactory factory = new MongoClientFactory()) {
           
            Receita receita = new Receita();
            receita.setNome(this.topPanel.getNomeTextField());
            receita.setMetrica(this.topPanel.getComboBoxSelection());
            receita.setRendimento(this.topPanel.getRendimentoTextField());
            
            receita.setEletrodomesticos(this.topPanel.getEletrodomesticos());
            receita.setInsumos(this.topPanel.getInsumos());

            ReceitaDAO dao = new ReceitaDAO(factory.getClient());
            dao.insert(receita);
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }  
        
        JOptionPane.showMessageDialog(new JFrame(), "Receita adicionada com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    public void updateReceitas() {
        if (!this.validateSelectedRows()) {
            return;
        }

        int[] selectedRows = this.getSelectedRows();
        if (selectedRows.length > 1) {
            JOptionPane.showMessageDialog(new JFrame(), "Selecione apenas uma receita por vez!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        
        Receita receita = new Receita();
        receita.setNome(this.getValueAt(selectedRows[0], 0).toString());
        receita.setRendimento(Integer.parseInt(getAmountFromTableRow(this.getValueAt(selectedRows[0], 1).toString())));
        receita.setMetrica(getMetricaFromTableRow(this.getValueAt(selectedRows[0], 1).toString()));
        
        Receita oldReceita = new Receita();
        try (MongoClientFactory factory = new MongoClientFactory()) {
            ReceitaDAO dao = new ReceitaDAO(factory.getClient());
            oldReceita = dao.searchOne(receita);
            this.topPanel.setNomeTextField(oldReceita.getNome());
            this.topPanel.setRendimentoTextField(Integer.toString(oldReceita.getRendimento()));
            this.topPanel.setComboBoxSelection(oldReceita.getMetrica());
            this.topPanel.updateTopPanel(oldReceita.getInsumos(), oldReceita.getEletrodomesticos());
        } 
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }  
        
        
        if (!this.topPanel.askForReceitaInput()) {
            return;
        }        
        
        try (MongoClientFactory factory = new MongoClientFactory()) {
            Receita newReceita = new Receita();
            newReceita.setNome(this.topPanel.getNomeTextField());
            newReceita.setMetrica(this.topPanel.getComboBoxSelection());
            newReceita.setRendimento(this.topPanel.getRendimentoTextField());
            
            newReceita.setEletrodomesticos(this.topPanel.getEletrodomesticos());
            newReceita.setInsumos(this.topPanel.getInsumos());

            ReceitaDAO dao = new ReceitaDAO(factory.getClient());
            dao.update(oldReceita, newReceita);
        } 
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }  

        JOptionPane.showMessageDialog(new JFrame(), "Receita alterada com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    public void deleteReceitas() {
        if (!this.validateSelectedRows()) {
            return;
        }
        
        int[] selectedRows = this.getSelectedRows();
        int result = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover " + selectedRows.length + " receita(s)?", "Remover Receita", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }
        
        try (MongoClientFactory factory = new MongoClientFactory()) {
            for (int i : selectedRows) {
                Receita receita = new Receita();
                receita.setNome(this.getValueAt(i, 0).toString());
                receita.setRendimento(Integer.parseInt(getAmountFromTableRow(this.getValueAt(i, 1).toString())));
                receita.setMetrica(getMetricaFromTableRow(this.getValueAt(i, 1).toString()));
                
                ReceitaDAO receitaDAO = new ReceitaDAO(factory.getClient());
                receitaDAO.delete(receita);
            }
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }  
        
        JOptionPane.showMessageDialog(new JFrame(), "Receita(s) removida(s) com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);          
    }
    
    
    
    private boolean validateSelectedRows() {
        if (this.getRowCount() == 0 || this.getValueAt(0, 0) == null) {
            JOptionPane.showMessageDialog(new JFrame(), "Nenhuma receita cadastrada!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (this.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(new JFrame(), "Nenhuma receita selecionada!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }    
    
    
    public class ReceitasTableModel extends DefaultTableModel {
        Class[] types = new Class [] {
            java.lang.String.class, java.lang.String.class, java.lang.Object.class
        };

        boolean[] canEdit = new boolean [] {
            false, false, false, true, true
        };

        @Override
        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }


        ReceitasTableModel(Object[][] data, String[] columnNames) {
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
