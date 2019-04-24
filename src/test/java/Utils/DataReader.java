package Utils;

import com.jayway.jsonpath.JsonPath;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utils for reading data used in test
 */
public final class DataReader {

    /**
     * Reads resource file as JSON. Converts params to map and substitutes parameters with values.
     *
     * @param pathToJson path to JSON file
     * @return parsed JSON as String
     * @see DataReader#(String)
     */
    public static String readJson(String pathToJson) {
        return readResource(pathToJson);
    }

    /**
     * Reads resource file as string. If source is JSON parses string as JSON string.
     *
     * @param pathToResource path to file in 'resources' folder
     * @return string with file content
     */
    public static String readResource(String pathToResource) {
        InputStream is = DataReader.class.getClassLoader().getResourceAsStream(pathToResource);
        if (is == null) {
            throw new IllegalArgumentException(String.format("Resource by '%s' path not found", pathToResource));
        }
        String data = new BufferedReader(new InputStreamReader(is)).lines()
                .parallel().collect(Collectors.joining("\n"));

        if (JSONUtils.isJSON(data)) {
            return JsonPath.parse(data).jsonString();
        }
        return data;
    }

    /**
     * Checks if resource exists.
     *
     * @param pathToResource path to file in 'resources' folder
     * @return string with file content
     */
    public static boolean resourceExists(String pathToResource) {
        return ClassLoader.getSystemClassLoader().getResource(pathToResource) != null;
    }
}
