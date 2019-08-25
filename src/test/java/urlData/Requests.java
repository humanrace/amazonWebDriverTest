package urlData;

/**
 * Used for storing constants and paths to common requests.
 */
public final class Requests {

    public static final String BASE_WEB_URL = "https://iqoption.com/en";
    public static final String AUTH_URL = "https://auth.iqoption.com";

    public static final class Registration {
        public static final String REGISTRATION_URL = "/api/register";
    }

    public static final class Authorization {
        public static final String AUTHORIZATION_URI = "/api/v1.0/login";
    }
}
