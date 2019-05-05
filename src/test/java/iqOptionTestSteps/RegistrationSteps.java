package iqOptionTestSteps;

import Utils.*;
import cucumber.api.java.en.And;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static Utils.Requests.BASE_URL;
import static Utils.Requests.Registration.REGISTRATION_URL;

public class RegistrationSteps {

    private final TestContext testContext;
    private final HttpClient httpClient;

    public RegistrationSteps(TestContext testContext, HttpClient httpClient) {
        this.testContext = testContext;
        this.httpClient = httpClient;
    }

    private static final Logger logger = LoggerFactory.getLogger(RegistrationSteps.class);

    @And("send registration request")
    public void sendRegistrationRequest(List<Map<String, String>> dataList) {
        testContext.setResponse(httpClient.sendMultipartRequest(BASE_URL, REGISTRATION_URL, dataList));
    }

    @And("check response ([^\"]*)")
    public void checkRegistrationRequest(int responseCodeExpected, String responseExpected) throws JSONException {
        checkResponse(responseCodeExpected, responseExpected);
    }

    private void checkResponse(int httpStatus, String expectedJson) throws JSONException {
        Assertions.assertResponseCodeIs(testContext.getResponse(), httpStatus);
        logger.info("Comparing response bodies..: \n Actual:{}", testContext.getResponseBody());
        if (!expectedJson.contains("REGEX")) {
            Assertions.assertJsonEqualsStrictMode(expectedJson, testContext.getResponseBody());
        } else
            Assertions.assertJsonEqualsWithRegexFields(expectedJson, testContext.getResponseBody());
    }
}