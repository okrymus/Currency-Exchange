public class DAOFactory {
    // this method maps the CurrencyDAOinterface
    // to the appropriate data storage mechanism
    public static CurrencyDAO getCurrencyDAO() {
        CurrencyDAO cDAO = new CurrencyXMLFile();
        return cDAO;
    }
}
