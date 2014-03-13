package im.jspp.elements;

import com.lamfire.json.JSON;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 13-11-13
 * Time: 下午4:13
 * To change this template use File | Settings | File Templates.
 */
public abstract class JSPP extends JSON {
    public static final String JSPP_TYPE_PREFIX_IQ = "iq";
    public static final String JSPP_TYPE_PREFIX_MESSAGE = "message";
    public static final String JSPP_TYPE_PREFIX_PRESENCE = "presence";

    protected static final String JSPP_PROPERTY_TYPE  = "type";
    protected static final String JSPP_PROPERTY_FROM  = "from";
    protected static final String JSPP_PROPERTY_TO  = "to";
    protected static final String JSPP_PROPERTY_ID  = "id";
    protected static final String JSPP_PROPERTY_BODY  = "body";
    protected static final String JSPP_PROPERTY_ERROR  = "error";
    protected static final String JSPP_PROPERTY_CODE  = "code";

    private ERROR error;

    public String getType() {
        return (String)get(JSPP_PROPERTY_TYPE);
    }

    public void setType(String type) {
        put(JSPP_PROPERTY_TYPE,type);
    }

    public String getFrom() {
        return (String)get(JSPP_PROPERTY_FROM);
    }

    public void setFrom(String from) {
        put(JSPP_PROPERTY_FROM,from);
    }

    public String getTo() {
        return (String)get(JSPP_PROPERTY_TO);
    }

    public void setTo(String to) {
        put(JSPP_PROPERTY_TO,to);
    }

    public String getId() {
        return (String)get(JSPP_PROPERTY_ID);
    }

    public void setId(String id) {
        put(JSPP_PROPERTY_ID,id);
    }

    public String getBody() {
        return (String)get(JSPP_PROPERTY_BODY);
    }

    public void setBody(String body) {
        put(JSPP_PROPERTY_BODY,body);
    }

    public ERROR getError() {
        if(error != null){
            return error;
        }
        Map<String,Object> map = ( Map<String,Object>)get(JSPP_PROPERTY_ERROR);
        if(map == null){
            return null;
        }
        error = new ERROR();
        error.setCode((Integer)map.get(JSPP_PROPERTY_CODE));
        error.setBody((String)map.get(JSPP_PROPERTY_BODY));
        return error;
    }

    public void setError(ERROR error) {
        this.error = error;
        put(JSPP_PROPERTY_ERROR,error);
    }

    public static ProtocolType getProtocolType(JSPP jspp){
        if(jspp instanceof MESSAGE){
            return ProtocolType.MESSAGE;
        }

        if(jspp instanceof IQ){
            return ProtocolType.IQ;
        }

        if(jspp instanceof PRESENCE){
            return ProtocolType.PRESENCE;
        }
        return null;
    }

}
