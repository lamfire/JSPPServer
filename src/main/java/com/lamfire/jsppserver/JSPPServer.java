package com.lamfire.jsppserver;

import com.lamfire.hydra.*;
import com.lamfire.jspp.*;
import com.lamfire.logger.Logger;

/**
 * JSPP Server
 * Created by linfan on 2017/9/12.
 */
public class JSPPServer implements MessageReceivedListener{
    private static final Logger LOGGER = Logger.getLogger(JSPPServer.class);

    private Hydra hydra;
    private String bind;
    private int port;
    private int threads = 64;

    private MessageHandler messageHandler;
    private PresenceHandler presenceHandler;
    private ServiceHandler serviceHandler;

    public String getBind() {
        return bind;
    }

    public void setBind(String bind) {
        this.bind = bind;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public PresenceHandler getPresenceHandler() {
        return presenceHandler;
    }

    public void setPresenceHandler(PresenceHandler presenceHandler) {
        this.presenceHandler = presenceHandler;
    }

    public ServiceHandler getServiceHandler() {
        return serviceHandler;
    }

    public void setServiceHandler(ServiceHandler serviceHandler) {
        this.serviceHandler = serviceHandler;
    }

    public synchronized void startup(){
        if(this.hydra != null){
            return;
        }
        HydraBuilder builder = new HydraBuilder();
        builder.bind(bind).port(port).threads(threads).messageReceivedListener(this);
        this.hydra = builder.build();
        this.hydra.startup();
    }

    public synchronized void shutdown(){
        if(this.hydra == null){
            return;
        }
        this.hydra.shutdown();
        this.hydra = null;
    }

    public void onMessageReceived(Session session, Message message) {
        JSPP jspp = null;
        try {
            jspp = JSPPUtils.decode(message.content());
        }catch (Throwable e){
            LOGGER.error("[ERROR]:None jspp packet,ignore -> " + session);
            return;
        }

//        if(LOGGER.isDebugEnabled()) {
//            LOGGER.debug("[RECEIVED]:" + session + " -> " + jspp);
//        }

        ProtocolType type = JSPP.getProtocolType(jspp);
        switch (type){
            case MESSAGE:
                handleMessage(session,(MESSAGE) jspp);
                break;
            case PRESENCE:
                handlePresence(session,(PRESENCE) jspp);
                break;
            case SERVICE:
                handleService(session,(SERVICE) jspp);
                break;
        }
    }

    protected void handleMessage(Session session, MESSAGE message){
        if(messageHandler == null){
            LOGGER.warn("MessageHandler Not Found -> " + session +" > " + message);
            return;
        }

        messageHandler.onMessage(session,message);
    }

    protected void handlePresence(Session session, PRESENCE presence){
        if(presenceHandler == null){
            LOGGER.warn("PresenceHandler Not Found -> " + session +" > " + presence);
            return;
        }

        presenceHandler.onPresence(session,presence);
    }

    protected void handleService(Session session, SERVICE service){
        if(serviceHandler == null){
            LOGGER.warn("ServiceHandler Not Found -> " + session +" > " + service);
            return;
        }
        serviceHandler.onService(session,service);
    }

    public SessionMgr getSessionMgr(){
        if(hydra == null){
            return null;
        }
        return hydra.getSessionMgr();
    }
}
