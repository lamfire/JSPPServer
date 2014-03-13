package im.jspp.jsppserver.router;

import com.lamfire.chimaera.OnMessageListener;
import com.lamfire.chimaera.Poller;
import com.lamfire.chimaera.client.ChimaeraCli;
import com.lamfire.hydra.Destination;
import com.lamfire.hydra.Message;
import com.lamfire.hydra.net.Session;
import com.lamfire.logger.Logger;

import com.lamfire.utils.StringUtils;
import im.jspp.elements.JID;
import im.jspp.elements.JSPP;
import im.jspp.elements.TO;
import im.jspp.hydra.PacketUtils;
import im.jspp.jsppserver.JSPPServer;
import im.jspp.jsppserver.OfflineMessageStore;

/**
 * 消息路由
 * User: lamfire
 * Date: 14-2-17
 * Time: 上午10:28
 * To change this template use File | Settings | File Templates.
 */
public class MessageRouter implements Destination,OnMessageListener {
    private static final Logger LOGGER = Logger.getLogger(MessageRouter.class);
    public static final String KEY_NAME = "MESSAGE_ROUTER";

    private JSPPServer server;
    private ChimaeraCli cli;
    private Poller poller ;
    private String domain;
    private OfflineMessageStore offlineMessageStore;

    public MessageRouter(String host, int port,String domain) {
        this.domain = domain;
        this.cli = new ChimaeraCli();
        this.cli.open(host,port);
        this.cli.setMaxConnections(1);
        this.poller = this.cli.getPoller();
        this.poller.bind(domain,domain,this);
        this.offlineMessageStore = new OfflineMessageStore(this.cli);
    }

    public void setJSPPServer(JSPPServer server){
        this.server = server;
    }

    public String getName() {
        return KEY_NAME;
    }

    /**
     * 获得该用户的离线消息,并推送到终端
     * @param session
     * @param jid
     */
    public void getOfflineMessage(Session session,JID jid){
        byte[] message = offlineMessageStore.getMessage(jid);
        while(message != null){
            PacketUtils.send(session,-1,message);
            message = offlineMessageStore.getMessage(jid);
        }
    }

    @Override
    public void forwardMessage(Message message) {
        JSPP jspp  = PacketUtils.decode(message);
        forwardMessage(new TO(jspp.getTo()),message.getBody());
    }

    public void dispatcheMessage(TO to,byte[] bytes) {
        poller.publish(to.getDomain(),to.getDomain(),bytes);
    }

    public void forwardMessage(TO to,byte[] bytes) {
        if(!StringUtils.equalsIgnoreCase(this.domain, to.getDomain())){
            //消息不是发给自已的,则转发到下个路由节点
            dispatcheMessage(to,bytes);
            return;
        }

        //消息为发给自已的消息
        String uid = to.getId();
        Session session = server.getSessionByUid(uid);
        if(session == null){//该JID不在线
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("[JID:"+uid+"]offline, save message to queue.");
            }
            offlineMessageStore.addMessage(to,bytes);
            return;
        }

        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("[JID:"+uid+"]online, sending...");
        }
        //在线则直接发给用户
        Message m = new Message(-1,bytes);
        session.send(m);
    }

    /**
     * 当接收到来自消息路由器转发来的消息时触发
     * @param s
     * @param bytes
     */
    @Override
    public void onMessage(String s, byte[] bytes) {
        JSPP jspp = PacketUtils.decode(bytes);
        TO to = new TO(jspp.getTo());
        forwardMessage(to,bytes);
    }
}
