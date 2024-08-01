package utils;

import io.restassured.response.Response;
import services.BaseService;
import services.GoRestService;

public class ApiUtil {
    public static Response getUser(){
        BaseService.setToken(Config.getProperty("api.validToken"));
        Response response = GoRestService.getUser()
                .then()
                .statusCode(200)
                .extract()
                .response();
        int firstUserId = response.jsonPath().getInt("[0].id");
        System.out.println("The ID of the first user is: " + firstUserId);
        return response;
    }
}
