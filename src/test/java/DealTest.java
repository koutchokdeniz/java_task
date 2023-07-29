import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.Instant;

public class DealTest {
    @Test
    public void testDealCreation() {
        // Create a sample Deal object
        String dealId = "12345";
        String fromCurrencyISO = "USD";
        String toCurrencyISO = "EUR";
        Instant dealTimestamp = Instant.now();
        double dealAmount =100;
        Deal deal = new Deal(dealId, fromCurrencyISO, toCurrencyISO, dealTimestamp, dealAmount);
        deal.setFromCurrencyISO("EUR");
        deal.setToCurrencyISO("USD");
        // Perform assertions to check the values of Deal properties
        Assertions.assertEquals("12345", deal.getDealId());
        Assertions.assertEquals("EUR", deal.getFromCurrencyISO());
        Assertions.assertEquals("USD", deal.getToCurrencyISO());
        Assertions.assertNotNull(deal.getDealTimestamp());
        Assertions.assertEquals(100, deal.getDealAmount());
    }
}
