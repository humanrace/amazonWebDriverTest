package iqOptionTestSteps;

import Utils.TestContext;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import org.junit.Assume;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static urlData.Requests.BASE_WEB_URL;

public class CucumberHooks {

    private static final String SPLITTER = "=====================";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final TestContext testContext;

    public CucumberHooks(TestContext testContext) {
        this.testContext = testContext;
    }

    AuthorizationWebSteps webTestSteps = new AuthorizationWebSteps();

    @Before()
    public void setUp(Scenario scenario) {
        testContext.clear();
        logger.info("{} SCENARIO '{}' {}", SPLITTER, scenario.getName(), SPLITTER);
        testContext.setScenario(scenario);
    }

    @Before("@skipScenario")
    public void skipScenario(Scenario scenario) {
        System.out.println("SKIP SCENARIO: " + scenario.getName());
        Assume.assumeTrue(false);
    }

    @Before("@web")
    public void beforeWebScenario(Scenario scenario) throws Exception {
        System.out.println("SKIP SCENARIO: " + scenario.getName());
        Assume.assumeTrue(true);
        webTestSteps.openSite(BASE_WEB_URL);
    }
}