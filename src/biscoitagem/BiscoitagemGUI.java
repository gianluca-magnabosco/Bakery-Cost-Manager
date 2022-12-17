package biscoitagem;

public class BiscoitagemGUI extends javax.swing.JFrame {
    
    PageManager manager;

    public BiscoitagemGUI() {
        initComponents();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        this.manager = new PageManager(this);
        this.setMouseListeners();
    }
    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        menuLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        dividerLabel = new javax.swing.JLabel();
        menuPanel = new javax.swing.JPanel();
        vendasPanel = new javax.swing.JPanel();
        vendasLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        estoquePanel = new javax.swing.JPanel();
        estoqueLabel = new javax.swing.JLabel();
        operacionalLabel = new javax.swing.JLabel();
        financeiroLabel = new javax.swing.JLabel();
        produtosPanel = new javax.swing.JPanel();
        produtosLabel = new javax.swing.JLabel();
        insumosPanel = new javax.swing.JPanel();
        insumosLabel = new javax.swing.JLabel();
        receitasPanel = new javax.swing.JPanel();
        receitasLabel = new javax.swing.JLabel();
        eletrodomesticosPanel = new javax.swing.JPanel();
        eletrodomesticosLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(64, 68, 75));
        setMaximumSize(new java.awt.Dimension(1366, 768));
        setMinimumSize(new java.awt.Dimension(1366, 768));
        setPreferredSize(new java.awt.Dimension(1366, 768));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        headerPanel.setBackground(new java.awt.Color(204, 0, 51));
        headerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/icons8_menu_48px_1.png"))); // NOI18N
        headerPanel.add(menuLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 16, -1, -1));

        nameLabel.setFont(new java.awt.Font("Segoe UI Semilight", 0, 25)); // NOI18N
        nameLabel.setForeground(new java.awt.Color(255, 255, 255));
        nameLabel.setText("Giovanna Notari");
        headerPanel.add(nameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 190, 40));

        dividerLabel.setFont(new java.awt.Font("Tahoma", 0, 45)); // NOI18N
        dividerLabel.setForeground(new java.awt.Color(51, 51, 51));
        dividerLabel.setText("|");
        headerPanel.add(dividerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, -10, 50, 90));

        getContentPane().add(headerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1930, 80));

        menuPanel.setBackground(new java.awt.Color(51, 51, 51));
        menuPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        vendasPanel.setBackground(new java.awt.Color(0, 0, 0));
        vendasPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        vendasLabel.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        vendasLabel.setForeground(new java.awt.Color(153, 153, 153));
        vendasLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/account_24px.png"))); // NOI18N
        vendasLabel.setText("    Vendas");
        vendasPanel.add(vendasLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 130, 40));
        vendasPanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -40, -1, -1));

        menuPanel.add(vendasPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 280, 60));

        estoquePanel.setBackground(new java.awt.Color(51, 51, 51));
        estoquePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        estoqueLabel.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        estoqueLabel.setForeground(new java.awt.Color(153, 153, 153));
        estoqueLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/icons8-open-box-28.png"))); // NOI18N
        estoqueLabel.setText("   Estoque");
        estoquePanel.add(estoqueLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 180, 40));

        menuPanel.add(estoquePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 280, 60));

        operacionalLabel.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        operacionalLabel.setForeground(new java.awt.Color(162, 162, 162));
        operacionalLabel.setText("   Operacional");
        menuPanel.add(operacionalLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 160, 40));

        financeiroLabel.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        financeiroLabel.setForeground(new java.awt.Color(162, 162, 162));
        financeiroLabel.setText("   Financeiro");
        menuPanel.add(financeiroLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 160, 40));

        produtosPanel.setBackground(new java.awt.Color(0, 0, 0));
        produtosPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        produtosLabel.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        produtosLabel.setForeground(new java.awt.Color(153, 153, 153));
        produtosLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/icons8-pink-macaron-29.png"))); // NOI18N
        produtosLabel.setText("  Produtos");
        produtosPanel.add(produtosLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 130, 40));

        menuPanel.add(produtosPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 280, 60));

        insumosPanel.setBackground(new java.awt.Color(0, 0, 0));
        insumosPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        insumosLabel.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        insumosLabel.setForeground(new java.awt.Color(153, 153, 153));
        insumosLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/icons8-ingredients-30.png"))); // NOI18N
        insumosLabel.setText("  Insumos");
        insumosPanel.add(insumosLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 190, 40));

        menuPanel.add(insumosPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 280, 60));

        receitasPanel.setBackground(new java.awt.Color(51, 51, 51));
        receitasPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        receitasLabel.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        receitasLabel.setForeground(new java.awt.Color(153, 153, 153));
        receitasLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/icons8-cooking-book-30.png"))); // NOI18N
        receitasLabel.setText("  Receitas");
        receitasPanel.add(receitasLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 130, 40));

        menuPanel.add(receitasPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 280, 60));

        eletrodomesticosPanel.setBackground(new java.awt.Color(51, 51, 51));
        eletrodomesticosPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        eletrodomesticosLabel.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        eletrodomesticosLabel.setForeground(new java.awt.Color(153, 153, 153));
        eletrodomesticosLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/icons8-mixer-30.png"))); // NOI18N
        eletrodomesticosLabel.setText("  Eletrodomésticos");
        eletrodomesticosPanel.add(eletrodomesticosLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 190, 40));

        menuPanel.add(eletrodomesticosPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 280, 60));

        getContentPane().add(menuPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 280, 940));

        setSize(new java.awt.Dimension(1382, 776));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BiscoitagemGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BiscoitagemGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BiscoitagemGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BiscoitagemGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new BiscoitagemGUI().setVisible(true);
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel dividerLabel;
    private javax.swing.JLabel eletrodomesticosLabel;
    private javax.swing.JPanel eletrodomesticosPanel;
    private javax.swing.JLabel estoqueLabel;
    private javax.swing.JPanel estoquePanel;
    private javax.swing.JLabel financeiroLabel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel insumosLabel;
    private javax.swing.JPanel insumosPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel menuLabel;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel operacionalLabel;
    private javax.swing.JLabel produtosLabel;
    private javax.swing.JPanel produtosPanel;
    private javax.swing.JLabel receitasLabel;
    private javax.swing.JPanel receitasPanel;
    private javax.swing.JLabel vendasLabel;
    private javax.swing.JPanel vendasPanel;
    // End of variables declaration//GEN-END:variables


    private void setMouseListeners() {
        menuLabel.addMouseListener(new MenuLabelMouseListener(menuLabel, menuPanel, "Menu", this.manager));
        vendasLabel.addMouseListener(new MenuLabelMouseListener(vendasLabel, vendasPanel, "Vendas", this.manager));
        estoqueLabel.addMouseListener(new MenuLabelMouseListener(estoqueLabel, estoquePanel, "Estoque", this.manager));
        insumosLabel.addMouseListener(new MenuLabelMouseListener(insumosLabel, insumosPanel, "Insumos", this.manager));
        produtosLabel.addMouseListener(new MenuLabelMouseListener(produtosLabel, produtosPanel, "Produtos", this.manager));
        receitasLabel.addMouseListener(new MenuLabelMouseListener(receitasLabel, receitasPanel, "Receitas", this.manager));
        eletrodomesticosLabel.addMouseListener(new MenuLabelMouseListener(eletrodomesticosLabel, eletrodomesticosPanel, "Eletrodomesticos", this.manager));
    }
    
  
}
