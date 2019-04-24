package Utils;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class CucumberHooks {

    private static final String SPLITTER = "=====================";

    private static final String FOLDER = "./target/outPut/";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final TestContext testContext;

    public CucumberHooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before()
    public void setUp(Scenario scenario) {
        testContext.clear();
        logger.info("{} SCENARIO '{}' {}", SPLITTER, scenario.getName(), SPLITTER);
        Collection<String> tags = scenario.getSourceTagNames();
        testContext.setContext(restGatewayClient.getContext());
        testContext.setScenario(scenario);
    }

    @After()
    public void clearSftp() {
        testContext.clear();
    }
}