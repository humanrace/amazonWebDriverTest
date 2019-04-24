package Utils;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class HttpResponseDecorator {

    private HttpResponse responseBase;
    private String body;

    public HttpResponseDecorator(HttpResponse responseBase) {
        this.responseBase = responseBase;
    }

    public boolean isSuccess() {
        return getStatusCode() >= 200 && getStatusCode() <= 299;
    }

    public int getStatusCode() {
        return responseBase.getStatusLine().getStatusCode();
    }

    public StatusLine getStatusLine() {
        return responseBase.getStatusLine();
    }

    public String getBody() {
        if (body == null) {
            body = "";
            if (responseBase.getEntity() != null) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                try {
                    responseBase.getEntity().writeTo(outputStream);
                } catch (IOException e) {
                    throw new RuntimeException("Exception during response body reading", e);
                }
                body = outputStream.toString();
            }
        }

        return body;
    }

    @Override
    public String toString() {
        return responseBase.toString();
    }
}
