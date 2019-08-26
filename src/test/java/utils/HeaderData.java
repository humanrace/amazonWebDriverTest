package Utils;

import java.nio.charset.StandardCharsets;

public final class HeaderData {

    public static final String JSON = "application/json";
    public static final String COOKIE = "en_US";

    public static final org.apache.http.entity.ContentType CONTENT_TYPE_JSON =
            org.apache.http.entity.ContentType.create(JSON, StandardCharsets.UTF_8);
}