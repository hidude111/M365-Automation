package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class SharePointLibraryPage {
    WebDriver driver;
    WebDriverWait wait;

    public SharePointLibraryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void clickUploadButton() {
        try {
            WebElement uploadBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@data-automationid,'Upload') or contains(@aria-label,'Upload')]")));
            uploadBtn.click();
        } catch (TimeoutException e) {
            WebElement fallbackUpload = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Upload')]")));
            fallbackUpload.click();
        }
    }

    public void uploadFile(String filePath) {
        WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[@type='file']")));
        fileInput.sendKeys(filePath);
    }

    public boolean isFileUploaded(String fileName) {
        try {
            WebElement uploaded = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[contains(text(),'" + fileName + "')]")));
            return uploaded.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
