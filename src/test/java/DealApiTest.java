import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;

import java.util.UUID;

public class DealApiTest {

    private Request mockRequest;
    private Response mockResponse;

    @BeforeEach
    public void setUp() {
        // Create mock Request and Response objects before each test
        mockRequest = new TestRequest();
        mockResponse = new TestResponse();
    }

    @Test
    public void testHandleDealsRequest_ValidDeal() {
        // Create a JSON representation of a valid deal
        long currentTimestamp = System.currentTimeMillis();
        String dealid= String.valueOf(currentTimestamp);
        String jsonBody = "{\"dealId\": \"" + dealid + "\", \"fromCurrencyISO\": \"USD\", \"toCurrencyISO\": \"EUR\", \"dealAmount\": 100}";

        // Set the JSON body in the mock Request object
        ((TestRequest) mockRequest).setBody(jsonBody);

        // Call the handleDealsRequest method and capture the response
        String result = DealApi.handleDealsRequest(mockRequest, mockResponse);

        // Assert that the response is as expected (e.g., "Deal received and saved!")
        Assertions.assertEquals("Deal received and saved!", result);

        // Assert that the response status code is 201 (Created)
        Assertions.assertEquals(201, mockResponse.status());
    }

    @Test
    public void testHandleDealsRequest_InvalidDeal() {
        //ID already exist
        // Create a JSON representation of an invalid deal (missing fields)
        String jsonBody = "{\"dealId\": \"12345\", \"fromCurrencyISO\": \"USD\"}";

        // Set the JSON body in the mock Request object
        ((TestRequest) mockRequest).setBody(jsonBody);

        // Call the handleDealsRequest method and capture the response
        String result = DealApi.handleDealsRequest(mockRequest, mockResponse);

        // Assert that the response is as expected (e.g., error message)
        Assertions.assertEquals("Deal ID already exists.", result);

        // Assert that the response status code is 400 (Bad Request)
        Assertions.assertEquals(400, mockResponse.status());
    }



    @Test
    public void testHandleDealsRequest_InvalidDeal2() {
        //ID is null
        // Create a JSON representation of an invalid deal (missing fields)
        String jsonBody = "{ \"fromCurrencyISO\": \"USD\"}";

        // Set the JSON body in the mock Request object
        ((TestRequest) mockRequest).setBody(jsonBody);

        // Call the handleDealsRequest method and capture the response
        String result = DealApi.handleDealsRequest(mockRequest, mockResponse);

        // Assert that the response is as expected (e.g., error message)
        Assertions.assertEquals("Deal ID is required.", result);

        // Assert that the response status code is 400 (Bad Request)
        Assertions.assertEquals(400, mockResponse.status());
    }

    @Test
    public void testHandleDealsRequest_InvalidDeal3() {
        //ID is not numbers
        // Create a JSON representation of an invalid deal (missing fields)
        String jsonBody = "{\"dealId\": \"12a345\", \"fromCurrencyISO\": \"USD\"}";


        // Set the JSON body in the mock Request object
        ((TestRequest) mockRequest).setBody(jsonBody);

        // Call the handleDealsRequest method and capture the response
        String result = DealApi.handleDealsRequest(mockRequest, mockResponse);

        // Assert that the response is as expected (e.g., error message)
        Assertions.assertEquals("Deal ID must contain numbers only.", result);

        // Assert that the response status code is 400 (Bad Request)
        Assertions.assertEquals(400, mockResponse.status());
    }

    @Test
    public void testHandleDealsRequest_InvalidDeal4() {
        //From Currency ISO Code is required.
        // Create a JSON representation of an invalid deal (missing fields)
        String jsonBody = "{\"dealId\": \"1222345\"}";


        // Set the JSON body in the mock Request object
        ((TestRequest) mockRequest).setBody(jsonBody);

        // Call the handleDealsRequest method and capture the response
        String result = DealApi.handleDealsRequest(mockRequest, mockResponse);

        // Assert that the response is as expected (e.g., error message)
        Assertions.assertEquals("From Currency ISO Code is required.", result);

        // Assert that the response status code is 400 (Bad Request)
        Assertions.assertEquals(400, mockResponse.status());
    }

    @Test
    public void testHandleDealsRequest_InvalidDeal5() {
        //fromCurrencyISO is valid
        // Create a JSON representation of an invalid deal (missing fields)
        String jsonBody = "{\"dealId\": \"12222345\", \"fromCurrencyISO\": \"tre\",\"toCurrencyISO\":\"USD\"}";


        // Set the JSON body in the mock Request object
        ((TestRequest) mockRequest).setBody(jsonBody);

        // Call the handleDealsRequest method and capture the response
        String result = DealApi.handleDealsRequest(mockRequest, mockResponse);

        // Assert that the response is as expected (e.g., error message)
        Assertions.assertEquals("Invalid From Currency ISO Code.", result);

        // Assert that the response status code is 400 (Bad Request)
        Assertions.assertEquals(400, mockResponse.status());
    }

    @Test
    public void testHandleDealsRequest_InvalidDeal6() {
        //toCurrencyISO is required
        // Create a JSON representation of an invalid deal (missing fields)
        String jsonBody = "{\"dealId\": \"12222345\", \"fromCurrencyISO\": \"tre\"}";


        // Set the JSON body in the mock Request object
        ((TestRequest) mockRequest).setBody(jsonBody);

        // Call the handleDealsRequest method and capture the response
        String result = DealApi.handleDealsRequest(mockRequest, mockResponse);

        // Assert that the response is as expected (e.g., error message)
        Assertions.assertEquals("To Currency ISO Code is required.", result);

        // Assert that the response status code is 400 (Bad Request)
        Assertions.assertEquals(400, mockResponse.status());
    }

    @Test
    public void testHandleDealsRequest_InvalidDeal7() {
        //fromCurrencyISO is valid
        // Create a JSON representation of an invalid deal (missing fields)
        String jsonBody = "{\"dealId\": \"12222345\", \"fromCurrencyISO\": \"USD\",\"toCurrencyISO\":\"TRE\"}";


        // Set the JSON body in the mock Request object
        ((TestRequest) mockRequest).setBody(jsonBody);

        // Call the handleDealsRequest method and capture the response
        String result = DealApi.handleDealsRequest(mockRequest, mockResponse);

        // Assert that the response is as expected (e.g., error message)
        Assertions.assertEquals("Invalid To Currency ISO Code.", result);

        // Assert that the response status code is 400 (Bad Request)
        Assertions.assertEquals(400, mockResponse.status());
    }


    // Helper TestRequest class to set the request body manually
    private static class TestRequest extends Request {
        private String body;

        public void setBody(String body) {
            this.body = body;
        }

        @Override
        public String body() {
            return body;
        }
    }

    // Helper TestResponse class to verify the response status
    private static class TestResponse extends Response {
        private int status;

        @Override
        public void status(int statusCode) {
            this.status = statusCode;
        }

        @Override
        public int status() {
            return status;
        }
    }
}
