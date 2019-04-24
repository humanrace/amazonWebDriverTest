package amazonTestSteps;

import Utils.*;
import cucumber.api.java.en.And;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiConsumer;

import static Utils.JSONUtils.isJSON;
import static Utils.Requests.Registration.REGISTRATION_URL;

/**
 * Created by PavelSnowMan on 23.04.2019.
 */
public class RegistrationSteps {

    public HttpClient httpClient = new HttpClient();

    private static final Logger logger = LoggerFactory.getLogger(RegistrationSteps.class);

    @And("send registration request")
    public void sendRegistrationRequest(String request) {
        httpClient.sendRequest(RequestType.POST, REGISTRATION_URL, request);
    }

    @And("check response ([^\"]*)")
    public void checkRegistrationRequest(int responseCode,String response) {

        checkResponse(responseCode, response, Assertions::assertJSONEqualsStrictOrder);
    }

    private void checkResponse(int httpStatus, String expectedJsonString, BiConsumer<String, String> assertion) {
        Assertions.assertResponseCodeIs(testContext.getResponse(), httpStatus);
        logger.info("Comparing response bodies");
        if (isJSON(expectedJsonString)) {
            assertion.accept(expectedJsonString, testContext.getResponseBody());
        } else {
            Assert.assertEquals(expectedJsonString, testContext.getResponseBody());
        }
    }
}
