package tests;

import com.github.javafaker.Faker;
import org.junit.Test;
import tests.helpers.ApiBase;
import tests.helpers.UserAPI;
import static org.testng.AssertJUnit.assertEquals;
public class GetUserTest extends ApiBase {
    ApiBase apiBaseToken;
    UserAPI userApi;

    Faker faker = new Faker();
    String password = "Rosa35B!";
    String endpoint = "/Account/v1/User/{UserId}";
    @Test
    public void GetUser() {
        String userName = faker.name().username();
        userApi = new UserAPI();
        String userId = userApi.getUserIdCreateUser(userName, password);
        String token = userApi.generateToken(userName, password);
        apiBaseToken = new ApiBase(token);
        String usernameResponse = apiBaseToken
                .getRequestWithParam(endpoint, 200, "UserId", userId)
                .body().jsonPath().getString("username");
        assertEquals(userName, usernameResponse, "Expected UserName does NOT correspond to UserNameFromResponse");
    }


    @Test
    public void GetUserWithoutToken() {
        String userName = faker.name().username();
        userApi = new UserAPI();
        userApi.getUserIdCreateUser(userName, password);
        String responseMessage= getRequestWithParam(endpoint, 401, "UserId", "5642987")
                .body().jsonPath().getString("message");
        assertEquals("User not authorized!", responseMessage, "Expected message does NOT correspond to response message");
    }
}
