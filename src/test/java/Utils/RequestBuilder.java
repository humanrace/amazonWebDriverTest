package Utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;

import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class RequestBuilder {

    private HttpUriRequest request;
    private String body = null;

    public RequestBuilder() {

    }

    public RequestBuilder createPostRequest(String url) {
        request = new HttpPost(url);
        return this;
    }

    public RequestBuilder appendJsonBody(String json) {
        body = json;
        return appendBody(new StringEntity(json, "application/json"));
    }

    public RequestBuilder createPutRequest(String url) {
        request = new HttpPut(url);
        return this;
    }

    public RequestBuilder appendTextBody(String text) {
        body = text;
        return appendBody(new StringEntity(text, StandardCharsets.UTF_8));
    }

    public RequestBuilder setHeader(HttpHeader header) {
        request.setHeader(header.getName(), header.getValue());
        return this;
    }

    public RequestBuilder createGetRequest(String url) {
        URIBuilder builder;
        try {
            builder = new URIBuilder(url);
            request = new HttpGet(builder.build().toString());
            return this;
        } catch (URISyntaxException e) {
            throw new RuntimeException("Failed to create url parameters", e);
        }
    }

    public HttpUriRequest build() {
        return request;
    }

    private RequestBuilder appendBody(HttpEntity entity) {
        if (!(request instanceof HttpEntityEnclosingRequestBase)) {
            throw new IllegalArgumentException(
                    String.format("RequestApi of type '%s' can't have body", request.getClass().getName()));
        }
        ((HttpEntityEnclosingRequestBase) request).setEntity(entity);
        return this;
    }
}
