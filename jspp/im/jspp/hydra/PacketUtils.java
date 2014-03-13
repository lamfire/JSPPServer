package im.jspp.hydra;


import com.lamfire.hydra.Message;
import com.lamfire.hydra.MessageContext;
import com.lamfire.hydra.net.Session;
import com.lamfire.json.JSON;
import com.lamfire.logger.Logger;
import im.jspp.elements.*;
import im.jspp.serializer.JSPPSerializer;


/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 13-11-8
 * Time: 下午4:48
 * To change this template use File | Settings | File Templates.
 */
public class PacketUtils {
    private static final Logger LOGGER = Logger.getLogger(PacketUtils.class);

    public static void send(Session session,int id,byte[] bytes){
        Message m = new Message();
        m.setId(id);
        m.setBody(bytes);
        session.send(m);
    }

    public static void send(MessageContext context,JSPP jspp){
        byte[] bytes = encode(jspp);
        context.send(context.getMessage().getId(),bytes);
    }

    public static void send(Session session,int messageId,JSPP jspp){
        send(session,messageId, encode(jspp));
    }

    public static JSPP decode(Message message) {
        if(message == null){
            return null;
        }
        byte[] bytes = message.getBody();
        return decode(bytes);
    }

    public static JSPP decode(byte[] bytes) {
        if(bytes == null){
            return null;
        }
        return JSPPSerializer.get().decode(bytes);
    }

    public static byte[] encode(JSPP jspp){
        return JSPPSerializer.get().encode(jspp);
    }

    public ProtocolType getProtocolType(byte[] bytes)    {
        return JSPPSerializer.get().getProtocolType(bytes) ;
    }

    public ProtocolType getProtocolType(String json)    {
        return JSPPSerializer.get().getProtocolType(json) ;
    }

    public static ProtocolType getProtocolType(JSON json){
        return JSPPSerializer.get().getProtocolType(json);
    }
}
