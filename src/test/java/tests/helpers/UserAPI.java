package tests.helpers;

import dto.LoginView;

public class UserAPI extends ApiBase {

    ApiBase apiBaseToken;

    public void createUser(String userName, String password) {
        LoginView requestBody = LoginView.builder()
                .userName(userName)
                .password(password).
                build();
        postRequest("/Account/v1/User", 201, requestBody).jsonPath().getString("userID");
    }

    public String getUserIdCreateUser(String userName, String password) {
        LoginView requestBody = LoginView.builder()
                .userName(userName)
                .password(password).
                build();
        String userID = postRequest("/Account/v1/User", 201, requestBody).jsonPath().getString("userID");
        return userID;
    }

    public String generateToken(String username, String password) {
        String endpoint = "/Account/v1/GenerateToken";
        LoginView userRequestBody = LoginView.builder()
                .userName(username).
                password(password).build();
        String token = postRequest(endpoint, 200, userRequestBody).jsonPath().getString("token");
        return token;
    }

    public void authorization(String userName, String password, String token) {
        LoginView requestBody = LoginView.builder()
                .userName(userName)
                .password(password).
                build();
        apiBaseToken = new ApiBase(token);
        apiBaseToken.postRequest("/Account/v1/Authorized", 200, requestBody)
                .asString().equalsIgnoreCase("true");
    }
}


