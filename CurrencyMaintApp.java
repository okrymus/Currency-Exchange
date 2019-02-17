import java.util.Scanner;
import java.util.ArrayList;
import java.util.*;

public class CurrencyMaintApp implements CurrencyConstants {
    private static CurrencyDAO currencyDAO = null;
    private static Scanner sc = null;
	public static ArrayList<Currency> currencyTemp = null;
	
    public static void main(String args[]) {
        System.out.println("Welcome to the Currency Maintenance application\n");

        currencyDAO = DAOFactory.getCurrencyDAO();
        sc = new Scanner(System.in);

        displayMenu();

        String action = "";
        while (!action.equalsIgnoreCase("exit")) {
            action = Validator.getString(sc, "Enter a command: ");
            System.out.println();

            if (action.equalsIgnoreCase("buylist") || action.equalsIgnoreCase("buy")) {
                displayAllCurrency();
            } 
			else if  (action.equalsIgnoreCase("selllist") || action.equalsIgnoreCase("sell")){
				displayAllCurrencySell() ;
			}
			else if (action.equalsIgnoreCase("add")) {
                addCurrency();
            } else if (action.equalsIgnoreCase("del") || action.equalsIgnoreCase("delete")) {
                deleteCurrency();
            } else if (action.equalsIgnoreCase("update")) {
                updateCurrency();
            } else if (action.equalsIgnoreCase("help") || action.equalsIgnoreCase("menu")) {
                displayMenu();
            } else if (action.equalsIgnoreCase("sort")) {
                sortBySymbol();
            } 			else if (action.equalsIgnoreCase("exit")) {
                System.out.println("Bye.\n");
            } else {
                System.out.println("Error! Not a valid command.\n");
            }

            // switch (action.toLowerCase()) {
            // case "list": displayAllProducts(); break;

            // case "add": addProduct(); break;

            // case "del":
            // case "delete": deleteProduct(); break;

            // case "update": updateProduct(); break;

            // case "help":
            // case "menu": displayMenu(); break;

            // case "exit":
            // case "quit":   System.out.println("Bye.\n"); break;

            // default: System.out.println("Error! Not a valid command.\n"); break;
            // }
        }
    }

    public static void displayMenu() {
        System.out.println("COMMAND MENU");
        System.out.println("buy && buylist    - List all currencies");
		System.out.println("sell && selllist  - ListSell all currencies");
        System.out.println("add               - Add a currency");
        System.out.println("del               - Delete a currency");
        System.out.println("update            - Update a currency");
        System.out.println("help              - Show this menu");
        System.out.println("exit              - Exit this application\n");
    }

    public static void displayAllCurrency() {
        System.out.println("CURRENCY BUY LIST");
		currencyDAO.changeCurrenciesFilename("currenciesBuy.xml");
		currencyTemp = currencyDAO.getCurrencies();
		
        if (currencyTemp == null) {
            System.out.println("Error! Unable to get currencies.\n");
        } else {
            Currency c = null;
            StringBuilder sb = new StringBuilder();
			 System.out.println("DATE          SYMBOL  DESCRIPTION             RATE PER ONE DOLLAR");
            for (int i = 0; i < currencyTemp.size(); i++) {
                c = currencyTemp.get(i);
                sb.append(StringUtils.padWithSpaces(c.getDate(), DATE_SIZE + 4) +
                        StringUtils.padWithSpaces(c.getId(), ID_SIZE + 4) +
                        StringUtils.padWithSpaces(c.getDescription(), DESCRIPTION_SIZE + 4) +
                     
                        c.getFormattedPrice() + "\n");
            }
            System.out.println(sb.toString());
        }
    }
	
