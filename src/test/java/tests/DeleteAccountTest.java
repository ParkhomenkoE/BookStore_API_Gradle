package tests;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import tests.helpers.ApiBase;
import tests.helpers.UserAPI;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteAccountTest extends ApiBase {
    ApiBase apiBase;
    UserAPI userApi;
    Faker faker = new Faker();
    String password = "Rosa35B!";
    String endPoint = "/Account/v1/User/{UserId}";

    @Test
    public void deleteAccount() {
        String userName = faker.name().username();
        userApi = new UserAPI();
        String userId = userApi.getUserIdCreateUser(userName, password);
        String token = userApi.generateToken(userName, password);
        apiBase = new ApiBase(token);
        apiBase.deleteRequestWithParam(endPoint, 200, "UserId", userId);
    }

    @Test
    public void invalidUserIdDeleteAccount() {
        String userName = faker.name().username();
        userApi = new UserAPI();
        userApi.createUser(userName, password);
        String token = userApi.generateToken(userName, password);
        apiBase = new ApiBase(token);
        String errorMessage = apiBase.deleteRequestWithParam(endPoint, 404, "UserId", "47e350e6")
                .jsonPath().getString("message");
        assertEquals("User Id not correct!", errorMessage,
                "Expected Error message does NOT correspond to from response");
    }


    @Test
    public void withoutTokenDeleteAccount() {
        String userName = faker.name().username();
        userApi = new UserAPI();
        String userId = userApi.getUserIdCreateUser(userName, password);
        String responseMessage = deleteRequestWithParam(endPoint, 401, "UserId", userId)
                .jsonPath().getString("message");
        assertEquals("User not authorized!", responseMessage,
                "Expected Error message does NOT correspond to from response");
    }

}
