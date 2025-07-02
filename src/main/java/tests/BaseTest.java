package tests;

import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.requests.GraphServiceClient;
import config.ConfigManager;
import okhttp3.Request;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utilities.AITestAnalyzer;

import java.util.Collections;


public class BaseTest
{
    protected WebDriver driver;
    protected GraphServiceClient<Request> graphClient;

    @BeforeMethod
    public void setUp() {
        // Initialize WebDriver
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();

        // Initialize Microsoft Graph Client for Azure/Office 365 API interactions
        TokenCredentialAuthProvider authProvider = new TokenCredentialAuthProvider(
                Collections.singletonList("https://graph.microsoft.com/v1/.default"),
                new ClientSecretCredentialBuilder()
                        .tenantId(ConfigManager.getAzureTenantId())
                        .clientId(ConfigManager.getAzureClientId())
                        .clientSecret(ConfigManager.getAzureClientSecret())
                        .build()
        );

        graphClient = GraphServiceClient.builder()
                .authenticationProvider(authProvider)
                .buildClient();
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        trackTestResult(result);
        stopDriver();
    }

    public void trackTestResult(ITestResult result) {
        // Record test execution for flakiness detection
        AITestAnalyzer.recordTestExecution(
                result.getName(),
                result.getStatus() == ITestResult.SUCCESS
        );

        // Generate report after test suite
        if (result.getMethod().isAfterClassConfiguration()) {
            AITestAnalyzer.generateFlakinessReport();
        }
    }
    public void stopDriver() {
        if (driver != null) {
            driver.quit();
        }
    }



}
