package im.jspp.elements;

import com.lamfire.json.JSON;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 13-11-13
 * Time: 下午4:16
 * To change this template use File | Settings | File Templates.
 */
public class ATTACH extends JSON {
    public static final String TYPE_IMAGE = "image";
    public static final String TYPE_AUDIO = "audio";
    public static final String TYPE_VIDEO = "video";
    public static final String TYPE_LOCATION = "location";

    protected static final String JSPP_PROPERTY_BODY  = "body";
    protected static final String JSPP_PROPERTY_TYPE  = "type";

    public String getType() {
        return (String)get(JSPP_PROPERTY_TYPE);
    }

    public void setType(String type) {
        put(JSPP_PROPERTY_TYPE,type);
    }

    public String getBody() {
        return (String)get(JSPP_PROPERTY_BODY);
    }

    public void setBody(String body) {
        put(JSPP_PROPERTY_BODY,body);
    }
}
