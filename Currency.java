import java.text.NumberFormat;

public class Currency{
    private String id;
    private String date;
    private String description;
    private double USDollarPerUnit;


    public Currency() {
        this("", "", "", 0);
    }

    public Currency(String id, String date, String description
            ,double USDollarPerUnit) {
        setDate(date);
        setId(id);
        setDescription(description);
        setPrice(USDollarPerUnit);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


    public void setPrice(double USDollarPerUnit) {
        this.USDollarPerUnit = USDollarPerUnit;
    }

    public double getPrice() {
        return USDollarPerUnit;
    }

    public String getFormattedPrice() {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(USDollarPerUnit);
    }

    public boolean equals(Object object) {
        if (object instanceof Currency) {
            Currency currency2 = (Currency) object;

            if (
                date.equals(currency2.getDate()) &&
                id.equals(currency2.getId()) &&
                description.equals(currency2.getDescription()) &&
                USDollarPerUnit == currency2.getPrice()) {
                return true;
            }
        }
        return false;
    }

    // This is kind of an ugly way of circumventing the implicit call to this
    // class's toString() method by the DefaultListModel's addElement() method.
    public String toStringAllFields() {
        StringBuilder sb = new StringBuilder(100);
        sb.append("Date: ").append(date).append(" \n")
		     .append("ID: ").append(id).append(" \n")
            .append("description: ").append(description).append(" \n")
            .append("price: ").append(getFormattedPrice()).append(" \n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return description;
    }
}
