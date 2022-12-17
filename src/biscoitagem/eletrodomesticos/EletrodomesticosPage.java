package biscoitagem.eletrodomesticos;

import biscoitagem.dao.ConnectionFactory;
import biscoitagem.dao.EletrodomesticoDAO;
import biscoitagem.exception.DAOException;
import static biscoitagem.utils.InputFormatHandler.getPriceAsDouble;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class EletrodomesticosPage {
    private JPanel panel;

    private EletrodomesticosCalcularCustoTempoComponent custoPorTempoComponent;
    private EletrodomesticosKWhComponent kwhComponent;
    private EletrodomesticosTopPanel topPanel;
    private EletrodomesticosTable table;
        
    private JFrame frame;
    
    
    public EletrodomesticosPage(JFrame frame) {
        this.frame = frame;
        this.initComponents();
        this.frame.revalidate();
        this.frame.repaint();
    }
    
    public JPanel getPanel() {
        return this.panel;
    }
    
    public void updatePageData() {
        this.custoPorTempoComponent.showPrecoPorTempo();
        this.kwhComponent.showKWh();
        this.table.showTableItems();
        
        this.frame.revalidate();
        this.frame.repaint();
    }   
    
    
    private void initComponents() {
        this.panel = new javax.swing.JPanel();
        this.panel.setBackground(new java.awt.Color(108, 117, 125));
        this.panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        
        this.initTableComponents();
        this.initCalcularTempoComponents();
        this.initKWhComponent();

        this.frame.getContentPane().add(this.panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 80, 1090, 690));
        
        this.addMouseListeners();

        this.panel.setVisible(false);
    }
    
    
    private void initTableComponents() {
        this.topPanel = new EletrodomesticosTopPanel();
        this.table = new EletrodomesticosTable(this.panel, this.topPanel);
        String[] columnNames = {"Nome", "Potência", "Preço/min"};
        this.table.setTableColumnNames(columnNames);
        this.table.initComponents();
        this.table.showTableItems();
    }
    
    private void initCalcularTempoComponents() {
        this.custoPorTempoComponent = new EletrodomesticosCalcularCustoTempoComponent(this.panel);
        this.custoPorTempoComponent.initComponents();
    }
    
    private void initKWhComponent() {
        this.kwhComponent = new EletrodomesticosKWhComponent(this.panel);
        this.kwhComponent.initComponents();
    }
    
    
    private void addMouseListeners() {
        this.kwhComponent.getButtonLabel().addMouseListener(new EletrodomesticosMouseListener(this.kwhComponent.getButtonPanel(), "KWh"));
        this.table.getRemovingLabel().addMouseListener(new EletrodomesticosMouseListener(this.table.getRemovingPanel(), "Remove"));
        this.table.getAlteringLabel().addMouseListener(new EletrodomesticosMouseListener(this.table.getAlteringPanel(), "Alter"));
        this.table.getAddingLabel().addMouseListener(new EletrodomesticosMouseListener(this.table.getAddingPanel(), "Add"));
        this.custoPorTempoComponent.getButtonLabel().addMouseListener(new EletrodomesticosMouseListener(this.custoPorTempoComponent.getButtonPanel(), "Calcular")); 
    }

    
    private void updateCustoEletrodomesticos() {
        try (ConnectionFactory factory = new ConnectionFactory()) {
            double kwh = getPriceAsDouble(this.kwhComponent.getTextFieldText());
            EletrodomesticoDAO eletrodomesticoDAO = new EletrodomesticoDAO(factory.getConnection());
            eletrodomesticoDAO.updatePreco(kwh);
        }
        catch (DAOException e) {
            System.out.println("### ERRO DE DAO: " + e.getMessage());
            e.printStackTrace();
        }
    }    
    
    
    
    public class EletrodomesticosMouseListener extends java.awt.event.MouseAdapter {
        JPanel panel;
        String text;
        Color currentBackgroundColor;
        Border currentBorder;
        
        
        EletrodomesticosMouseListener(JPanel panel, String text) {
            this.panel = panel;
            this.text = text;
            
            this.currentBorder = panel.getBorder();
            this.currentBackgroundColor = panel.getBackground();
        }
        
        @Override
        public void mouseEntered(MouseEvent event) {
            panel.setBackground(new Color(42, 98, 135));
            panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(42, 88, 135), 5, true));
        }
        
        @Override
        public void mouseExited(MouseEvent event) {  
            panel.setBackground(this.currentBackgroundColor);
            panel.setBorder(this.currentBorder);
        }   
        
        @Override
        public void mouseClicked(MouseEvent event) {   
            switch (this.text) {
                case "KWh" -> {
                    kwhComponent.atualizarKWh();
                    updateCustoEletrodomesticos();
                    updatePageData();
                }
                
                case "Add" -> {
                    table.insertEletrodomesticos();
                    updatePageData();
                }
                
                case "Alter" -> {
                    table.updateEletrodomesticos();
                    updateCustoEletrodomesticos();
                    updatePageData();
                }
                
                case "Remove" -> {
                    table.deleteEletrodomesticos();
                    updatePageData();
                }
                
                
                case "Calcular" -> {
                    try {
                        custoPorTempoComponent.calcularPrecoPorTempo();
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
