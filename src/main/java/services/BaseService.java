package services;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.RequestSpecification;
import utils.Config;

import static io.restassured.RestAssured.given;

public class BaseService {

    private static String token = Config.getProperty("api.validToken");

    // Method to set the token
    public static void setToken(String newToken) {
        token = newToken;
    }
    protected static RequestSpecification defaultRequestSpecification() {
        return restAssured().given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token);
    }

    protected static RequestSpecification restAssured() {
        System.out.println("Baseurl - "+Config.getProperty("api.baseurl"));
        RestAssured.baseURI = Config.getProperty("api.baseurl");
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.urlEncodingEnabled = false;

        return given()
                .config(RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));
    }
}
