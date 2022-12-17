package biscoitagem.utils;


public class InputFormatHandler {
    
    public static double getPriceAsDouble(String content) {
        if (!content.matches("[Rr]?\\$?\\s*\\d+([\\,\\.]\\d{1,2})?")) {
            return 0;
        }

        double actual_content; 
        try { 
            content = content.replaceAll("\\,", ".").replaceAll("[Rr]?\\$\\s*", "");
            actual_content = Double.parseDouble(content);
        } catch (Exception e) {
            return 0;
        }
        
        return actual_content;
    }
    
    public static String formatToReais(double valor) {
        return "R$ " + Double.toString(valor).replaceAll("\\.", ",");
    }
    
    
    
    public static String getAmountFromTableRow(String tableRow) {
        if (tableRow.contains("g") || tableRow.contains("W")) {
            return tableRow.substring(0, tableRow.length() - 1);
        } else if (tableRow.contains("ml") || tableRow.contains("un")) {
            return tableRow.substring(0, tableRow.length() - 2);
        } else if (tableRow.contains("min")) {
            return tableRow.substring(0, tableRow.length() - 3);
        }

        return null;
    }
    
    
    public static String getMetricaFromTableRow(String tableRow) {
        if (tableRow.contains("g")) {
            return "g";
        } else if (tableRow.contains("ml")) {
            return "ml";
        } else if (tableRow.contains("un")) {
            return "un";
        } else if (tableRow.contains("W")) {
            return "W";
        }
        
        return null;
    }
   
}
