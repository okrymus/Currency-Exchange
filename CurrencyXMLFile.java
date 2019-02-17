import java.util.*;
import java.io.*;
import javax.xml.stream.*;  // StAX API

public class CurrencyXMLFile implements CurrencyDAO {
    private String currenciesFilename = "currenciesBuy.xml";
    private File currenciesFile = null;

    public CurrencyXMLFile() {
        currenciesFile = new File(currenciesFilename);
    }

    private void checkFile() throws IOException {
        if (!currenciesFile.exists()) {
            currenciesFile.createNewFile();
        }
    }
	
	public void changeCurrenciesFilename(String currenciesFilename){
	this.currenciesFilename = currenciesFilename;
	}
	

    private boolean saveCurrencies(ArrayList<Currency> currencies) {

        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

        try {
            this.checkFile();

            FileWriter fileWriter = new FileWriter(currenciesFilename);
            XMLStreamWriter writer = outputFactory.createXMLStreamWriter(fileWriter);

            writer.writeStartDocument("1.0");
            writer.writeStartElement("Currencies");
            for (Currency c : currencies) {
                writer.writeStartElement("Currency");
                writer.writeAttribute("ID",c.getId());

                writer.writeStartElement("Date");
                writer.writeCharacters( c.getDate());
                writer.writeEndElement();

                writer.writeStartElement("Description");
                writer.writeCharacters(c.getDescription());
                writer.writeEndElement();

      

                writer.writeStartElement("PriceUSdollarPerUnit");
                double USDollarPerUnit = c.getPrice();
                writer.writeCharacters(Double.toString(USDollarPerUnit));
                writer.writeEndElement();

                writer.writeEndElement();
            }
            writer.writeEndElement();
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (XMLStreamException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<Currency> getCurrencies() {
        ArrayList<Currency> currencies = new ArrayList<Currency>();
        Currency c = null;
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();

        try {
            this.checkFile();
			

            FileReader fileReader = new FileReader(currenciesFilename);
            XMLStreamReader reader = inputFactory.createXMLStreamReader(fileReader);

            while (reader.hasNext()) {
                int eventType = reader.getEventType();

                switch (eventType) {
                case XMLStreamConstants.START_ELEMENT:
                    String elementName = reader.getLocalName();

                    if (elementName.equals("Currency")) {
                        c = new Currency();
                        String id = reader.getAttributeValue(0);
                        c.setId(id);
                    }
                    if (elementName.equals("Date")) {
                        String date = reader.getElementText();
                        c.setDate(date);
                    }
                    if (elementName.equals("Description")) {
                        String description = reader.getElementText();
                        c.setDescription(description);
                    }
                    
                    if (elementName.equals("PriceUSdollarPerUnit")) {
                        String priceString = reader.getElementText();
                        double USDollarPerUnit = Double.parseDouble(priceString);
                        c.setPrice(USDollarPerUnit);
                    }
            
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    elementName = reader.getLocalName();
                    if (elementName.equals("Currency")) {
                        currencies.add(c);
                    }
                    break;
                default:
                    break;
                }
                reader.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (XMLStreamException e) {
            e.printStackTrace();
            return null;
        }
        return currencies;
    }

    public Currency getCurrency(String id, String date) {
        ArrayList<Currency> currencies = this.getCurrencies();

        for (Currency c : currencies) {
            if (c.getId().equals(id)  && c.getDate().equals(date)) {
                return c;
            }
        }
        return null;
    }
	
	   public Currency getCurrency(String id) {
        ArrayList<Currency> currencies = this.getCurrencies();

        for (Currency c : currencies) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public boolean addCurrency(Currency c) {
        ArrayList<Currency> currencies = this.getCurrencies();
        currencies.add(c);

        return this.saveCurrencies(currencies);
    }

    public boolean deleteCurrency(Currency c) {
        ArrayList<Currency> currencies = this.getCurrencies();
        currencies.remove(c);

        return this.saveCurrencies(currencies);
    }

    public boolean updateCurrency(Currency newCurrency) {
        ArrayList<Currency> currencies = this.getCurrencies();

        // get the old Currency and remove it
        Currency oldCurrency = this.getCurrency(newCurrency.getId(),newCurrency.getDate());
        int i = currencies.indexOf(oldCurrency);
        currencies.remove(i);

        // add the updated currency
        currencies.add(i, newCurrency);

        return this.saveCurrencies(currencies);
    }
}
