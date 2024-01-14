package tests;

import dto.LoginView;
import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import tests.helpers.ApiBase;
import tests.helpers.DeleteAPI;
import tests.helpers.UserAPI;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateUserTest extends ApiBase {
    DeleteAPI deleteAPI = new DeleteAPI();
    UserAPI userAPI = new UserAPI();
    Faker faker = new Faker();
    String validPassword = "Rosa35B!";
    @Test
    public void successfulCreateUser() {
        String validUserName = faker.name().username();
        LoginView requestBody = LoginView.builder()
                .userName(validUserName)
                .password(validPassword).
                build();
        JsonPath responseBody= postRequest("/Account/v1/User", 201, requestBody).jsonPath();
        String responseUserName = responseBody.getString("username");
        assertEquals(validUserName, responseUserName, "UserName from request does NOT correspond to UserName from response");
        String userId = responseBody.getString("userID");
        String token = userAPI.generateToken(validUserName, validPassword);
        deleteAPI.deleteAccount(userId, token);
    }



    @Test
    public void emptyUserNameCreateUser(){
        String expectedErrorMessage = "UserName and Password required.";
        LoginView requestBody = LoginView.builder()
                .userName("")
                .password(validPassword).
                build();
        String actualErrorMessage = postRequest("/Account/v1/User", 400, requestBody)
                .jsonPath().getString("message");
        assertEquals(expectedErrorMessage, actualErrorMessage,
                "Expected error message does NOT correspond to Actual Error message");
    }

    @Test
    public void emptyPasswordCreateUser(){
        String expectedErrorMessage = "UserName and Password required.";
        LoginView requestBody = LoginView.builder()
                .userName(validPassword)
                .password("").
                build();
        String actualErrorMessage = postRequest("/Account/v1/User", 400, requestBody)
                .jsonPath().getString("message");
        assertEquals(expectedErrorMessage, actualErrorMessage,
                "Expected error message does NOT correspond to Actual Error message");
    }
}

