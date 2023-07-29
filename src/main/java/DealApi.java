import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Spark;
import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.List;


public class DealApi {
    // Regex pattern to validate only numbers are used
    private static final Pattern DEAL_ID_PATTERN = Pattern.compile("\\d+");
    // sample list of valid ISO currencey codes
    private static final List<String> validCurrencyCodes = Arrays.asList(
            "USD", "EUR","JOD","SAR", "GBP", "JPY", "CAD", "AUD", "CHF", "CNY", "INR", "SGD", "AED", "NZD"// Add more valid codes as needed
            // ...
    );

    public static void main(String[] args) {

        Spark.port(8080); // Set the port for your API
        // Define the API endpoint to receive deals data
        Spark.post("/deals", "application/json", DealApi::handleDealsRequest);
    }

    static String handleDealsRequest(Request req, Response res) {
        // Get the JSON request body and deserialize it into a Deal object
        Gson gson = new Gson();
        Deal deal = gson.fromJson(req.body(), Deal.class);



        // Perform basic data validation
        if (deal.getDealId() == null || deal.getDealId().isEmpty() ) {
            res.status(400); // Bad Request status code
            return "Deal ID is required.";
        }

        if (!DEAL_ID_PATTERN.matcher(deal.getDealId()).matches()) {
            res.status(400); // Bad Request status code
            return "Deal ID must contain numbers only.";
        }

        if (dealIdExists(deal.getDealId())) {
            res.status(400); // Bad Request status code
            return "Deal ID already exists.";
        }



        if (deal.getFromCurrencyISO() == null || deal.getFromCurrencyISO().isEmpty()) {
            res.status(400); // Bad Request status code
            return "From Currency ISO Code is required.";
        }

        if (deal.getToCurrencyISO() == null || deal.getToCurrencyISO().isEmpty()) {
            res.status(400); // Bad Request status code
            return "To Currency ISO Code is required.";
        }
        if (!isValidCurrencyCode(deal.getFromCurrencyISO())) {
            res.status(400); // Bad Request status code
            return "Invalid From Currency ISO Code.";
        }
        deal.setFromCurrencyISO(deal.getFromCurrencyISO().toUpperCase());

        if (!isValidCurrencyCode(deal.getToCurrencyISO())) {
            res.status(400); // Bad Request status code
            return "Invalid To Currency ISO Code.";
        }
        deal.setToCurrencyISO(deal.getToCurrencyISO().toUpperCase());


        // If all validations pass, save the deal to the database using the DatabaseManager
        boolean savedSuccessfully = saveDealToDatabase(deal);
        if (savedSuccessfully) {
            res.status(201); // Created status code
            return "Deal received and saved!";
        } else {
            res.status(500); // Internal Server Error status code
            return "Failed to save the deal to the database.";
        }
    }
    private static boolean saveDealToDatabase(Deal deal) {
        // JDBC connection properties
        String jdbcUrl = "jdbc:mysql://localhost:3306/fx_deals_db";
        String jdbcUsername = "root";
        String jdbcPassword = "";

        // SQL query to insert the deal into the database
        String sql = "INSERT INTO deals (deal_id, from_currency_iso, to_currency_iso, deal_amount) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {


            // Set the parameter values for the prepared statement
            preparedStatement.setString(1, deal.getDealId());
            preparedStatement.setString(2, deal.getFromCurrencyISO());
            preparedStatement.setString(3, deal.getToCurrencyISO());
            preparedStatement.setBigDecimal(4, BigDecimal.valueOf(deal.getDealAmount())); // Convert to BigDecimal


            // Execute the insert statement
            int rowsAffected = preparedStatement.executeUpdate();

            // Return true if the deal is successfully saved (rowsAffected > 0)
            return rowsAffected > 0;
        } catch (SQLException e) {
            return false; // Failed to save the deal
        }
    }
    private static boolean dealIdExists(String dealId) {
        // Implement the logic to check if the dealId already exists in the database using JDBC
        // Perform a SELECT query to search for a matching dealId in the database
        // Return true if a record with the dealId exists, false otherwise
        String jdbcUrl = "jdbc:mysql://localhost:3306/fx_deals_db";
        String jdbcUsername = "root";
        String jdbcPassword = "";
        // Example implementation (assuming 'deals' table with 'deal_unique_id' column):
        String sql = "SELECT COUNT(*) FROM deals WHERE deal_id = ?";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, dealId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Error occurred or dealId not found
    }
    private static boolean isValidCurrencyCode(String currencyCode) {
        // check if the ISO code provided is in sample list
        return validCurrencyCodes.contains(currencyCode.toUpperCase());
    }

}
