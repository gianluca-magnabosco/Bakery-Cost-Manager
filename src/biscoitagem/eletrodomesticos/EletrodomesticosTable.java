package biscoitagem.eletrodomesticos;

import biscoitagem.PageTable;
import biscoitagem.dao.ConnectionFactory;
import biscoitagem.dao.EletrodomesticoDAO;
import biscoitagem.dao.KWhDAO;
import biscoitagem.domain.Eletrodomestico;
import biscoitagem.domain.KWh;
import biscoitagem.exception.DAOException;
import static biscoitagem.utils.InputFormatHandler.getAmountFromTableRow;
import static biscoitagem.utils.InputFormatHandler.getPriceAsDouble;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class EletrodomesticosTable extends PageTable {
    
    private final JPanel panel;
    private EletrodomesticosTopPanel topPanel;
    private String[] columnNames = {"Nome", "Potência", "Preço/min"};
    
    EletrodomesticosTable(JPanel panel, EletrodomesticosTopPanel topPanel) {
        super(panel);
        this.panel = panel;
        this.topPanel = topPanel;
    }
    
    
    public void initComponents() {
        this.setTableColumnNames(this.columnNames);
    }
    
    
    public void showTableItems() {

        try (ConnectionFactory factory = new ConnectionFactory()) {
            PageTable.GUITableModel model = null;
            
            EletrodomesticoDAO dao = new EletrodomesticoDAO(factory.getConnection());
            List<Eletrodomestico> list = dao.search();
            
            if (list.isEmpty()) {
                model = new PageTable.GUITableModel(new Object [][] {{null, null, null}}, this.columnNames);
                this.initTableComponents(model); 
                return;
            }
            
            int i = -1;
            for (Eletrodomestico eletrodomestico : list) {
                i++;
                
                if (i == 0) {
                    model = new PageTable.GUITableModel(new Object [][] {{eletrodomestico.getNome(), eletrodomestico.getPotenciaAndMetrica(), eletrodomestico.getPrecoPorMinutoInReais()}}, this.columnNames);
                    continue;
                }
                
                model.addRow(new Object[] {eletrodomestico.getNome(), eletrodomestico.getPotenciaAndMetrica(), eletrodomestico.getPrecoPorMinutoInReais()});
            }
            
            this.initTableComponents(model);
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }          
    }
    
    
    public void insertEletrodomesticos() {
        
        this.topPanel.clearFields();
        
        if (!this.topPanel.askForEletrodomesticoInput()) {
            return;
        }
        
        try (ConnectionFactory factory = new ConnectionFactory()) {
            Eletrodomestico eletrodomestico = new Eletrodomestico();
            eletrodomestico.setNome(this.topPanel.getNomeTextFieldValue());
            eletrodomestico.setPotencia(this.topPanel.getPotenciaTextFieldText());
            eletrodomestico.setPrecoPorHora(this.getPrecoPorHora());
            
            EletrodomesticoDAO eletrodomesticoDAO = new EletrodomesticoDAO(factory.getConnection());
            eletrodomesticoDAO.insert(eletrodomestico);
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }  
        
        JOptionPane.showMessageDialog(new JFrame(), "Eletrodoméstico adicionado com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    
    public void updateEletrodomesticos() {
        if (!this.validateSelectedRows()) {
            return;
        }

        int[] selectedRows = this.getSelectedRows();
        if (selectedRows.length > 1) {
            JOptionPane.showMessageDialog(new JFrame(), "Selecione apenas um eletrodoméstico por vez!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String currentNome = this.getValueAt(selectedRows[0], 0).toString();
        this.topPanel.setNomeTextFieldText(currentNome);
        
        String currentPotencia = getAmountFromTableRow(this.getValueAt(selectedRows[0], 1).toString());
        this.topPanel.setPotenciaTextFieldText(currentPotencia + "W");
        
        String currentPreco = this.getValueAt(selectedRows[0], 2).toString();
        
        if (!this.topPanel.askForEletrodomesticoInput()) {
            return;
        }        
        
        try (ConnectionFactory factory = new ConnectionFactory()) {
            Eletrodomestico oldEletrodomestico = new Eletrodomestico();
            oldEletrodomestico.setNome(currentNome);
            oldEletrodomestico.setPotencia(Integer.parseInt(currentPotencia));
            oldEletrodomestico.setPrecoPorMinuto(currentPreco);
            
            Eletrodomestico newEletrodomestico = new Eletrodomestico();
            newEletrodomestico.setNome(this.topPanel.getNomeTextFieldValue());
            newEletrodomestico.setPotencia(this.topPanel.getPotenciaTextFieldText());
            newEletrodomestico.setPrecoPorHora(this.getPrecoPorHora());
            
            EletrodomesticoDAO eletrodomesticoDAO = new EletrodomesticoDAO(factory.getConnection());
            eletrodomesticoDAO.update(oldEletrodomestico, newEletrodomestico);
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }  
        
        JOptionPane.showMessageDialog(new JFrame(), "Eletrodoméstico alterado com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    public void deleteEletrodomesticos() {
        if (!this.validateSelectedRows()) {
            return;
        }
        
        int[] selectedRows = this.getSelectedRows();
        int result = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover " + selectedRows.length + " Eletrodoméstico(s)?", "Remover Eletrodoméstico", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }
        
        try (ConnectionFactory factory = new ConnectionFactory()) {
            for (int i : selectedRows) {
                Eletrodomestico eletrodomestico = new Eletrodomestico();
                eletrodomestico.setNome(this.getValueAt(i, 0).toString());
                eletrodomestico.setPotencia(Integer.parseInt(getAmountFromTableRow(this.getValueAt(i, 1).toString())));
                eletrodomestico.setPrecoPorMinuto(getPriceAsDouble(this.getValueAt(i, 2).toString()));
                
                EletrodomesticoDAO eletrodomesticoDAO = new EletrodomesticoDAO(factory.getConnection());
                eletrodomesticoDAO.delete(eletrodomestico);
            }
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }  
        
        JOptionPane.showMessageDialog(new JFrame(), "Eletrodoméstico(s) removido(s) com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);          
    }
    
    
    private double getPrecoPorHora() {
        try (ConnectionFactory factory = new ConnectionFactory()) {
            int potencia = this.topPanel.getPotenciaTextFieldText();
            double kw = (double) potencia / 1000;
            KWhDAO dao = new KWhDAO(factory.getConnection());
            List<KWh> list = dao.search();
            for (KWh kwh : list) {
                return kw * kwh.getKWhAsDouble();
            }
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }  
        
        return -1;
    }
    
    
    private boolean validateSelectedRows() {
        if (this.getRowCount() == 0 || this.getValueAt(0, 0) == null) {
            JOptionPane.showMessageDialog(new JFrame(), "Nenhum eletrodoméstico cadastrado!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (this.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(new JFrame(), "Nenhum eletrodoméstico selecionado!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }    

}
