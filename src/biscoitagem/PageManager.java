package biscoitagem;

import biscoitagem.eletrodomesticos.EletrodomesticosPage;
import biscoitagem.insumos.InsumosPage;
import biscoitagem.produtos.ProdutosPage;
import biscoitagem.receitas.ReceitasPage;
import javax.swing.JFrame;

class PageManager {
    private EletrodomesticosPage eletrodomesticosPage;
    private InsumosPage insumosPage;
    private ReceitasPage receitasPage;
    private ProdutosPage produtosPage;
    //private EstoquePage estoquePage;
    //private VendasPage vendasPage;
    private JFrame topFrame;
    
    
    PageManager(JFrame topFrame) {
        this.topFrame = topFrame;
        this.eletrodomesticosPage = new EletrodomesticosPage(this.topFrame);
        this.insumosPage = new InsumosPage(this.topFrame);
        this.receitasPage = new ReceitasPage(this.topFrame);
        this.produtosPage = new ProdutosPage(this.topFrame);
        //this.estoquePage = new EstoquePage(this.topFrame);
        //this.vendasPage = new VendasPage(this.topFrame);
    }
    
    
    public void setVisible(String text) {
        hidePreviousPanel();
        switch (text) {
            case "Eletrodomesticos" -> {
                this.eletrodomesticosPage.getPanel().setVisible(true);
                this.eletrodomesticosPage.updatePageData();
            }  
            
            case "Insumos" -> {
                this.insumosPage.getPanel().setVisible(true);
                this.insumosPage.updatePageData();
            }
            
            case "Receitas" -> {
                this.receitasPage.getPanel().setVisible(true);
                this.receitasPage.updatePageData();
            }
            
            case "Produtos" -> { 
                this.produtosPage.getPanel().setVisible(true);
                this.produtosPage.updatePageData();
            }
            /*
            case "Estoque" -> {
                this.estoquePage.getPanel().setVisible(true);
                this.estoquePage.updatePageData();
            }
            case "Vendas" -> {
                this.vendasPage.getPanel().setVisible(true);
                this.vendasPage.updatePageData();
            }
            */
            default -> {
            }
        }
    }
    
    
    private void hidePreviousPanel() {
        if (this.eletrodomesticosPage.getPanel().isVisible()) {
            this.eletrodomesticosPage.getPanel().setVisible(false);
        } 
        
        if (this.insumosPage.getPanel().isVisible()) {
            this.insumosPage.getPanel().setVisible(false);
        } 
        
        if (this.receitasPage.getPanel().isVisible()) {
            this.receitasPage.getPanel().setVisible(false);
        } 
        
        if (this.produtosPage.getPanel().isVisible()) {
            this.produtosPage.getPanel().setVisible(false);
        } 
        
        /*
        if (this.vendasPage.getPanel().isVisible()) {
            this.vendasPage.getPanel().setVisible(false);
        } 
        
        if (this.estoquePage.getPanel().isVisible()) {
            this.estoquePage.getPanel().setVisible(false);
        } 
        
        
        */

        this.topFrame.revalidate();
        this.topFrame.repaint();
    }
    
}
