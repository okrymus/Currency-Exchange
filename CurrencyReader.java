import java.util.ArrayList;

public interface CurrencyReader {
    Currency getCurrency(String Id,String date);
    ArrayList<Currency> getCurrencies();
	void changeCurrenciesFilename(String currenciesFilename);
}
