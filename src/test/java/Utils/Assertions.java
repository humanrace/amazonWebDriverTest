package Utils;

import org.junit.Assert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Contains complex assertions.
 */
public class Assertions {

    private static final Logger logger = LoggerFactory.getLogger(Assertions.class);

    private static final String DIFF_FIELDS_FORMAT = "Field is different from expected. Expected: %s\n Actual: %s\n";

    private static final String SPACES = "\\s+";

    private Assertions() {
        // utils class
    }

    /**
     * Asserts that JSON fields are equal. The field can be a plain string or a JSON.
     *
     * @param expected expected field value
     * @param actual   actual field value
     */
    public static void assertJSONFieldEquals(String expected, String actual) {
        if (JSONUtils.isJSON(expected)) {
            assertJSONEqualsStrictOrder(expected, actual);
        } else {
            assertJSONTextFieldEquals(expected, actual);
        }
    }

    /**
     * Asserts that JSONs are equal. JSONCompareMode - STRICT
     *
     * @param expectedJson expected JSON
     * @param actualJson   actual JSON
     */
    public static void assertJSONEqualsStrictOrder(String expectedJson, String actualJson) {
        assertJSONEquals(expectedJson, actualJson, JSONCompareMode.STRICT);
    }

    /**
     * Asserts that JSONs are equal. JSONCompareMode - LENIENT (non-strict array ordering)
     *
     * @param expectedJson expected JSON
     * @param actualJson   actual JSON
     */
    public static void assertJSONEqualsNonStrictOrder(String expectedJson, String actualJson) {
        assertJSONEquals(expectedJson, actualJson, JSONCompareMode.LENIENT);
    }

    /**
     * Asserts that JSONs are equal. JSONCompareMode - STRICT
     *
     * @param expectedJson expected JSON
     * @param actualJson   actual JSON
     * @param compareMode  JSON compare mode
     */
    public static void assertJSONEquals(String expectedJson, String actualJson, JSONCompareMode compareMode) {
        if (actualJson.equals("") || expectedJson.equals("")) {
            Assert.assertEquals("Jsons are not equal", expectedJson, actualJson);
            return;
        }
        JSONCustomComparator comparator = new JSONCustomComparator(compareMode);
        JSONAssert.assertEquals(String.format(
                DIFF_FIELDS_FORMAT, expectedJson, JSONUtils.beautifyIfJSON(actualJson)),
                expectedJson, actualJson, comparator);
    }

    /**
     * Asserts that JSON text fields are equal.
     * Text field may contain regexp, in this case asserts that
     * actual field value matches the pattern.
     *
     * @param expected expected field value (can be present by regexp)
     * @param actual   actual field value
     */
    public static void assertJSONTextFieldEquals(String expected, String actual) {
        if (RegexpUtils.isRegexpString(expected)) {
            String regexp = RegexpUtils.extractRegexpFromExpectation(expected);
            assertTrue(String.format(DIFF_FIELDS_FORMAT, regexp, actual), RegexpUtils.isMatched(actual, regexp));
        } else {
            Assert.assertEquals(String.format(DIFF_FIELDS_FORMAT, expected, actual), expected, actual);
        }
    }

    /**
     * Asserts that JSON is present in list of JSON strings. If not - AssertionError is thrown
     *
     * @param jsons        list of JSONs
     * @param expectedJson expected JSON
     */
    public static void assertJSONIsPresentInList(List<String> jsons, String expectedJson) {
        boolean isPresent = false;

        for (String json : jsons) {
            try {
                Assertions.assertJSONEqualsStrictOrder(expectedJson, json);
                isPresent = true;
            } catch (JSONException | AssertionError e) {
                logger.info("Expected JSON is not present in actual JSONs");
            }
        }

        if (!isPresent) {
            throw new AssertionError(
                    String.format("Expected JSON:\n%s\n is not present in actual JSONs:\n%s",
                            expectedJson, String.join("\n", jsons.stream()
                                    .map(JSONUtils::beautifyIfJSON)
                                    .collect(Collectors.toList()))
                    ));
        }
    }

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
}
