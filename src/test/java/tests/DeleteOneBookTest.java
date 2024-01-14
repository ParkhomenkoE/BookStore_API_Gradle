package tests;

import dto.DeleteOneBook;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;
import tests.helpers.ApiBase;
import tests.helpers.BookAPI;
import tests.helpers.DeleteAPI;
import tests.helpers.UserAPI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DeleteOneBookTest extends ApiBase {
    ApiBase apiBaseToken;
    UserAPI userApi;
    BookAPI bookApi;
    DeleteAPI deleteApi;
    Faker faker = new Faker();
    String password = "Rosa35B!";
    String endpoint = "/BookStore/v1/Book";

    @Test
    public void deleteOneBookTest(){
        String userName = faker.name().username();
        userApi = new UserAPI();
        String userId = userApi.getUserIdCreateUser(userName, password);
        String token = userApi.generateToken(userName, password);
        deleteApi = new DeleteAPI();
        deleteApi.deleteAllBooks(userId, token);
        bookApi = new BookAPI();
        String isbn = bookApi.getISBN(5);
        bookApi.addOneBook(isbn, userId, token);
        DeleteOneBook request = DeleteOneBook.builder()
                .isbn(isbn)
                .userId(userId)
                .build();
        apiBaseToken = new ApiBase(token);
        String responseBody = apiBaseToken.deleteRequest(endpoint, 204, request).getBody().asString();
        assertNotEquals("", responseBody, "The response body is empty.");
    }

    @Test
    public void deleteOneBookUnauthorizedUserTest(){
        String userName = faker.name().username();
        userApi = new UserAPI();
        String userId = userApi.getUserIdCreateUser(userName, password);
        String token = userApi.generateToken(userName, "11111");
        bookApi = new BookAPI();
        String isbn = bookApi.getISBN(5);
        DeleteOneBook request = DeleteOneBook.builder()
                .isbn(isbn)
                .userId(userId)
                .build();
        apiBaseToken = new ApiBase(token);
        String responseMessage= apiBaseToken.deleteRequest(endpoint, 401, request).jsonPath().getString("message");
        assertEquals("User not authorized!", responseMessage, "Expected message does NOT correspond to from response");
    }

}
