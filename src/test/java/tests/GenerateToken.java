package tests;

import dto.LoginView;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import tests.helpers.ApiBase;
import tests.helpers.UserAPI;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenerateToken extends ApiBase {
    UserAPI userAPI;
    Faker faker = new Faker();
    String password = "Rosa35B!";
    String endpoint = "/Account/v1/GenerateToken";

    @Test
    public void successfulGenerateToken(){
        String userName = faker.name().username();
        userAPI = new UserAPI();
        userAPI.createUser(userName, password);
        LoginView userRequestBody = LoginView.builder()
                .userName(userName).
                password(password).build();
        String resultFromResponse = postRequest(endpoint, 200, userRequestBody).jsonPath().getString("result");
        assertEquals("User authorized successfully.", resultFromResponse,
                "Expected result does NOT correspond to from response");
    }

    @Test
    public void invalidPasswordGenerateToken(){
        String userName = faker.name().username();
        userAPI = new UserAPI();
        userAPI.createUser(userName, password);
        LoginView userRequestBody = LoginView.builder()
                .userName(userName).
                password("987Aqua654#")
                .build();
        String resultFromResponse = postRequest(endpoint, 200, userRequestBody).jsonPath().getString("result");
        assertEquals("User authorization failed.", resultFromResponse,
                "Expected result does NOT correspond to from response");
    }

    @Test
    public void emptyPasswordGenerateToken(){
        String userName = faker.name().username();
        userAPI = new UserAPI();
        userAPI.createUser(userName, password);
        LoginView userRequestBody = LoginView.builder()
                .userName(userName).
                password("").build();
        String resultFromResponse = postRequest(endpoint, 400, userRequestBody).jsonPath().getString("message");
        assertEquals("UserName and Password required.", resultFromResponse,
                "Expected result does NOT correspond to from response");
    }

    @Test
    public void emptyUserNameGenerateToken(){
        String userName = faker.name().username();
        userAPI = new UserAPI();
        userAPI.createUser(userName, password);
        LoginView userRequestBody = LoginView.builder()
                .userName("").
                password(password).build();
        String resultFromResponse = postRequest(endpoint, 400, userRequestBody).jsonPath().getString("message");
        assertEquals("UserName and Password required.", resultFromResponse,
                "Expected result does NOT correspond to from response");
    }


}

