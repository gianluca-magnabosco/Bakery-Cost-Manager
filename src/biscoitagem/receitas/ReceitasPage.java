package biscoitagem.receitas;

import biscoitagem.exception.DAOException;
import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class ReceitasPage {
    private JFrame frame;
    private JPanel panel;
    private ReceitasTable table;
    private ReceitasCalcularCustoPorQuantidadeComponent custoPorQuantidadeComponent;
    private ReceitasTopPanel topPanel;   
    
    public ReceitasPage(JFrame frame) {
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
        this.custoPorQuantidadeComponent.showCustoPorQuantidade();
        this.frame.revalidate();
        this.frame.repaint();
    }
    
    
    private void initComponents() {
        this.panel = new javax.swing.JPanel();
        this.panel.setBackground(new java.awt.Color(108, 117, 125));
        this.panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        
        this.initTableComponents();
        this.initCustoPorQuantidadeComponent();
        
        this.frame.getContentPane().add(this.panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 80, 1090, 690));
        
        this.addMouseListeners();

        this.panel.setVisible(false);
    }
    

    private void initTableComponents() {
        this.topPanel = new ReceitasTopPanel();
        this.table = new ReceitasTable(this.panel, this.topPanel);
        this.table.initComponents();
        this.table.showTableItems();
    }
    
    
    private void initCustoPorQuantidadeComponent() {
        this.custoPorQuantidadeComponent = new ReceitasCalcularCustoPorQuantidadeComponent(this.panel);
        this.custoPorQuantidadeComponent.initComponents();
    }
    
    
    private void addMouseListeners() {
        this.table.getAddingLabel().addMouseListener(new ReceitasMouseListener(this.table.getAddingPanel(), "Add"));
        this.table.getAlteringLabel().addMouseListener(new ReceitasMouseListener(this.table.getAlteringPanel(), "Alter"));
        this.table.getRemovingLabel().addMouseListener(new ReceitasMouseListener(this.table.getRemovingPanel(), "Remove"));
        this.custoPorQuantidadeComponent.getButtonLabel().addMouseListener(new ReceitasMouseListener(this.custoPorQuantidadeComponent.getButtonPanel(), "Calcular"));
    }
    
    
    
    
    public class ReceitasMouseListener extends java.awt.event.MouseAdapter {
        JPanel panel;
        String text;
        Color currentBackgroundColor;
        Border currentBorder;
        
        
        ReceitasMouseListener(JPanel panel, String text) {
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
                    table.insertReceitas();
                    updatePageData();
                }
                
                case "Alter" -> {
                    try {
                        table.updateReceitas();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                    updatePageData();
                }
                
                case "Remove" -> {
                    table.deleteReceitas();
                    updatePageData();
                }
                
                case "Calcular" -> {
                    try {
                        custoPorQuantidadeComponent.calcularCustoPorQuantidade();
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

        

