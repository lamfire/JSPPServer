package im.jspp.elements;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 13-11-11
 * Time: 上午10:24
 * To change this template use File | Settings | File Templates.
 */
public class PRESENCE extends JSPP{
    public static final String TYPE_AVAILABLE = "available";
    public static final String TYPE_UNAVAILABLE = "unavailable";
    public static final String TYPE_ERROR = "error";
    public static final String TYPE_PROBE = "probe";
    public static final String TYPE_SUBSCRIBE = "subscribe";
    public static final String TYPE_SUBSCRIBED = "subscribed";
    public static final String TYPE_UNSUBSCRIBE = "unsubscribe";
    public static final String TYPE_UNSUBSCRIBED = "unsubscribed";

    public static final String STATUS_AWAY = "away";
    public static final String STATUS_CHATTING = "chatting";
    public static final String STATUS_DND = "dnd";

    protected static final String JSPP_PROPERTY_STATUS  = "status";

    public String getStatus() {
        return (String)get(JSPP_PROPERTY_STATUS);
    }

    public void setStatus(String status) {
        put(JSPP_PROPERTY_STATUS,status);
    }

    @Override
    public String toString() {
        return "{PRESENCE:" + super.toString() + '}';
    }
}
