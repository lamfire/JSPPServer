package im.jspp.serializer;

import com.lamfire.json.JSON;
import com.lamfire.logger.Logger;
import im.jspp.elements.*;

import java.nio.charset.Charset;


/**
 * JSPP元素序例化工具类
 * User: lamfire
 * Date: 14-2-13
 * Time: 下午5:07
 */
public class JSPPSerializer implements Serializer {
    private static final Logger LOGGER = Logger.getLogger(JSPPSerializer.class);
    private final Charset CHARSET = Charset.forName("utf-8");
    private static final  JSPPSerializer SERIALIZER = new JSPPSerializer();
    public static  JSPPSerializer get(){
        return SERIALIZER;
    }

    @Override
    public byte[] encode(JSPP jspp) {
        if(jspp instanceof MESSAGE){
            return encodeMESSAGE((MESSAGE)jspp);
        }

        if(jspp instanceof IQ){
            return encodeIQ((IQ)jspp);
        }

        if(jspp instanceof PRESENCE){
            return encodePRESENCE((PRESENCE)jspp);
        }
        return null;
    }


    public byte[] encodeIQ(IQ iq) {
        JSON json = new JSON();
        json.put(JSPP.JSPP_TYPE_PREFIX_IQ,iq);
        String js = json.toJSONString();
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("[ENCODE]:" +js);
        }
        return js.getBytes(CHARSET);
    }


    public byte[] encodeMESSAGE(MESSAGE message) {
        JSON json = new JSON();
        json.put(JSPP.JSPP_TYPE_PREFIX_MESSAGE,message);

        String js = json.toJSONString();
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("[ENCODE]:" +js);
        }
        return js.getBytes(CHARSET);
    }


    public byte[] encodePRESENCE(PRESENCE presence) {
        JSON json = new JSON();
        json.put(JSPP.JSPP_TYPE_PREFIX_PRESENCE,presence);
        String js = json.toJSONString();
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("[ENCODE]:" +js);
        }
        return js.getBytes(CHARSET);
    }


    public JSPP decode(byte[] bytes) {
        String js = new String(bytes);
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("[DECODE]:" +js);
        }
        JSON json = JSON.fromJSONString(js);
        JSON jspp = (JSON)json.get(JSPP.JSPP_TYPE_PREFIX_MESSAGE);
        if(jspp != null){
            MESSAGE m = new MESSAGE();
            m.putAll(jspp);
            return m;
        }

        jspp = (JSON)json.get(JSPP.JSPP_TYPE_PREFIX_IQ);
        if(jspp != null){
            IQ m = new IQ();
            m.putAll(jspp);
            return m;
        }

        jspp = (JSON)json.get(JSPP.JSPP_TYPE_PREFIX_PRESENCE);
        if(jspp != null){
            PRESENCE m = new PRESENCE();
            m.putAll(jspp);
            return m;
        }
        return null;
    }


    public IQ decodeIQ(byte[] bytes) {
        String js = new String(bytes);
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("[DECODE]:" +js);
        }
        JSON json = JSON.fromJSONString(js);
        JSON jspp = (JSON)json.get(JSPP.JSPP_TYPE_PREFIX_IQ);
        if(jspp == null){
            return null;
        }
        IQ m = new IQ();
        m.putAll(jspp);
        return m;
    }


    public MESSAGE decodeMESSAGE(byte[] bytes) {
        String js = new String(bytes);
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("[DECODE]:" +js);
        }
        JSON json = JSON.fromJSONString(js);
        JSON jspp = (JSON)json.get(JSPP.JSPP_TYPE_PREFIX_MESSAGE);
        if(jspp == null){
            return null;
        }
        MESSAGE m = new MESSAGE();
        m.putAll(jspp);
        return m;
    }


    public PRESENCE decodePRESENCE(byte[] bytes) {
        String js = new String(bytes);
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("[DECODE]:" +js);
        }
        JSON json = JSON.fromJSONString(js);
        JSON jspp = (JSON)json.get(JSPP.JSPP_TYPE_PREFIX_PRESENCE);
        if(jspp == null){
            return null;
        }
        PRESENCE m = new PRESENCE();
        m.putAll(jspp);
        return m;
    }


    public ProtocolType getProtocolType(byte[] bytes) {
        String js = new String(bytes);
        JSON json = JSON.fromJSONString(js);
        return getProtocolType(json);
    }

    public ProtocolType getProtocolType(String json) {
        return getProtocolType(JSON.fromJSONString(json));
    }

    public ProtocolType getProtocolType(JSON json) {
        JSON jspp = (JSON)json.get(JSPP.JSPP_TYPE_PREFIX_MESSAGE);
        if(jspp != null){
            return ProtocolType.MESSAGE;
        }
        jspp = (JSON)json.get(JSPP.JSPP_TYPE_PREFIX_IQ);
        if(jspp != null){
            return ProtocolType.IQ;
        }
        jspp = (JSON)json.get(JSPP.JSPP_TYPE_PREFIX_PRESENCE);
        if(jspp != null){
            return ProtocolType.PRESENCE;
        }
        return null;
    }
}
