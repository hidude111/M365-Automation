package config;

import com.azure.core.credential.TokenCredential;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager
{
    private static Properties prop = new Properties();

    static {
        try {
            prop.load(new FileInputStream("config.properties"));

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }

    // Azure-specific configurations
    public static String getAzureTenantId() {
        return getProperty("azure.tenantId");
    }

    public static String getAzureClientId() {
        return getProperty("azure.clientId");
    }

    public static String getAzureClientSecret() {
        return getProperty("azure.clientSecret");
    }

    // SharePoint configurations
    public static String getSharePointSiteUrl() {
        return getProperty("sharepoint.siteUrl");
    }

    // Power Platform configurations
    public static String getPowerAppsUrl() {
        return getProperty("powerplatform.appsUrl");
    }

    public static String getAzureAIEndpoint() { return getProperty("ai.azure.endpoint"); }

    public static String getAzureAIKey() { return getProperty("ai.azure.key"); }

    public static Boolean getEnabled() {
        return Boolean.parseBoolean(ConfigManager.getProperty("ai.enabled"));
    }
}
