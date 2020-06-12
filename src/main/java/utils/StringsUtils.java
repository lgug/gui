package utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class StringsUtils {

    public static String foundProduct(int n) {
        if (n == 1) return n + " prodotto trovato";
        else return n + " prodotti trovati";
    }

    public static String getPriceString(Object price) {
        DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance();
        decimalFormatSymbols.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#.00", decimalFormatSymbols);
        String priceString = decimalFormat.format(price);
        return Manager.EURO + priceString;
    }
}