	public static void sortBySymbol(){
	Collections.sort(currencyTemp, new MyCompName());
	 Iterator<Currency> it =currencyTemp.iterator();
	if (it == null) {
            System.out.println("Error! Unable to get currencies.\n");
        } else {
            Currency c = null;
            StringBuilder sb = new StringBuilder();
			 System.out.println("DATE          SYMBOL  DESCRIPTION             RATE PER ONE DOLLAR");
            for (; it.hasNext();) {
                c = it.next();
                sb.append(StringUtils.padWithSpaces(c.getDate(), DATE_SIZE + 4) +
                        StringUtils.padWithSpaces(c.getId(), ID_SIZE + 4) +
                        StringUtils.padWithSpaces(c.getDescription(), DESCRIPTION_SIZE + 4) +
                     
                        c.getFormattedPrice() + "\n");
            }
            System.out.println(sb.toString());
        }
	}
	
	 public static void displayAllCurrencySell() {
        System.out.println("CURRENCY SELL LIST");
currencyDAO.changeCurrenciesFilename("currenciesSell.xml");
        ArrayList<Currency> currencies = currencyDAO.getCurrencies();
        if (currencies == null) {
            System.out.println("Error! Unable to get currencies.\n");
        } else {
            Currency c = null;
            StringBuilder sb = new StringBuilder();
			 System.out.println("DATE          SYMBOL  DESCRIPTION             RATE PER ONE DOLLAR");
            for (int i = 0; i < currencies.size(); i++) {
                c = currencies.get(i);
                sb.append(StringUtils.padWithSpaces(c.getDate(), DATE_SIZE + 4) +
                        StringUtils.padWithSpaces(c.getId(), ID_SIZE + 4) +
                        StringUtils.padWithSpaces(c.getDescription(), DESCRIPTION_SIZE + 4) +
                     
                        c.getFormattedPrice() + "\n");
            }
            System.out.println(sb.toString());
        }
    }

    public static void addCurrency() {
        String date = Validator.getString(sc, "Enter currency on date: ");
        String ID = Validator.getString(sc, "Enter currency ID: ");
        String description = Validator.getLine(sc, "Enter currency description: ");
        double USDollarPerUnit = Validator.getDouble(sc, "Enter US dollar per unit: ");

        Currency currency = new Currency();

        currency.setDate(date);
        currency.setId(ID);
        currency.setDescription(description);
        currency.setPrice(USDollarPerUnit);
        boolean success = currencyDAO.addCurrency(currency);

        System.out.println();
        if (success) {
            System.out.println(description + " was added to the database.\n");
        } else {
            System.out.println("Error! Unable to add currency\n");
        }
    }

    public static void deleteCurrency() {
        String id = Validator.getString(sc, "Enter currency's symbol to delete: ");
		String date = Validator.getString(sc, "Enter date to delete: ");
        Currency c = currencyDAO.getCurrency(id,date);

        System.out.println();
        if (c != null) {
            boolean success = currencyDAO.deleteCurrency(c);
            if (success) {
                System.out.println(c.getDescription()
                        + " was deleted from the database.\n");
            } else {
                System.out.println("Error! Unable to add currency\n");
            }
        } else {
            System.out.println("No currency matches that ID.\n");
        }
    }

    public static void updateCurrency() {
        String date = Validator.getString(sc, "Enter currency on date: ");
        String ID = Validator.getString(sc, "Enter currency ID: ");
        String description = Validator.getLine(sc, "Enter currency description: ");
        double USDollarPerUnit = Validator.getDouble(sc, "Enter US dollar per unit: ");

        Currency currency = new Currency();

        currency.setDate(date);
        currency.setId(ID);
        currency.setDescription(description);
        currency.setPrice(USDollarPerUnit);
        boolean success = currencyDAO.updateCurrency(currency);

        System.out.println();
        if (success) {
            System.out.println(description
                    + " was added to the database.\n");
        } else {
            System.out.println("Error! Unable to add currency\n");
        }
    }
}
class MyCompName implements Comparator<Object> {

// eclipse ask for casting object
    public int compare(Object emp1, Object emp2) {

        String emp1Name = ((Currency) emp1).getId();

        String emp2Name = ((Currency) emp2).getId();

        return emp1Name.compareTo(emp2Name);

    }
}
