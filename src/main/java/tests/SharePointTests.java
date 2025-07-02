package tests;

import com.microsoft.graph.models.DriveItem;
import config.ConfigManager;
import org.testng.annotations.Test;

public class SharePointTests extends BaseTest {
    @Test
    public void verifySharePointDocumentUpload() {
        // Navigate to SharePoint site
        driver.get(ConfigManager.getSharePointSiteUrl());

        // Example: Upload a document to SharePoint using Graph API
        DriveItem driveItem = graphClient.sites(ConfigManager.getProperty("sharepoint.siteId"))
                .drives(ConfigManager.getProperty("sharepoint.driveId"))
                .root()
                .children()
                .buildRequest()
                .post(new DriveItem() {{
                    name = "test_document.txt";
                    file = new com.microsoft.graph.models.File();
                }});

        // Verify the document was uploaded
        assert driveItem.id != null : "Document upload failed";
    }
}