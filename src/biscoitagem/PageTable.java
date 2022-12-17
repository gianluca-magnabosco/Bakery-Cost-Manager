package biscoitagem;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class PageTable extends javax.swing.JTable {

    public final javax.swing.JScrollPane tableScrollPane;
    private JLabel addingLabel;
    private JPanel addingPanel;
    private JLabel alteringLabel;
    private JPanel alteringPanel;
    private JLabel removingLabel;
    private JPanel removingPanel;
    private final JPanel panel;
    private String[] columnNames;
    
    
    public PageTable(JPanel panel) {
        super();
        this.tableScrollPane = new javax.swing.JScrollPane(); 
        this.panel = panel;
        this.initButtons();
    }
    
    public JLabel getAddingLabel() {
        return this.addingLabel;
    }
    
    public JPanel getAddingPanel() {
        return this.addingPanel;
    }
    
    
    public JLabel getAlteringLabel() {
        return this.alteringLabel;
    }
    
    public JPanel getAlteringPanel() {
        return this.alteringPanel;
    }

    public JLabel getRemovingLabel() {
        return this.removingLabel;
    }
    
    public JPanel getRemovingPanel() {
        return this.removingPanel;
    }    
    
    public void setTableColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }
    
    
    private void initButtons() {
        this.initAddingButton();
        this.initAlteringButton();
        this.initRemovingButton();
    }        
    
    
    
    protected void initTableComponents(GUITableModel model) {

        this.setModel(model);
        this.getTableHeader().setReorderingAllowed(false);
        this.tableScrollPane.setViewportView(this);
        if (this.getColumnModel().getColumnCount() > 0) {
            this.getColumnModel().getColumn(0).setPreferredWidth(250);
            this.getColumnModel().getColumn(1).setPreferredWidth(35);
            this.getColumnModel().getColumn(2).setPreferredWidth(30);
        }
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) this.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer centeredRenderer = new DefaultTableCellRenderer();
        centeredRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        this.getColumnModel().getColumn(1).setCellRenderer(centeredRenderer);
        this.getColumnModel().getColumn(2).setCellRenderer(centeredRenderer);
        this.panel.add(tableScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 40, 584, 519));
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

        javax.swing.GroupLayout addingPanelLayout = new javax.swing.GroupLayout(addingPanel);
        this.addingPanel.setLayout(addingPanelLayout);
        addingPanelLayout.setHorizontalGroup(
            addingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );
        addingPanelLayout.setVerticalGroup(
            addingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addingLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        this.panel.add(addingPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 580, 84, 35));        
    }
    
    
    private void initAlteringButton() {
        this.alteringPanel = new javax.swing.JPanel();
        this.alteringLabel = new javax.swing.JLabel();

        this.alteringPanel.setBackground(new java.awt.Color(255, 193, 7));
        this.alteringPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 183, 7), 5, true));

        this.alteringLabel.setBackground(new java.awt.Color(255, 255, 255));
        this.alteringLabel.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        this.alteringLabel.setForeground(new java.awt.Color(255, 255, 255));
        this.alteringLabel.setText("   Alterar");

        javax.swing.GroupLayout alteringPanelLayout = new javax.swing.GroupLayout(alteringPanel);
        this.alteringPanel.setLayout(alteringPanelLayout);
        alteringPanelLayout.setHorizontalGroup(
            alteringPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, alteringPanelLayout.createSequentialGroup()
                .addComponent(alteringLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        alteringPanelLayout.setVerticalGroup(
            alteringPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, alteringPanelLayout.createSequentialGroup()
                .addComponent(alteringLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        this.panel.add(alteringPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 580, 84, 35));
        this.alteringPanel.getAccessibleContext().setAccessibleName("");        
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

        javax.swing.GroupLayout removingPanelLayout = new javax.swing.GroupLayout(removingPanel);
        this.removingPanel.setLayout(removingPanelLayout);
        removingPanelLayout.setHorizontalGroup(
            removingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(removingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
        );
        removingPanelLayout.setVerticalGroup(
            removingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, removingPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(removingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        this.panel.add(removingPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 580, -1, -1));            
    }

    
    public class GUITableModel extends DefaultTableModel {
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


        public GUITableModel(Object[][] data, String[] columnNames) {
            super(data, columnNames);
        }
    }
}
