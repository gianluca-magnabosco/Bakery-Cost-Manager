package biscoitagem.insumos;

import biscoitagem.exception.DAOException;
import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class InsumosPage {
    private JFrame frame;
    private JPanel panel;
    private InsumosTable table;
    private InsumosCalcularPrecoPorQuantidadeComponent precoPorQuantidadeComponent;
    private InsumosAverageComponent averageComponent;
    private InsumosTopPanel topPanel;
    
    
    public InsumosPage(JFrame frame) {
        this.frame = frame;
        this.initComponents();
        this.frame.revalidate();
        this.frame.repaint();
    }
    
    
    public JPanel getPanel() {
        return this.panel;
    }
    
    public void updatePageData() {
        this.table.showTableItems();
        this.precoPorQuantidadeComponent.showPrecoPorQuantidade();
        this.averageComponent.clearTextFields();
        this.frame.revalidate();
        this.frame.repaint();
    }
    
    
    private void initComponents() {
        this.panel = new javax.swing.JPanel();
        this.panel.setBackground(new java.awt.Color(108, 117, 125));
        this.panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        
        this.initTableComponents();
        this.initPrecoPorQuantidadeComponent();
        this.initAverageComponent();

        this.frame.getContentPane().add(this.panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 80, 1090, 690));
        
        this.addMouseListeners();

        this.panel.setVisible(false);
    }
    

    private void initTableComponents() {
        this.topPanel = new InsumosTopPanel();
        this.table = new InsumosTable(this.panel, this.topPanel);
        this.table.initComponents();
        this.table.showTableItems();
    }
    
    
    private void initPrecoPorQuantidadeComponent() {
        this.precoPorQuantidadeComponent = new InsumosCalcularPrecoPorQuantidadeComponent(this.panel);
        this.precoPorQuantidadeComponent.initComponents();
    }
    
    
    private void initAverageComponent() {
        this.averageComponent = new InsumosAverageComponent(this.panel);
        this.averageComponent.initComponents();
    }    
    
    
    private void addMouseListeners() {
        this.averageComponent.getButtonLabel().addMouseListener(new InsumosMouseListener(this.averageComponent.getButtonPanel(), "Calcular Media"));
        this.table.getAddingLabel().addMouseListener(new InsumosMouseListener(this.table.getAddingPanel(), "Add"));
        this.table.getAlteringLabel().addMouseListener(new InsumosMouseListener(this.table.getAlteringPanel(), "Alter"));
        this.table.getRemovingLabel().addMouseListener(new InsumosMouseListener(this.table.getRemovingPanel(), "Remove"));
        this.precoPorQuantidadeComponent.getButtonLabel().addMouseListener(new InsumosMouseListener(this.precoPorQuantidadeComponent.getButtonPanel(), "Calcular Preco Por Quantidade"));
    }
    
    
    
    
    public class InsumosMouseListener extends java.awt.event.MouseAdapter {
        JPanel panel;
        String text;
        Color currentBackgroundColor;
        Border currentBorder;
        
        
        InsumosMouseListener(JPanel panel, String text) {
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
                    table.insertInsumos();
                    updatePageData();
                }
                
                case "Alter" -> {
                    table.updateInsumos();
                    updatePageData();
                }
                
                case "Remove" -> {
                    table.deleteInsumos();
                    updatePageData();
                }
                
                case "Calcular Media" -> averageComponent.calcularMedia();
                
                case "Calcular Preco Por Quantidade" -> {
                    try {
                        precoPorQuantidadeComponent.calcularPrecoPorQuantidade();
                    } catch (DAOException ex) {
                        System.out.println("### ERRO DE DAO: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
                
                default -> {
                }
            }
        } 
    }
}

        
