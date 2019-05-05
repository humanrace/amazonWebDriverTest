package Utils;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public class MultiPartBuilder {

    public static HttpEntity setMultiPartParameters(List<Map<String, String>> dataList) {
        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        try {
            entity.addPart("first_name", new StringBody(dataList.get(0).get("first_name")));
            entity.addPart("last_name", new StringBody(dataList.get(0).get("last_name")));
            entity.addPart("email", new StringBody(dataList.get(0).get("email")));
            entity.addPart("password", new StringBody(dataList.get(0).get("password")));
            entity.addPart("tz", new StringBody("Europe/London"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return entity;
    }
}
