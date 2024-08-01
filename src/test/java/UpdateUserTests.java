import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import models.request.CreateUserModel;
import models.request.UpdateUserModel;
import org.junit.jupiter.api.Test;
import services.BaseService;
import services.GoRestService;
import utils.ApiUtil;
import utils.Config;
import utils.RandomDataUtil;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateUserTests {

    @Test
    public void Users_UpdateUsers_Success() {
        BaseService.setToken(Config.getProperty("api.validToken"));
        Response user = ApiUtil.getUser();
        int id = user.jsonPath().getInt("[0].id");
        String randomName = RandomDataUtil.generateRandomName();
        String randomEmail = RandomDataUtil.generateRandomEmail();
        String randomStatus = RandomDataUtil.generateRandomStatus();

        UpdateUserModel updateUserModel = new UpdateUserModel(randomName, randomEmail, randomStatus);

        // Send update request and validate response
        ValidatableResponse validatableResponse = GoRestService.updateUser(updateUserModel, id)
                .then()
                .statusCode(SC_OK)
                .body("id", notNullValue())
                .body("name", equalTo(updateUserModel.getName()))
                .body("email", equalTo(updateUserModel.getEmail().toLowerCase()))
                .body("status", equalTo(updateUserModel.getStatus().toLowerCase()));

        // Extract response to get the updated user data
        Response response = validatableResponse.extract().response();
        user = ApiUtil.getUser();

        //To check if the updated name sent in patch request is updated on the server/DB by validating the getuser api response
        String getApiName = user.jsonPath().getString("find { it.id == " + response.jsonPath().getInt("id") + " }.name");
        assertEquals(getApiName, randomName, "Names do not match");
    }

    @Test
    public void Users_UpdateUsers_InvalidEmail_Error() {
        BaseService.setToken(Config.getProperty("api.validToken"));
        Response user = ApiUtil.getUser();
        int id = user.jsonPath().getInt("[0].id");
        String randomName = RandomDataUtil.generateRandomName();
        String randomEmail = "Invalid Email";
        String randomStatus = RandomDataUtil.generateRandomStatus();

        UpdateUserModel updateUserModel = new UpdateUserModel(randomName, randomEmail, randomStatus);

        // Send update request and validate response
        ValidatableResponse validatableResponse = GoRestService.updateUser(updateUserModel, id)
                .then()
                .statusCode(SC_UNPROCESSABLE_ENTITY)
                .body("[0].field", equalTo("email"))
                .body("[0].message", containsString("is invalid"));
    }

    @Test
    public void Users_UpdateUsers_InvalidStatus_Error() {
        BaseService.setToken(Config.getProperty("api.validToken"));
        Response user = ApiUtil.getUser();
        int id = user.jsonPath().getInt("[0].id");
        String randomName = RandomDataUtil.generateRandomName();
        String randomEmail = RandomDataUtil.generateRandomEmail();
        String randomStatus = "Invalid status";

        UpdateUserModel updateUserModel = new UpdateUserModel(randomName, randomEmail, randomStatus);

        // Send update request and validate response
        ValidatableResponse validatableResponse = GoRestService.updateUser(updateUserModel, id)
                .then()
                .statusCode(SC_UNPROCESSABLE_ENTITY)
                .body("[0].field", equalTo("status"))
                .body("[0].message", containsString("can't be blank"));
    }

    @Test
    public void Users_UpdateUsers_BlankName_Error() {
        BaseService.setToken(Config.getProperty("api.validToken"));
        Response user = ApiUtil.getUser();
        int id = user.jsonPath().getInt("[0].id");
        String randomName = "";
        String randomEmail = RandomDataUtil.generateRandomEmail();
        String randomStatus = RandomDataUtil.generateRandomStatus();

        UpdateUserModel updateUserModel = new UpdateUserModel(randomName, randomEmail, randomStatus);

        // Send update request and validate response
        ValidatableResponse validatableResponse = GoRestService.updateUser(updateUserModel, id)
                .then()
                .statusCode(SC_UNPROCESSABLE_ENTITY)
                .body("[0].field", equalTo("name"))
                .body("[0].message", containsString("can't be blank"));
    }


    @Test
    public void Users_UpdateUsers_BlankEmail_Error() {
        BaseService.setToken(Config.getProperty("api.validToken"));
        Response user = ApiUtil.getUser();
        int id = user.jsonPath().getInt("[0].id");
        String randomName = RandomDataUtil.generateRandomName();
        String randomEmail = "";
        String randomStatus = RandomDataUtil.generateRandomStatus();

        UpdateUserModel updateUserModel = new UpdateUserModel(randomName, randomEmail, randomStatus);

        // Send update request and validate response
        ValidatableResponse validatableResponse = GoRestService.updateUser(updateUserModel, id)
                .then()
                .statusCode(SC_UNPROCESSABLE_ENTITY)
                .body("[0].field", equalTo("email"))
                .body("[0].message", containsString("can't be blank"));
    }

}