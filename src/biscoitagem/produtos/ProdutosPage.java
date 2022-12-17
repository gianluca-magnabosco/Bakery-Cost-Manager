package biscoitagem.produtos;

import biscoitagem.exception.DAOException;
import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class ProdutosPage {
    private JFrame frame;
    private JPanel panel;
    private ProdutosTable table;
    private ProdutosHorasTrabalhadasComponent horasTrabalhadasComponent;
    private ProdutosCalcularCustoPorQuantidadeComponent custoPorQuantidadeComponent;
    private ProdutosCalculoGlaceRealComponent calculoGlaceRealComponent;
    private ProdutosTopPanel topPanel;   
    
    
    public ProdutosPage(JFrame frame) {
        this.frame = frame;
        this.initComponents();
        this.frame.revalidate();
        this.frame.repaint();
    } 
    
    
    public JPanel getPanel() {
        return this.panel;
    }
    
    
    public void updatePageData() {
        this.custoPorQuantidadeComponent.showCustoPorQuantidade();
        this.horasTrabalhadasComponent.showValorHorasTrabalhadas();
        this.calculoGlaceRealComponent.clearTextFields();
        this.table.showTableItems();
        this.frame.revalidate();
        this.frame.repaint();
    }
    
    
    private void initComponents() {
        this.panel = new javax.swing.JPanel();
        this.panel.setBackground(new java.awt.Color(108, 117, 125));
        this.panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        
        this.initTableComponents();
        this.initCustoPorQuantidadeComponent();
        this.initCalculoGlaceRealComponent();
        this.initHorasTrabalhadasComponent();
        
        this.frame.getContentPane().add(this.panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 80, 1090, 690));
        
        this.addMouseListeners();

        this.panel.setVisible(false);
    }
    

    private void initTableComponents() {
        this.topPanel = new ProdutosTopPanel();
        this.table = new ProdutosTable(this.panel, this.topPanel);
        this.table.initComponents();
        this.table.showTableItems();
    }
    
    
    private void initCustoPorQuantidadeComponent() {
        this.custoPorQuantidadeComponent = new ProdutosCalcularCustoPorQuantidadeComponent(this.panel);
        this.custoPorQuantidadeComponent.initComponents();
    }
    
    
    private void initCalculoGlaceRealComponent() {
        this.calculoGlaceRealComponent = new ProdutosCalculoGlaceRealComponent(this.panel);
        this.calculoGlaceRealComponent.initComponents();
    }
    
    
    private void initHorasTrabalhadasComponent() {
        this.horasTrabalhadasComponent = new ProdutosHorasTrabalhadasComponent(this.panel);
        this.horasTrabalhadasComponent.initComponents();
    }
    
    
    private void addMouseListeners() {
        this.horasTrabalhadasComponent.getButtonLabel().addMouseListener(new ProdutosMouseListener(this.horasTrabalhadasComponent.getButtonPanel(), "Horas Trabalhadas"));
        this.table.getAddingLabel().addMouseListener(new ProdutosMouseListener(this.table.getAddingPanel(), "Add"));
        this.table.getAlteringLabel().addMouseListener(new ProdutosMouseListener(this.table.getAlteringPanel(), "Alter"));
        this.table.getRemovingLabel().addMouseListener(new ProdutosMouseListener(this.table.getRemovingPanel(), "Remove"));
        this.custoPorQuantidadeComponent.getButtonLabel().addMouseListener(new ProdutosMouseListener(this.custoPorQuantidadeComponent.getButtonPanel(), "Calcular"));
        this.calculoGlaceRealComponent.getButtonLabel().addMouseListener(new ProdutosMouseListener(this.calculoGlaceRealComponent.getButtonPanel(), "Calculo Glace Real"));
    }
    
    
    
    
    public class ProdutosMouseListener extends java.awt.event.MouseAdapter {
        JPanel panel;
        String text;
        Color currentBackgroundColor;
        Border currentBorder;
        
        
        ProdutosMouseListener(JPanel panel, String text) {
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
                case "Horas Trabalhadas" -> {
                    horasTrabalhadasComponent.atualizarValorHorasTrabalhadas();
                    updatePageData();
                }
                
                case "Add" -> {
                    table.insertProdutos();
                    updatePageData();
                }
                
                case "Alter" -> {
                    try {
                        table.updateProdutos();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                    updatePageData();
                }
                
                case "Remove" -> {
                    table.deleteProdutos();
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
                
                case "Calculo Glace Real" -> {
                    calculoGlaceRealComponent.calcular();
                }

                default -> {
                }
            }
        } 
    }
}

