import models.request.CreateUserModel;
import org.junit.jupiter.api.Test;
import services.BaseService;
import services.GoRestService;
import utils.Config;
import utils.RandomDataUtil;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.*;

public class CreateUserTests {

    /**
     * Test to validate if the create users api works well
     */
    @Test
    public void Users_CreateUsers_Success(){
        BaseService.setToken(Config.getProperty("api.validToken"));
        String randomName = RandomDataUtil.generateRandomName();
        String randomGender = RandomDataUtil.generateRandomGender();
        String randomEmail = RandomDataUtil.generateRandomEmail();
        String randomStatus = RandomDataUtil.generateRandomStatus();

        CreateUserModel createUserModel = new CreateUserModel(randomName, randomGender, randomEmail, randomStatus);

        GoRestService.createUser(createUserModel)
                .then()
                .statusCode(SC_CREATED)
                .body("data.id", notNullValue())
                .body("data.name", equalTo(createUserModel.getName()))
                .body("data.email", equalTo(createUserModel.getEmail().toLowerCase()))
                .body("data.gender", equalTo(createUserModel.getGender().toLowerCase()))
                .body("data.status", equalTo(createUserModel.getStatus().toLowerCase()));
    }

    /**
     * Negative Test to validate if the api handles the same email
     */
    @Test
    public void Users_CreateUsers_SameEmail_Error(){
        BaseService.setToken(Config.getProperty("api.validToken"));
        String randomName = RandomDataUtil.generateRandomName();
        String randomGender = RandomDataUtil.generateRandomGender();
        String randomEmail = RandomDataUtil.generateRandomEmail();
        String randomStatus = RandomDataUtil.generateRandomStatus();

        CreateUserModel createUserModel = new CreateUserModel(randomName, randomGender, randomEmail, randomStatus);
        GoRestService.createUser(createUserModel);
        GoRestService.createUser(createUserModel)
                .then()
                .statusCode(SC_UNPROCESSABLE_ENTITY)
                .body("data[0].field", equalTo("email"))
                .body("data[0].message", containsString("has already been taken"));
    }

    /**
     * Negative Test to validate if the api handles the invalid email
     */
    @Test
    public void Users_CreateUsers_InvalidEmail_Error(){
        BaseService.setToken(Config.getProperty("api.validToken"));
        String randomName = RandomDataUtil.generateRandomName();
        String randomGender = RandomDataUtil.generateRandomGender();
        String randomEmail = "Invalid Email";
        String randomStatus = RandomDataUtil.generateRandomStatus();

        CreateUserModel createUserModel = new CreateUserModel(randomName, randomGender, randomEmail, randomStatus);
        GoRestService.createUser(createUserModel)
                .then()
                .statusCode(SC_UNPROCESSABLE_ENTITY)
                .body("data[0].field", equalTo("email"))
                .body("data[0].message", containsString("is invalid"));
    }

    /**
     * Negative Test to validate if the api handles the invalid token
     */
    @Test
    public void Users_CreateUsers_InvalidToken_Error() {
        BaseService.setToken(Config.getProperty("api.invalidToken"));
        String randomName = RandomDataUtil.generateRandomName();
        String randomGender = RandomDataUtil.generateRandomGender();
        String randomEmail = RandomDataUtil.generateRandomEmail();
        String randomStatus = RandomDataUtil.generateRandomStatus();

        CreateUserModel createUserModel = new CreateUserModel(randomName, randomGender, randomEmail, randomStatus);
        GoRestService.createUser(createUserModel)
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .body("data.message", containsString("Invalid token"));
    }

    /**
     * Negative Test to validate if the api handles the invalid status
     */
    @Test
    public void Users_CreateUsers_InvalidStatus_Error(){
        BaseService.setToken(Config.getProperty("api.validToken"));
        String randomName = RandomDataUtil.generateRandomName();
        String randomGender = RandomDataUtil.generateRandomGender();
        String randomEmail = RandomDataUtil.generateRandomEmail();
        String randomStatus = "Invalid";

        CreateUserModel createUserModel = new CreateUserModel(randomName, randomGender, randomEmail, randomStatus);
        GoRestService.createUser(createUserModel)
                .then()
                .statusCode(SC_UNPROCESSABLE_ENTITY)
                .body("data[0].field", equalTo("status"))
                .body("data[0].message", containsString("can't be blank"));
    }

    /**
     * Negative Test to validate if the api handles the blank name
     */
    @Test
    public void Users_CreateUsers_BlankName_Error(){
        BaseService.setToken(Config.getProperty("api.validToken"));
        String randomName = "";
        String randomGender = RandomDataUtil.generateRandomGender();
        String randomEmail = RandomDataUtil.generateRandomEmail();
        String randomStatus = RandomDataUtil.generateRandomStatus();

        CreateUserModel createUserModel = new CreateUserModel(randomName, randomGender, randomEmail, randomStatus);
        GoRestService.createUser(createUserModel)
                .then()
                .statusCode(SC_UNPROCESSABLE_ENTITY)
                .body("data[0].field", equalTo("name"))
                .body("data[0].message", containsString("can't be blank"));
    }

    /**
     * Negative Test to validate if the api handles the blank gender
     */
    @Test
    public void Users_CreateUsers_BlankGender_Error(){
        BaseService.setToken(Config.getProperty("api.validToken"));
        String randomName = RandomDataUtil.generateRandomName();
        String randomGender = "";
        String randomEmail = RandomDataUtil.generateRandomEmail();
        String randomStatus = RandomDataUtil.generateRandomStatus();

        CreateUserModel createUserModel = new CreateUserModel(randomName, randomGender, randomEmail, randomStatus);
        GoRestService.createUser(createUserModel)
                .then()
                .statusCode(SC_UNPROCESSABLE_ENTITY)
                .body("data[0].field", equalTo("gender"))
                .body("data[0].message", containsString("can't be blank, can be male of female"));
    }

    /**
     * Negative Test to validate if the api handles the blank email
     */
    @Test
    public void Users_CreateUsers_BlankEmail_Error(){
        BaseService.setToken(Config.getProperty("api.validToken"));
        String randomName = RandomDataUtil.generateRandomName();
        String randomGender = RandomDataUtil.generateRandomGender();
        String randomEmail = "";
        String randomStatus = RandomDataUtil.generateRandomStatus();

        CreateUserModel createUserModel = new CreateUserModel(randomName, randomGender, randomEmail, randomStatus);
        GoRestService.createUser(createUserModel)
                .then()
                .statusCode(SC_UNPROCESSABLE_ENTITY)
                .body("data[0].field", equalTo("email"))
                .body("data[0].message", containsString("can't be blank"));
    }
}