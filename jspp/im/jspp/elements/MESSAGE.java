package im.jspp.elements;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 13-11-11
 * Time: 上午10:24
 * To change this template use File | Settings | File Templates.
 */
public class MESSAGE extends JSPP{
    public static final String TYPE_DEFAULT = "default";
    public static final String TYPE_CHAT = "chat";
    public static final String TYPE_GROUPCHAT = "groupchat";
    public static final String TYPE_ERROR = "error";

    protected static final String JSPP_PROPERTY_ATTACH  = "attach";

    private ATTACH attach;

    public ATTACH getAttach() {
        if(attach != null){
            return attach;
        }
        Map<String,Object> map = (Map<String,Object>) get(JSPP_PROPERTY_ATTACH);
        if(map == null){
            return null;
        }
        attach = new ATTACH();
        attach.putAll(map);
        return attach;
    }

    public void setAttach(ATTACH attach) {
        this.attach = attach;
        put(JSPP_PROPERTY_ATTACH,attach);
    }

    @Override
    public String toString() {
        return "{MESSAGE:" + super.toString() + '}';
    }
}
