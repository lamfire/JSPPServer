package im.jspp.jsppserver;

import com.lamfire.hydra.Destination;
import com.lamfire.hydra.Message;
import com.lamfire.hydra.MessageBus;
import com.lamfire.hydra.net.Session;
import com.lamfire.utils.StringUtils;
import im.jspp.elements.*;
import im.jspp.hydra.PacketUtils;
import im.jspp.jsppserver.router.IQRouter;
import im.jspp.jsppserver.router.MessageRouter;
import im.jspp.jsppserver.router.PresenceRouter;

import java.util.Map;

/**
 * JSPP消息路由总线
 * User: lamfire
 * Date: 14-2-19
 * Time: 上午10:31
 * To change this template use File | Settings | File Templates.
 */
public class JSPPRouter extends MessageBus {
    private JSPPServer jsppServer;

    public JSPPRouter(JSPPServer jsppServer, int bufferSize) {
        super(jsppServer, bufferSize);
        this.jsppServer = jsppServer;
    }

    public void setJSPPServer(JSPPServer jsppServer){
        this.jsppServer = jsppServer;
    }

    public void route(JSPP jspp){
        Message message = new Message(1, PacketUtils.encode(jspp));
        dispatch(getDestinations(),jspp,message);
    }

    private JSPP decode(Message message) {
        return PacketUtils.decode(message);
    }


    protected void dispatch(Map<String,? extends Destination> destinations ,JSPP jspp,Message message){
        //IQ路由
        if(jspp instanceof IQ){
            Destination dest = destinations.get(IQRouter.KEY_NAME);
            if(dest != null){
                dest.forwardMessage(message);
            }
            return;
        }

        //MESSAGE路由
        if(jspp instanceof MESSAGE){
            MessageRouter router = (MessageRouter)destinations.get(MessageRouter.KEY_NAME);
            if(router != null){
                //routeMessate(dest,new TO(jspp.getTo()),message);
                router.forwardMessage(new TO(jspp.getTo()), message.getBody());
            }
            return;
        }

        //PRESENCE路由
        if(jspp instanceof PRESENCE){
            Destination dest = destinations.get(PresenceRouter.KEY_NAME);
            if(dest != null){
                dest.forwardMessage(message);
            }
            return;
        }
    }


    @Override
    protected void onDispatch(Map<String,? extends Destination> destinations, Message message) {
        JSPP jspp = decode(message);
        dispatch(destinations,jspp,message);
    }

    @Override
    protected void onSendReplyMessage(Session replySession, Message message) {
        super.onSendReplyMessage(replySession, message);

        //已经绑定过UID
        if(replySession.getAttribute("uid") != null){
            return;
        }

        //绑定UID
        JSPP jspp = PacketUtils.decode(message);
        if(jspp instanceof IQ){
            IQ iq = (IQ)jspp;
            if(StringUtils.equals(iq.getNs(), "user.auth") && StringUtils.equals(iq.getType(), IQ.TYPE_RESULT) ){
                //绑定在线用户UID
                RESULT result = iq.getResult();
                String uid = result.getString("uid");
                JSPPServer.get().online(uid,replySession);
            }
        }
    }
}
