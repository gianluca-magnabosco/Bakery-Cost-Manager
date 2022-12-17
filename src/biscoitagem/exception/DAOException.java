package biscoitagem.exception;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class DAOException extends Exception {
    
    public DAOException() {
        
    }
    
    public DAOException(String string) {
        super(string);
        JOptionPane.showMessageDialog(new JFrame(), string, "Erro!", JOptionPane.ERROR_MESSAGE);
    }

    
    public DAOException(String string, Throwable throwable) {
        super(string, throwable);
        JOptionPane.showMessageDialog(new JFrame(), string, "Erro!", JOptionPane.ERROR_MESSAGE);
    }
}
