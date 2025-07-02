package tests;

import config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PowerPlatformTests extends BaseTest {
    @Test
    public void testPowerAutomateFlow() {
        // Navigate to Power Platform
        driver.get(ConfigManager.getPowerAppsUrl());

        // Example: Trigger a Power Automate flow via REST API
        Response response = given()
                .auth().oauth2(getAzureToken())
                .contentType("application/json")
                .body("{ \"input\": \"test data\" }")
                .when()
                .post(ConfigManager.getProperty("powerplatform.flowTriggerUrl"));

        // Verify the flow executed successfully
        assert response.getStatusCode() == 200 : "Flow execution failed";
    }

    private String getAzureToken() {
        // Implementation to get Azure AD token
        // This would use the same authentication as in BaseTest
        return "your_access_token";
    }
}