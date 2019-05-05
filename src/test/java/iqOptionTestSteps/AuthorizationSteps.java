package iqOptionTestSteps;

import Utils.HttpClient;
import Utils.RequestType;
import Utils.TestContext;
import cucumber.api.java.en.And;

import static Utils.Requests.AUTH_URL;
import static Utils.Requests.Authorization.AUTHORIZATION_URI;

public class AuthorizationSteps {

    private final TestContext testContext;
    private final HttpClient httpClient;

    public AuthorizationSteps(TestContext testContext, HttpClient httpClient) {
        this.testContext = testContext;
        this.httpClient = httpClient;
    }

    @And("send auth request")
    public void sendRegistrationRequest(String request) {
        testContext.setResponse(httpClient.sendRequest(RequestType.POST, AUTH_URL, AUTHORIZATION_URI, request));
    }
}