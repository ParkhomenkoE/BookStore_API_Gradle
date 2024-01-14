package tests;

import dto.LoginView;
import org.junit.jupiter.api.Test;
import tests.helpers.ApiBase;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest extends ApiBase {

    @Test
    public void successfulLogin(){
        LoginView requestBody = LoginView.builder()
                .userName("Rosa")
                .password("Rosa35B!")
                .build();
        String userName = postRequest("/Account/v1/Login", 200, requestBody).jsonPath().getString("username");
        assertEquals("Rosa", userName, "Expected Username does NOT correspond to actual");
    }
}
