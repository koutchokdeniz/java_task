import java.time.Instant;


public class Deal {
    private String dealId;
    private String fromCurrencyISO;
    private String toCurrencyISO;
    private Instant dealTimestamp;
    private double dealAmount;


    // Constructors, getters, and setters

    public Deal(String dealId, String fromCurrencyISO, String toCurrencyISO, Instant dealTimestamp, double dealAmount) {
        this.dealId = dealId;
        this.fromCurrencyISO = fromCurrencyISO;
        this.toCurrencyISO = toCurrencyISO;
        this.dealTimestamp = dealTimestamp;
        this.dealAmount = dealAmount;
    }

    public String getDealId() {
        return dealId;
    }

    public String getFromCurrencyISO() {
        return fromCurrencyISO;
    }

    public void setFromCurrencyISO(String fromCurrencyISO) {
        this.fromCurrencyISO = fromCurrencyISO;
    }

    public String getToCurrencyISO() {
        return toCurrencyISO;
    }

    public void setToCurrencyISO(String toCurrencyISO) {
        this.toCurrencyISO = toCurrencyISO;
    }

    public Instant getDealTimestamp() {
        return dealTimestamp;
    }

    public double getDealAmount() {
        return dealAmount;
    }


}
