package tests;

import com.github.javafaker.Faker;
import dto.LoginView;
import org.testng.annotations.Test;
import tests.helpers.ApiBase;
import tests.helpers.UserAPI;

import static org.testng.AssertJUnit.assertEquals;

public class AuthorizationTest extends ApiBase {
    UserAPI userApi;
    Faker faker = new Faker();
    String password = " Rosa35B!";
    String endPont = "/Account/v1/Authorized";

    @Test
    public void successfulAuthorization() {
        String userName = faker.name().username();
        userApi = new UserAPI();
        userApi.createUser(userName, password);
        userApi.generateToken(userName, password);
        LoginView requestBody = LoginView.builder()
                .userName(userName)
                .password(password).
                build();
        String response = postRequest(endPont, 200, requestBody).asString();
        assertEquals("true", response);
    }

    @Test
    public void authorizationWithoutCreatedUser(){
        String userName = faker.name().username();
        LoginView requestBody = LoginView.builder()
                .userName(userName)
                .password(password).
                build();
        String responseMessage = postRequest(endPont, 404, requestBody).jsonPath().getString("message");
        assertEquals("User not found!", responseMessage, "Expected message does NOT correspond to from response");
    }

    @Test
    public void invalidPasswordAuthorization(){
        String userName = faker.name().username();
        userApi = new UserAPI();
        userApi.createUser(userName, password);
        LoginView requestBody = LoginView.builder()
                .userName(userName)
                .password("qqw234!!!").
                build();
        String responseMessage = postRequest(endPont, 404, requestBody).jsonPath().getString("message");
        assertEquals("User not found!", responseMessage, "Expected message does NOT correspond to from response");
    }

    @Test
    public void invalidUserNameAuthorization(){
        String userName = faker.name().username();
        userApi = new UserAPI();
        userApi.createUser(userName, password);
        LoginView requestBody = LoginView.builder()
                .userName("April")
                .password(password).
                build();
        String responseMessage = postRequest(endPont, 404, requestBody).jsonPath().getString("message");
        assertEquals("User not found!", responseMessage, "Expected message does NOT correspond to from response");
    }

    @Test
    public void emptyPasswordAuthorization(){
        String userName = faker.name().username();
        userApi = new UserAPI();
        userApi.createUser(userName, password);
        LoginView requestBody = LoginView.builder()
                .userName(userName)
                .password("").
                build();
        String responseMessage = postRequest(endPont, 400, requestBody).jsonPath().getString("message");
        assertEquals("UserName and Password required.", responseMessage,
                "Expected message does NOT correspond to from response");
    }

    @Test
    public void emptyUserNameAuthorization(){
        String userName = faker.name().username();
        userApi = new UserAPI();
        userApi.createUser(userName, password);
        LoginView requestBody = LoginView.builder()
                .userName("")
                .password(password).
                build();
        String responseMessage = postRequest(endPont, 400, requestBody).jsonPath().getString("message");
        assertEquals("UserName and Password required.", responseMessage,
                "Expected message does NOT correspond to from response");
    }

}


