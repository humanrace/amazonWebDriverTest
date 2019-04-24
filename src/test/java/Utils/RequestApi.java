package Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RequestApi {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private HttpClient httpClient;


    protected HttpResponseDecorator sendGetRequest(String url) {
        return httpClient.sendRequest(
                RequestType.GET,
                getRelativeUrl(url),
                null);
    }

    protected HttpResponseDecorator sendPostRequest(String url, String body) {
        return httpClient.sendRequest(
                RequestType.POST,
                getRelativeUrl(url),
                body);
    }


    protected abstract String getRelativeUrl(String url);
}
