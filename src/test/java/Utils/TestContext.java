package Utils;

import com.amaiz.backend.client.core.context.AuthInfo;
import com.amaiz.backend.client.core.context.ExecutionContext;
import com.amaiz.backend.client.core.context.SessionInfo;
import com.amaiz.backend.client.core.entity.UserData;
import com.amaiz.backend.client.core.rest.http.HttpHeader;
import com.amaiz.backend.client.core.rest.http.HttpResponseDecorator;
import com.google.common.collect.Maps;
import cucumber.api.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestContext {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Map<String, List<Map<String, String>>> tableTemplates = Maps.newHashMap();
    private Map<String, Map<String, String>> clearingTemplates = Maps.newHashMap();
    private final Map<String, List<Map<String, String>>> globalTableTemplates = Maps.newHashMap();
    private final Map<String, Map<String, String>> globalClearingTemplates = Maps.newHashMap();

    private Map<String, Integer> tablesRecordsCount = Maps.newHashMap();
    private HttpHeader requestHeader;
    private HttpResponseDecorator response;

    // Map for storing parameters used in tests.
    // This map can be updated during test execution.
    private Map<String, Object> params = Maps.newHashMap();

    // Map for storing user's data, key - login
    private Map<String, UserData> usersData = Maps.newHashMap();

    private ExecutionContext context;

    private Scenario scenario;

    public void clear() {
        tableTemplates.clear();
        tablesRecordsCount.clear();
        params.clear();
        requestHeader = null;
        response = null;
        context = null;
        scenario = null;
    }

    public void setContext(ExecutionContext context) {
        this.context = context;
    }

    l

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }


    public Map<String, Object> getParams() {
        return params;
    }

    public Object getParam(String paramName) {
        return params.get(paramName);
    }

    public void addParam(String name, Object value) {
        logger.info("Adding parameter '{}' with value: '{}' to context \n", name, value);
        params.put(name, value);
    }

    public void incrementParam(String name, Object value) {
        logger.info("Incrementing parameter '{}' with value: '{}'\n", name, value);
        if (!(value instanceof Integer)) {
            throw new IllegalArgumentException("Only integer parameters can be incremented");
        }

        Integer finalValue = (Integer) value;
        if (params.containsKey(name)) {
            finalValue = finalValue + (Integer) params.get(name);
        }

        params.put(name, finalValue);
    }

    public boolean containsParam(String name) {
        return params.containsKey(name);
    }

    public HttpHeader getRequestHeader() {
        return requestHeader;
    }

    public void addRequestHeader(String key, String value) {
        requestHeader = new HttpHeader(key, value);
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
