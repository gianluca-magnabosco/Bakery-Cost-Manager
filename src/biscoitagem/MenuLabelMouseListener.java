package biscoitagem;

import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class MenuLabelMouseListener extends java.awt.event.MouseAdapter {
    JPanel panel;
    JPanel emptyJPanel;
    String text;
    Color currentBackgroundColor;
    JFrame topFrame;
    PageManager manager;
    
    
    MenuLabelMouseListener(JLabel label, JPanel panel, String text, PageManager manager) {
        this.panel = panel;
        this.text = text;
        this.manager = manager;
        
        this.currentBackgroundColor = panel.getBackground();
        this.topFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
    }
    
    
    @Override
    public void mouseEntered(MouseEvent event) {
        if (this.text.equals("Menu")) {
            return;
        }
        
        panel.setBackground(new Color(84, 24, 24));
    }
    
    
    @Override
    public void mouseExited(MouseEvent event) {
        if (this.text.equals("Menu")) {
            return;
        }
        
        panel.setBackground(this.currentBackgroundColor);
    }
    
  
    @Override
    public void mouseClicked(MouseEvent event) {
        if (this.text.equals("Menu")) {
            if (this.panel.isVisible()) {
                this.panel.setVisible(false);
                addEmptyJPanel();
                return;
            }
            
            this.emptyJPanel.setVisible(false);
            this.panel.setVisible(true);
            
            return;
        } 
            
        this.manager.setVisible(this.text);
    }
    
   
    
    private void addEmptyJPanel() {
        this.emptyJPanel = new JPanel();
        this.emptyJPanel.setBackground(new java.awt.Color(108, 117, 125));
        this.emptyJPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        JLabel heartImageLabel = new JLabel();
        heartImageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/icons8-love-130.png"))); // NOI18N
        this.emptyJPanel.add(heartImageLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 200, -1, -1));

        JLabel teAmoLabel = new JLabel();
        teAmoLabel.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        teAmoLabel.setText("Te amo");
        this.emptyJPanel.add(teAmoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 330, -1, -1));
        this.topFrame.getContentPane().add(this.emptyJPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 280, 940));
        this.emptyJPanel.setVisible(true);
    }
}

