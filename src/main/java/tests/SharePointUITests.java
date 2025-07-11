package tests;

import config.ConfigManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SharePointLibraryPage;

public class SharePointUITests extends BaseTest {

    @Test
    public void uploadDocumentToLibrary() {
        driver.get(ConfigManager.getSharePointUploadUrl());

        //Example: Upload to library page...
        SharePointLibraryPage sharePointLibraryPage = new SharePointLibraryPage(driver);
        sharePointLibraryPage.clickUploadButton();
        sharePointLibraryPage.uploadFile("\"C:/TestFiles/QA_Automation_Doc.pdf\"");


        Assert.assertTrue(sharePointLibraryPage.isFileUploaded("test_document.txt"), "Document upload Passed");


    }

}