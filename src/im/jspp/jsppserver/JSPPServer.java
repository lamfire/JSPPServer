package im.jspp.jsppserver;

import com.lamfire.hydra.Gateway;
import com.lamfire.hydra.net.Session;
import com.lamfire.hydra.net.SessionClosedListener;
import com.lamfire.hydra.net.SessionGroup;
import com.lamfire.logger.Logger;
import im.jspp.elements.JID;
import im.jspp.elements.PRESENCE;
import im.jspp.jsppserver.router.IQRouter;
import im.jspp.jsppserver.router.MessageRouter;
import im.jspp.jsppserver.router.PresenceRouter;


/**
 * JSPP接入服务器
 * User: lamfire
 * Date: 14-2-14
 * Time: 上午10:42
 * To change this template use File | Settings | File Templates.
 */
public class JSPPServer extends Gateway implements SessionClosedListener{
    private static final Logger LOGGER = Logger.getLogger(JSPPServer.class);
    private static JSPPServer INSTANCE;
    public synchronized static JSPPServer get(){
        if(INSTANCE != null){
            return INSTANCE;
        }
        Configure conf = Configure.get();
        INSTANCE = new JSPPServer(conf.getBind(),conf.getPort(),conf.getDomain());
        IQRouter iqRouter = new IQRouter(conf.getIqHost(),conf.getIqPort());
        MessageRouter messageRouter = new MessageRouter(conf.getMessageHost(),conf.getMessagePort(),conf.getDomain());
        PresenceRouter presenceRouter = new PresenceRouter(conf.getPresenceHost(),conf.getPresencePort());

        INSTANCE.setIQRouter(iqRouter);
        INSTANCE.setMessageRouter(messageRouter);
        INSTANCE.setPresenceRouter(presenceRouter);
        return INSTANCE;
    }

    private SessionGroup onlineSessionGroup = new SessionGroup();
    private String host;
    private int port;
    private String domain;

    private JSPPRouter router;
    private IQRouter iqRouter;
    private MessageRouter messageRouter;
    private PresenceRouter presenceRouter;

    private JSPPServer(String host, int port, String domain){
        super(host,port);
        this.host = host;
        this.port = port;
        this.domain = domain;
    }

    public Session getSessionByUid(String uid){
        return onlineSessionGroup.get(uid);
    }

    private void bindSession(String uid,Session session){
        session.setAttribute("uid",uid);
        onlineSessionGroup.add(uid,session);
        session.setSessionClosedListener(this);
        LOGGER.info("[SESSION_UID_BIND]:" + uid +"@" +domain+ " -> " + session);
    }

    public void online(String uid ,Session session){
        bindSession(uid,session);
        setPresenceAvailable(uid);
        //推送离线消息
        this.messageRouter.getOfflineMessage(session,new JID(uid,this.domain));
    }

    private void setPresenceAvailable(String uid){
        JID jid = new JID(uid,domain);
        PRESENCE p = new PRESENCE();
        p.setType(PRESENCE.TYPE_AVAILABLE);
        p.setFrom(jid.toString());
        router.route(p);
    }

    private void setPresenceUnAvailable(String uid){
        JID jid = new JID(uid,domain);
        PRESENCE p = new PRESENCE();
        p.setType(PRESENCE.TYPE_UNAVAILABLE);
        p.setFrom(jid.toString());
        router.route(p);
    }

    public String getDomain(){
        return this.domain;
    }

    public IQRouter getIQRouter() {
        return iqRouter;
    }

    public void setIQRouter(IQRouter iqRouter) {
        this.iqRouter = iqRouter;
    }

    public MessageRouter getMessageRouter() {
        return messageRouter;
    }

    public void setMessageRouter(MessageRouter messageRouter) {
        this.messageRouter = messageRouter;
        this.messageRouter.setJSPPServer(this);
    }

    public PresenceRouter getPresenceRouter() {
        return presenceRouter;
    }

    public void setPresenceRouter(PresenceRouter presenceRouter) {
        this.presenceRouter = presenceRouter;
    }

    public void startup(){
        this.bind();
        this.iqRouter.connect();
        this.presenceRouter.connect();

        this.router = new JSPPRouter(this,10000);
        this.router.addDestination(iqRouter.getName(),iqRouter);
        this.router.addDestination(messageRouter.getName(),messageRouter);
        this.router.addDestination(presenceRouter.getName(),presenceRouter);
    }

    @Override
    public void onClosed(Session session) {
        String uid = (String)session.getAttribute("uid");
        if(uid == null){
            return;
        }
        setPresenceUnAvailable(uid);
    }
}
