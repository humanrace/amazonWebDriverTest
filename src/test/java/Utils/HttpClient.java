package Utils;

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
import java.util.List;
import java.util.Map;

import static Utils.MultiPartBuilder.setMultiPartParameters;

public class HttpClient {

    private final Logger logger = LoggerFactory.getLogger(HttpClient.class);

    private static final ThreadLocal<CloseableHttpClient> threadLocalClient =
            ThreadLocal.withInitial(() -> HttpClientBuilder.create().build());

    public HttpResponseDecorator sendRequest(
            RequestType type, String url, String resource, String body) {
        RequestBuilder requestBuilder = new RequestBuilder();
        switch (type) {
            case POST:
                requestBuilder
                        .createPostRequest(url + resource)
                        .appendJsonBody(body)
                        .setHeader(new HttpHeader("Cookie", HeaderData.COOKIE))
                        .setHeader(new HttpHeader("Content", HeaderData.JSON));
                break;
        }
        return sender(requestBuilder);
    }

    public HttpResponseDecorator sendMultipartRequest(
            String url, String resource, List<Map<String, String>> dataList) {
        RequestBuilder requestBuilder = new RequestBuilder();
        requestBuilder
                .createPostRequest(url + resource)
                .addMultyPartFormData(setMultiPartParameters(dataList))
                .setHeader(new HttpHeader("Cookie", HeaderData.COOKIE));
        return sender(requestBuilder);
    }

    private HttpResponseDecorator sender(RequestBuilder requestBuilder) {
        HttpResponseDecorator response = sendRequest(requestBuilder.build());
        logger.info("Received response {}\n", response.getStatusLine());
        logger.debug("with body:\n{}", JSONUtils.beautifyIfJSON(response.getBody()));
        return response;
    }

    private HttpResponseDecorator sendRequest(HttpUriRequest request) {
        logger.info("Sending request to Uri: \n{}", request.getURI());
        logger.info("Headers: {}", Arrays.toString(request.getAllHeaders()));
        if (request instanceof HttpEntityEnclosingRequestBase) {
            HttpEntityEnclosingRequestBase entityEnclosingRequest = (HttpEntityEnclosingRequestBase) request;
            if (null != entityEnclosingRequest.getEntity()) {
                ByteArrayOutputStream outstream = new ByteArrayOutputStream();
                try {
                    entityEnclosingRequest.getEntity().writeTo(outstream);
                    String body = outstream.toString(StandardCharsets.UTF_8.name());
                    logger.info("Body: \n{}", JSONUtils.beautifyIfJSON(body));
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
}