package amazonTestSteps;

import cucumber.api.java.en.And;

/**
 * Created by PavelSnowMan on 23.04.2019.
 */
public class RegistrationSteps {

    public HttpSender httpSender = new HttpClient();


    @And("send registration request")
    public void sendRegistrationRequest(String jsonRequest) {

        httpSender.sendRegistrationRequests(jsonRequest);
    }


    @And("check response ([^\"]*)")
    public void checkRegistrationRequest(int responseCode) {


    }
}
