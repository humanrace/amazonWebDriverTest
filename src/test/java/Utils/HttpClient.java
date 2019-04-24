package Utils;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class HttpClient {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final ThreadLocal<CloseableHttpClient> threadLocalClient =
            ThreadLocal.withInitial(() -> HttpClientBuilder.create().build());

    public HttpClient() {
    }

    public HttpResponseDecorator sendRequest(
            RequestType type, String resource, String body) {
        RequestBuilder requestBuilder = new RequestBuilder();
        switch (type) {
            case POST:
                requestBuilder
                        .createPostRequest(Requests.BASE_URL + resource)
                        .appendJsonBody(body);
                requestBuilder.setHeader(new HttpHeader("Content-Type", "application/json"));
                requestBuilder.setHeader(new HttpHeader("Cookie", "ru_RU"));
                break;
            case GET:
                requestBuilder.createGetRequest(Requests.BASE_URL + resource);
                break;
            case PUT:
                requestBuilder
                        .createPutRequest(Requests.BASE_URL + resource)
                        .appendTextBody(body);
                break;
        }
        return sender(requestBuilder);
    }

    private HttpResponseDecorator sender(RequestBuilder requestBuilder) {
        HttpResponseDecorator response = sendRequest(requestBuilder.build());
        logger.info("Received response {}\n", response.getStatusLine());
        logger.debug("with body:\n{}", JSONUtils.beautifyIfJSON(response.getBody()));
        return response;
    }

    private HttpResponseDecorator sendRequest(HttpUriRequest request) {
        logger.info("Sending request: \n{}", request);
        logger.debug("Headers: {}", Arrays.toString(request.getAllHeaders()));
        if (request instanceof HttpEntityEnclosingRequestBase) {
            HttpEntityEnclosingRequestBase entityEnclosingRequest = (HttpEntityEnclosingRequestBase) request;
            if (null != entityEnclosingRequest.getEntity()) {
                ByteArrayOutputStream outstream = new ByteArrayOutputStream();
                try {
                    entityEnclosingRequest.getEntity().writeTo(outstream);
                    String body = outstream.toString(StandardCharsets.UTF_8.name());
                    if (Arrays.stream(request.getAllHeaders()).noneMatch(this::headerContainsMediaInfo)) {
                        logger.debug("Body: \n{}", JSONUtils.beautifyIfJSON(body));
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Failed to create request body", e);
                }
            }
        }

        try {
            CloseableHttpClient client = threadLocalClient.get();
            return new HttpResponseDecorator(client.execute(request));
        } catch (IOException e) {
            throw new RuntimeException("Failed to send request", e);
        }
    }

    private boolean headerContainsMediaInfo(Header h) {
        return h.getValue() != null && (h.getValue().equals("image/jpeg") || h.getValue().equals("video/mp4"));
    }
}