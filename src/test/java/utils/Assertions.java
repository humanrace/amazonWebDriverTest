package Utils;

import org.json.JSONException;
import org.junit.Assert;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.RegularExpressionValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

import static org.hamcrest.core.Is.is;

/**
 * Contains assertions.
 */
public class Assertions {

    /**
     * Asserts that response status code is equal to expected.
     *
     * @param response       response
     * @param expectedStatus expected response status code
     */
    public static void assertResponseCodeIs(HttpResponseDecorator response, int expectedStatus) {
        Assert.assertThat("Response status code is different from expected", response.getStatusCode(),
                is(expectedStatus));
    }

    /**
     * Asserts that response json is equal to expected.
     *
     * @param expectedJson       expected json request
     * @param actualJsonResponse expected response status code
     */
    public static void assertJsonEqualsStrictMode(String expectedJson, String actualJsonResponse) throws JSONException {
        JSONAssert.assertEquals(expectedJson, actualJsonResponse, JSONCompareMode.STRICT);
    }

    /**
     * Asserts that response status code is equal to expected.
     *
     * @param expectedJson       expected json request
     * @param actualJsonResponse expected response status code
     */
    public static void assertJsonEqualsWithRegexFields(String expectedJson, String actualJsonResponse) throws JSONException {
        JSONAssert.assertEquals(expectedJson, actualJsonResponse, new CustomComparator(JSONCompareMode.LENIENT,
                new Customization("data.ssid", new RegularExpressionValueMatcher("(.*)"))
        ));
    }
}
