package tests;

import config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SharePointAPITests {

    @Test
    public void verifyFileExists() {
        String accessToken = "YOUR_ACCESS_TOKEN"; // Get this from Azure Identity

        RestAssured.baseURI = ConfigManager.getProperty("sharepoint.url");

        given()
                .auth().oauth2(accessToken)
                .accept(ContentType.JSON)
                .when()
                .get("/sites/QA_Team/_api/web/GetFolderByServerRelativeUrl('/sites/QA_Team/Shared Documents')/Files('QA_Automation_Doc.pdf')")
                .then()
                .statusCode(200)
                .body("d.Name", equalTo("QA_Automation_Doc.pdf"));
    }
}
