package im.jspp.elements;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 13-11-11
 * Time: 上午10:24
 * To change this template use File | Settings | File Templates.
 */
public class IQ extends JSPP{
    public static final String TYPE_GET = "get";
    public static final String TYPE_SET = "set";
    public static final String TYPE_ERROR = "error";
    public static final String TYPE_RESULT = "result";

    protected static final String JSPP_PROPERTY_KEY  = "key";
    protected static final String JSPP_PROPERTY_NS  = "ns";
    protected static final String JSPP_PROPERTY_ARGS  = "args";
    protected static final String JSPP_PROPERTY_RESULT  = "result";

    private ARGS args;
    private RESULT result;

    public String getKey() {
        return (String)get(JSPP_PROPERTY_KEY);
    }

    public void setKey(String key) {
        put(JSPP_PROPERTY_KEY,key);
    }

    public String getNs() {
        return (String)get(JSPP_PROPERTY_NS);
    }

    public void setNs(String ns) {
        put(JSPP_PROPERTY_NS,ns);
    }

    public ARGS getArgs() {
        if(args != null){
            return args;
        }
        Map<String,Object> map = (Map<String,Object>)get(JSPP_PROPERTY_ARGS);
        if(map == null){
            return null;
        }
        args = new ARGS();
        args.putAll(map);
        return args;
    }

    public void setArgs(ARGS args) {
        this.args = args;
        put(JSPP_PROPERTY_ARGS,args);
    }

    public RESULT getResult() {
        if(result != null){
            return result;
        }
        Map<String,Object> map = (Map<String,Object>)get(JSPP_PROPERTY_RESULT);
        if(map == null){
            return null;
        }
        result  = new RESULT();
        result.putAll(map);
        return result;
    }

    public void setResult(RESULT result) {
        this.result = result;
        put(JSPP_PROPERTY_RESULT,result);
    }

    @Override
    public String toString() {
        return "{IQ:" + super.toString() + '}';
    }
}
