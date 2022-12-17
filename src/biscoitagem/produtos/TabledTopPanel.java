package biscoitagem.produtos;

import biscoitagem.domain.Insumo;
import biscoitagem.domain.InsumoBSON;
import biscoitagem.domain.Receita;
import biscoitagem.domain.ReceitaBSON;
import static biscoitagem.utils.InputFormatHandler.getAmountFromTableRow;
import static biscoitagem.utils.InputFormatHandler.getMetricaFromTableRow;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class TabledTopPanel extends JPanel {
    
    private String title;
    
    private JTable table = new JTable();
    private javax.swing.JScrollPane tableScrollPane;
    private String[] columnNames;
    private JPanel addingPanel;
    private JLabel addingLabel;
    private JPanel removingPanel;
    private JLabel removingLabel;
    
    private JPanel fullPanel;
    private JPanel groupingPanel;
    
    private List<Receita> currentReceitas = new ArrayList<>();
    private List<ReceitaBSON> productReceitas = new ArrayList<>();
    private List<Insumo> currentInsumos = new ArrayList<>();
    private List<InsumoBSON> productInsumos = new ArrayList<>();
    
    private TopPanel topPanel;
    
    
    public TabledTopPanel(String title) {
        super();
        this.title = title;
        this.initComponents();
        this.addMouseListeners();
        this.topPanel = new TopPanel(title);
    }
    
    
    
    public List<ReceitaBSON> getProductReceitas() {
        return this.productReceitas;
    }
    
    public void setProductReceitas(List<ReceitaBSON> receitas) {
        this.productReceitas = receitas;
    }
    
    public List<Receita> getCurrentReceitas() {
        return this.currentReceitas;
    }
    
    public List<InsumoBSON> getProductInsumos() {
        return this.productInsumos;
    }
    
    public void setProductInsumos(List<InsumoBSON> insumos) {
        this.productInsumos = insumos;
    }
    
    public List<Insumo> getCurrentInsumos() {
        return this.currentInsumos;
    }
    
    public TopPanel getTopPanel() {
        return this.topPanel;
    }
    
    public void adjustReceitaTopPanelComboBox(List<ReceitaBSON> receitas) {
        this.topPanel.removeFromReceitaComboBox(receitas);
    }
    
    public void adjustInsumoTopPanelComboBox(List<InsumoBSON> insumos) {
        this.topPanel.removeFromInsumoComboBox(insumos);
    }
    
    
    public void clearFields() {
        this.productReceitas = new ArrayList<>();
        this.currentReceitas = new ArrayList<>();
        this.productInsumos = new ArrayList<>();
        this.currentInsumos = new ArrayList<>();
        
        this.topPanel.resetComboBoxes();
        
        this.showTableItems();
    }
    
    
    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }
    
    private void initComponents() {
        this.fullPanel = new JPanel();
        this.fullPanel.setLayout(new BoxLayout(this.fullPanel, BoxLayout.Y_AXIS));
        this.tableScrollPane = new javax.swing.JScrollPane(); 
        this.fullPanel.add(this.tableScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 40, 584, 519));   
        this.initAddingButton();
        this.initRemovingButton();
        this.groupingPanel = new JPanel();
        this.groupingPanel.add(this.addingPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 580, 84, 35));   
        this.groupingPanel.add(Box.createHorizontalStrut(200));
        this.groupingPanel.add(this.removingPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 580, -1, -1));      
        this.fullPanel.add(this.groupingPanel);
        this.add(this.fullPanel);
    }

    
    public void showTableItems() {

        AlternativeTableModel model = null;
        
        if (this.title.equals("Receitas")) {
            if (this.productReceitas.isEmpty()) {
                model = new AlternativeTableModel(new Object [][] {{null, null}}, this.columnNames);
                this.initTableComponents(model); 
                return;
            }

            int i = -1;
            for (ReceitaBSON receita : this.productReceitas) {
                i++;

                String nome = receita.getNome();
                String quantidade = Integer.toString(receita.getQuantidade()) + receita.getMetrica();

                if (i == 0) {
                    model = new AlternativeTableModel(new Object [][] {{nome, quantidade}}, this.columnNames);
                    continue;
                }

                model.addRow(new Object[] {nome, quantidade});
            }            
            
        } else {
            
            if (this.productInsumos.isEmpty()) {
                model = new AlternativeTableModel(new Object [][] {{null, null}}, this.columnNames);
                this.initTableComponents(model); 
                return;
            }

            int i = -1;
            for (InsumoBSON insumo : this.productInsumos) {
                i++;

                String nome = insumo.getNome();
                String quantidade = Integer.toString(insumo.getQuantidade()) + insumo.getMetrica();

                if (i == 0) {
                    model = new AlternativeTableModel(new Object [][] {{nome, quantidade}}, this.columnNames);
                    continue;
                }

                model.addRow(new Object[] {nome, quantidade});
            }

        }

        this.initTableComponents(model);
    }


    private void addMouseListeners() {
        this.addingLabel.addMouseListener(new TabledTopPanelMouseListener(this.addingPanel, "Add"));
        this.removingLabel.addMouseListener(new TabledTopPanelMouseListener(this.removingPanel, "Remove"));
    }

    
    public void insert() {
        
        if (!this.topPanel.askForInput()) {
            return;
        }
        
        if (this.title.equals("Receitas")) {
            this.currentReceitas = this.topPanel.getReceitaList();
            ReceitaBSON receitaBson = new ReceitaBSON();
            receitaBson.setNome(this.topPanel.getCheckBoxSelection());
            receitaBson.setQuantidade(this.topPanel.getQuantidadeTextField());

            for (Receita receita : this.currentReceitas) {
                if (receita.getNome().equals(receitaBson.getNome())) {
                    receitaBson.setMetrica(receita.getMetrica());
                }
            }
            
            this.productReceitas.add(receitaBson);
        } else {
            this.currentInsumos = this.topPanel.getInsumoList();
            InsumoBSON insumoBson = new InsumoBSON();
            insumoBson.setNome(this.topPanel.getCheckBoxSelection());
            insumoBson.setQuantidade(this.topPanel.getQuantidadeTextField());
            
            for (Insumo insumo : this.currentInsumos) {
                if (insumo.getNome().equals(insumoBson.getNome())) {
                    insumoBson.setMetrica(insumo.getMetrica());
                }
            }            
            
            this.productInsumos.add(insumoBson);
        }
        
        
        this.topPanel.removeCheckBoxProductByName();
        this.showTableItems();
    }    
    
    
    public void delete() {
        if (!this.validateSelectedRows()) {
            return;
        }
        
        int[] selectedRows = this.table.getSelectedRows();
        int result = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover " + selectedRows.length + " " + this.title.toLowerCase().substring(0, this.title.length() - 1) + "(s)?", "Remover " + this.title, JOptionPane.OK_CANCEL_OPTION);
        
        if (result != JOptionPane.OK_OPTION) {
            return;
        }
        
        if (this.title.equals("Receitas")) {
            for (int i : selectedRows) {
                Receita receita = new Receita();
                receita.setNome(this.table.getValueAt(i, 0).toString());

                ReceitaBSON receitaBson = new ReceitaBSON();
                receitaBson.setNome(this.table.getValueAt(i, 0).toString());
                receitaBson.setQuantidade(Integer.parseInt(getAmountFromTableRow(this.table.getValueAt(i, 1).toString())));

                int j = 0;
                for (ReceitaBSON recipe : this.productReceitas) {
                    if (recipe.getNome().equals(receitaBson.getNome())) {
                        this.productReceitas.remove(j);
                        break;
                    }
                    j++;
                }
                this.topPanel.getReceitaNameList().add(receita.getNome());
                this.topPanel.getReceitasComboBox().setModel(new DefaultComboBoxModel(this.topPanel.getReceitaNameList().toArray()));
                this.currentReceitas.add(receita);
            }

            JOptionPane.showMessageDialog(new JFrame(), "Receita(s) removida(s) com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE); 
            this.showTableItems();
            return;
        }
        
        
        for (int i : selectedRows) {
            Insumo insumo = new Insumo();
            insumo.setNome(this.table.getValueAt(i, 0).toString());
            insumo.setQuantidade(Integer.parseInt(getAmountFromTableRow(this.table.getValueAt(i, 1).toString())));
            insumo.setMetrica(getMetricaFromTableRow(this.table.getValueAt(i, 1).toString()));

            InsumoBSON insumoBson = new InsumoBSON();
            insumoBson.setNome(this.table.getValueAt(i, 0).toString());
            insumoBson.setQuantidade(Integer.parseInt(getAmountFromTableRow(this.table.getValueAt(i, 1).toString())));
            insumoBson.setMetrica(getMetricaFromTableRow(this.table.getValueAt(i, 1).toString()));

            int j = 0;
            for (InsumoBSON ins : this.productInsumos) {
                if (ins.getNome().equals(insumoBson.getNome())) {
                    this.productInsumos.remove(j);
                    break;
                }
                j++;
            }
            this.topPanel.getInsumoNameList().add(insumo.getNome());
            this.topPanel.getInsumosComboBox().setModel(new DefaultComboBoxModel(this.topPanel.getInsumoNameList().toArray()));
            this.currentInsumos.add(insumo);
        }

        JOptionPane.showMessageDialog(new JFrame(), "Insumo(s) removido(s) com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE); 
        this.showTableItems();
    }
    
    
    public boolean askForInput() {
        this.showTableItems();
        ImageIcon icon = null;
        if (this.title.equals("Receitas")) {
            icon = new ImageIcon(getClass().getResource("/images/icons/icons8-recipe-100.png"));
        } else {
            icon = new ImageIcon(getClass().getResource("/images/icons/icons8-ingredients-for-cooking-100.png"));
        }
        
        
        int result = JOptionPane.showConfirmDialog(null, this, "Inserir " + this.title, JOptionPane.OK_CANCEL_OPTION, 1, icon);
        
        if (result != JOptionPane.OK_OPTION) {
            return false;
        } 

        return true;
    }    
    
    
    protected void initTableComponents(AlternativeTableModel model) {

        this.table.setModel(model);
        this.table.getTableHeader().setReorderingAllowed(false);
        this.tableScrollPane.setViewportView(this.table);
        if (this.table.getColumnModel().getColumnCount() > 0) {
            this.table.getColumnModel().getColumn(0).setPreferredWidth(250);
            this.table.getColumnModel().getColumn(1).setPreferredWidth(35);
        }
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) this.table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer centeredRenderer = new DefaultTableCellRenderer();
        centeredRenderer.setHorizontalAlignment(SwingConstants.CENTER); 
    }
    

    private boolean validateSelectedRows() {
        if (this.table.getRowCount() == 0 || this.table.getValueAt(0, 0) == null) {
            JOptionPane.showMessageDialog(new JFrame(), "Nenhum item cadastrado!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (this.table.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(new JFrame(), "Nenhum item selecionado!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }      
    
    
    private void initAddingButton() {
        this.addingPanel = new javax.swing.JPanel();
        this.addingLabel = new javax.swing.JLabel();
        
        this.addingPanel.setBackground(new java.awt.Color(57, 180, 74));
        this.addingPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(57, 170, 73), 5, true));
        this.addingPanel.setPreferredSize(new java.awt.Dimension(90, 35));

        this.addingLabel.setBackground(new java.awt.Color(255, 255, 255));
        this.addingLabel.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        this.addingLabel.setForeground(new java.awt.Color(255, 255, 255));
        this.addingLabel.setText("Adicionar");

        javax.swing.GroupLayout addingPanelLayout = new javax.swing.GroupLayout(this.addingPanel);
        this.addingPanel.setLayout(addingPanelLayout);
        addingPanelLayout.setHorizontalGroup(
            addingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(this.addingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );
        addingPanelLayout.setVerticalGroup(
            addingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(this.addingLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );
 
    }
    
    private void initRemovingButton() {
        this.removingPanel = new javax.swing.JPanel();
        this.removingLabel = new javax.swing.JLabel();
        
        this.removingPanel.setBackground(new java.awt.Color(220, 53, 69));
        this.removingPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(220, 43, 69), 5, true));
        this.removingPanel.setPreferredSize(new java.awt.Dimension(86, 35));

        this.removingLabel.setBackground(new java.awt.Color(255, 255, 255));
        this.removingLabel.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        this.removingLabel.setForeground(new java.awt.Color(255, 255, 255));
        this.removingLabel.setText(" Remover");
        this.removingLabel.setPreferredSize(new java.awt.Dimension(74, 21));

        javax.swing.GroupLayout removingPanelLayout = new javax.swing.GroupLayout(this.removingPanel);
        this.removingPanel.setLayout(removingPanelLayout);
        removingPanelLayout.setHorizontalGroup(
            removingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(this.removingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
        );
        removingPanelLayout.setVerticalGroup(
            removingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, removingPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(this.removingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
      
    }
    
    
    public class TabledTopPanelMouseListener extends java.awt.event.MouseAdapter {
        JPanel panel;
        String text;
        Color currentBackgroundColor;
        Border currentBorder;
        
        
        TabledTopPanelMouseListener(JPanel panel, String text) {
            this.panel = panel;
            this.text = text;
            
            this.currentBorder = panel.getBorder();
            this.currentBackgroundColor = panel.getBackground();
        }
        
        @Override
        public void mouseEntered(MouseEvent event) {
            this.panel.setBackground(new Color(42, 98, 135));
            this.panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(42, 88, 135), 5, true));
        }
        
        @Override
        public void mouseExited(MouseEvent event) {  
            this.panel.setBackground(this.currentBackgroundColor);
            this.panel.setBorder(this.currentBorder);
        }   
        
        @Override
        public void mouseClicked(MouseEvent event) {   
            switch (this.text) {
                case "Add" -> {
                    insert();
                }
                
                case "Remove" -> {
                    delete();
                }

                default -> {
                }
            }
        } 
    }


    public class AlternativeTableModel extends DefaultTableModel {
        Class[] types = new Class [] {
            java.lang.String.class, java.lang.String.class, java.lang.Object.class
        };

        boolean[] canEdit = new boolean [] {
            false, false, false, false
        };

        @Override
        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }


        AlternativeTableModel(Object[][] data, String[] columnNames) {
            super(data, columnNames);
        }
    }
}
