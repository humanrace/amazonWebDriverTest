package Utils;

import com.google.common.collect.Maps;
import cucumber.api.Scenario;
import java.util.Map;

public class TestContext {

    private HttpResponseDecorator response;

    // Map for storing parameters used in tests.
    // This map can be updated during test execution.
    private Map<String, Object> params = Maps.newHashMap();

    private Scenario scenario;

    public void clear() {
        params.clear();
        response = null;
        scenario = null;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public String getResponseBody() {
        return response.getBody();
    }

    public HttpResponseDecorator getResponse() {
        return response;
    }

    public void setResponse(HttpResponseDecorator response) {
        this.response = response;
    }
}