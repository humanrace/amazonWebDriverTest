package Utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.*;


public class RequestBuilder {
    private HttpUriRequest request;
    private String body = null;

    public RequestBuilder createPostRequest(String url) {
        request = new HttpPost(url);
        return this;
    }

    public RequestBuilder appendJsonBody(String json) {
        body = json;
        return appendBody(new StringEntity(json, HeaderData.CONTENT_TYPE_JSON));
    }

    public RequestBuilder addMultyPartFormData(HttpEntity entity) {
        return appendBody(entity);
    }

    public RequestBuilder setHeader(HttpHeader header) {
        request.setHeader(header.getName(), header.getValue());
        return this;
    }

    public HttpUriRequest build() {
        return request;
    }

    private RequestBuilder appendBody(HttpEntity entity) {
        ((HttpEntityEnclosingRequestBase) request).setEntity(entity);
        return this;
    }
}
