package biscoitagem.insumos;

import biscoitagem.PageTable;
import biscoitagem.dao.ConnectionFactory;
import biscoitagem.dao.InsumoDAO;
import biscoitagem.domain.Insumo;
import biscoitagem.exception.DAOException;
import static biscoitagem.utils.InputFormatHandler.getAmountFromTableRow;
import static biscoitagem.utils.InputFormatHandler.getMetricaFromTableRow;
import static biscoitagem.utils.InputFormatHandler.getPriceAsDouble;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class InsumosTable extends PageTable {
    
    private final JPanel panel;
    private InsumosTopPanel topPanel;
    private String[] columnNames = {"Nome", "Quantidade", "Preço"};
    
    InsumosTable(JPanel panel, InsumosTopPanel topPanel) {
        super(panel);
        this.panel = panel;
        this.topPanel = topPanel;
    }
    
    
    public void initComponents() {
        this.setTableColumnNames(columnNames);
    }
    
    
    public void showTableItems() {

        try (ConnectionFactory factory = new ConnectionFactory()) {
            GUITableModel model = null;
            
            InsumoDAO dao = new InsumoDAO(factory.getConnection());
            List<Insumo> list = dao.search();
            
            if (list.isEmpty()) {
                model = new GUITableModel(new Object [][] {{null, null, null}}, this.columnNames);
                this.initTableComponents(model); 
                return;
            }
            
            int i = -1;
            for (Insumo insumo : list) {
                i++;
                
                if (i == 0) {
                    model = new GUITableModel(new Object [][] {{insumo.getNome(), insumo.getQuantidadeAndMetrica(), insumo.getPrecoInReais()}}, this.columnNames);
                    continue;
                }
                
                model.addRow(new Object[] {insumo.getNome(), insumo.getQuantidadeAndMetrica(), insumo.getPrecoInReais()});
            }
            
            this.initTableComponents(model);
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }          
    }
    
    
    public void insertInsumos() {
        
        this.topPanel.clearFields();
        
        if (!this.topPanel.askForInsumoInput()) {
            return;
        }
        
        try (ConnectionFactory factory = new ConnectionFactory()) {
            Insumo insumo = new Insumo();
            insumo.setNome(this.topPanel.getNomeTextFieldValue());
            insumo.setQuantidade(this.topPanel.getQuantidadeTextFieldValue());
            insumo.setMetrica(this.topPanel.getComboBoxSelection());
            insumo.setPreco(this.topPanel.getPrecoTextFieldValue());
            
            InsumoDAO insumoDAO = new InsumoDAO(factory.getConnection());
            insumoDAO.insert(insumo);
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }  
        
        JOptionPane.showMessageDialog(new JFrame(), "Insumo adicionado com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    public void updateInsumos() {
        if (!this.validateSelectedRows()) {
            return;
        }

        int[] selectedRows = this.getSelectedRows();
        if (selectedRows.length > 1) {
            JOptionPane.showMessageDialog(new JFrame(), "Selecione apenas um insumo por vez!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String currentNome = this.getValueAt(selectedRows[0], 0).toString();
        this.topPanel.setNomeTextFieldText(currentNome);
        
        String currentQuantidade = getAmountFromTableRow(this.getValueAt(selectedRows[0], 1).toString());
        this.topPanel.setQuantidadeTextFieldText(currentQuantidade);
        
        String currentMetrica = getMetricaFromTableRow(this.getValueAt(selectedRows[0], 1).toString());
        this.topPanel.setComboBoxSelection(currentMetrica);
        
        String currentPreco = this.getValueAt(selectedRows[0], 2).toString();
        this.topPanel.setPrecoTextFieldText(currentPreco);        
        
        
        
        if (!this.topPanel.askForInsumoInput()) {
            return;
        }        
        
        try (ConnectionFactory factory = new ConnectionFactory()) {
            Insumo oldInsumo = new Insumo();
            oldInsumo.setNome(currentNome);
            oldInsumo.setQuantidade(currentQuantidade);
            oldInsumo.setMetrica(currentMetrica);
            oldInsumo.setPreco(currentPreco);
            
            Insumo newInsumo = new Insumo();
            newInsumo.setNome(this.topPanel.getNomeTextFieldValue());
            newInsumo.setQuantidade(this.topPanel.getQuantidadeTextFieldValue());
            newInsumo.setMetrica(this.topPanel.getComboBoxSelection());
            newInsumo.setPreco(this.topPanel.getPrecoTextFieldValue());
            
            InsumoDAO insumoDAO = new InsumoDAO(factory.getConnection());
            insumoDAO.update(oldInsumo, newInsumo);
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }  
        
        JOptionPane.showMessageDialog(new JFrame(), "Insumo alterado com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    public void deleteInsumos() {
        if (!this.validateSelectedRows()) {
            return;
        }
        
        int[] selectedRows = this.getSelectedRows();
        int result = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover " + selectedRows.length + " insumo(s)?", "Remover Insumo", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }
        
        try (ConnectionFactory factory = new ConnectionFactory()) {
            for (int i : selectedRows) {
                Insumo insumo = new Insumo();
                insumo.setNome(this.getValueAt(i, 0).toString());
                insumo.setQuantidade(getAmountFromTableRow(this.getValueAt(i, 1).toString()));
                insumo.setMetrica(getMetricaFromTableRow(this.getValueAt(i, 1).toString()));
                insumo.setPreco(getPriceAsDouble(this.getValueAt(i, 2).toString()));
                
                InsumoDAO insumoDAO = new InsumoDAO(factory.getConnection());
                insumoDAO.delete(insumo);
            }
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }  
        
        JOptionPane.showMessageDialog(new JFrame(), "Insumo(s) removido(s) com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);          
    }
    
    
    
    private boolean validateSelectedRows() {
        if (this.getRowCount() == 0 || this.getValueAt(0, 0) == null) {
            JOptionPane.showMessageDialog(new JFrame(), "Nenhum insumo cadastrado!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (this.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(new JFrame(), "Nenhum insumo selecionado!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }    

}
