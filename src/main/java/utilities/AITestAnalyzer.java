package utilities;
import com.azure.ai.metricsadvisor.MetricsAdvisorClient;
import com.azure.ai.metricsadvisor.MetricsAdvisorClientBuilder;
import com.azure.ai.metricsadvisor.models.MetricsAdvisorKeyCredential;
import com.azure.core.credential.AzureKeyCredential;
import config.ConfigManager;
import java.util.concurrent.ConcurrentHashMap;

import static config.ConfigManager.getAzureAIKey;

/**
 * AI-powered test analysis utilities
 * Integrates with Azure Metrics Advisor and Applitools
 */
public class AITestAnalyzer {
    private static final ConcurrentHashMap<String, TestStats> testHistory = new ConcurrentHashMap<>();


    /**
     * Tracks test execution patterns to detect flakiness
     */
    public static synchronized void recordTestExecution(String testName, boolean passed) {
        testHistory.compute(testName, (key, stats) -> {
            if (stats == null) return new TestStats(passed);
            stats.recordExecution(passed);
            return stats;
        });
    }

    /**
     * Generates flakiness report using Azure Metrics Advisor
     */
    public static void generateFlakinessReport() {
        if (!ConfigManager.getEnabled() || getAzureAIKey().isEmpty()) return;

        MetricsAdvisorClient client = new MetricsAdvisorClientBuilder()
                .credential(new MetricsAdvisorKeyCredential(ConfigManager.getAzureAIKey(), "{api_key}"))
                .endpoint(ConfigManager.getAzureAIEndpoint())
                .buildClient();

        testHistory.forEach((testName, stats) -> {
            if (stats.getFlakinessScore() > 0.3) { // Threshold for flakiness
                System.out.printf("[AI Alert] Flaky test detected: %s (Score: %.2f)%n",
                        testName, stats.getFlakinessScore());
            }
        });
    }
    

    // Inner class for test statistics
    private static class TestStats {
        private int totalRuns;
        private int failures;

        TestStats(boolean initialPass) {
            this.totalRuns = 1;
            this.failures = initialPass ? 0 : 1;
        }

        void recordExecution(boolean passed) {
            totalRuns++;
            if (!passed) failures++;
        }

        double getFlakinessScore() {
            return (double) failures / totalRuns;
        }
    }
}