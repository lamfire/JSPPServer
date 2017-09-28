package com.lamfire.jsppserver;

import com.lamfire.hydra.Message;
import com.lamfire.hydra.Session;
import com.lamfire.hydra.SessionClosedListener;
import com.lamfire.jspp.JSPP;
import com.lamfire.logger.Logger;

import java.net.SocketAddress;

/**
 * JSPPSession
 * Created by linfan on 2017/9/22.
 */
class JSPPSessionImpl implements JSPPSession{
    private static final Logger LOGGER = Logger.getLogger(JSPPSessionImpl.class);
    private JSPPCoder jsppCoder;
    private Session session;

    public JSPPSessionImpl(Session session, JSPPCoder jsppCoder) {
        this.session = session;
        this.jsppCoder = jsppCoder;
    }

    public void send(JSPP jspp){
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("[SEND]:" + session + " -> " + jspp);
        }
        session.send(jsppCoder.encode(this,jspp));
    }

    public JSPPCoder getJSPPCoder() {
        return jsppCoder;
    }

    public Session getSession() {
        return session;
    }

    public long getId(){
        return session.getId();
    }

    public void send(Message message) {
        throw new IllegalArgumentException("Not support this method");
    }

    public void send(Message message, boolean sync) throws InterruptedException {
        throw new IllegalArgumentException("Not support this method");
    }

    public void close(){
        session.close();
    }

    public SocketAddress getRemoteAddress(){
        return session.getRemoteAddress();
    }

    public boolean isActive(){
        return session.isActive();
    }

    public boolean isOpen(){
        return session.isOpen();
    }

    public boolean isWritable(){
        return session.isWritable();
    }

    public Object attr(String name){
        return session.attr(name);
    }

    public void attr(String name,Object value){
        session.attr(name,value);
    }

    public void heartbeat() {
        session.heartbeat();
    }

    public void addCloseListener(SessionClosedListener listener) {
        session.addCloseListener(listener);
    }

    public void removeCloseListener(SessionClosedListener listener) {
        session.removeCloseListener(listener);
    }
}
