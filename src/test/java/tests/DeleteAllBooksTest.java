package tests;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import tests.helpers.ApiBase;
import tests.helpers.BookAPI;
import tests.helpers.UserAPI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DeleteAllBooksTest extends ApiBase {
    ApiBase apiBaseToken;
    UserAPI userApi;
    BookAPI bookApi;
    Faker faker = new Faker();
    String password = "Rosa35B!";
    String endpoint = "/BookStore/v1/Books";

    @Test
    public void deleteAllBooksTest() {
        String userName = faker.name().username();
        userApi = new UserAPI();
        String userId = userApi.getUserIdCreateUser(userName, password);
        String token = userApi.generateToken(userName, password);
        bookApi = new BookAPI();
        bookApi.addListOfSixBooks(userId,token);
        apiBaseToken = new ApiBase(token);
        String responseBody = apiBaseToken.deleteRequestWithQuery(endpoint, 204, userId).getBody().asString();
        assertNotEquals("", responseBody, "The response body is empty.");
    }

    @Test
    public void deleteAllBooksTestWithoutToken() {
        String userName = faker.name().username();
        userApi = new UserAPI();
        String userId = userApi.getUserIdCreateUser(userName, password);
        String responseMessage = deleteRequestWithQuery(endpoint, 401, userId).jsonPath().getString("message");
        assertEquals("User not authorized!", responseMessage, "Expected message does NOT correspond to from response");
    }
}

