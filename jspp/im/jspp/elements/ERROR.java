package im.jspp.elements;

import com.lamfire.json.JSON;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 13-11-11
 * Time: 上午10:28
 * To change this template use File | Settings | File Templates.
 */
public class ERROR extends JSON {
    protected static final String JSPP_PROPERTY_BODY  = "body";
    protected static final String JSPP_PROPERTY_CODE  = "code";

    public ERROR() {
    }

    public ERROR(int code, String body) {
        put(JSPP_PROPERTY_CODE,code);
        put(JSPP_PROPERTY_BODY,body);
    }

    public Integer getCode() {
        return (Integer)get(JSPP_PROPERTY_CODE);
    }

    public void setCode(int code) {
        put(JSPP_PROPERTY_CODE,code);
    }

    public String getBody() {
        return (String)get(JSPP_PROPERTY_BODY);
    }

    public void setBody(String body) {
        put(JSPP_PROPERTY_BODY,body);
    }


}
