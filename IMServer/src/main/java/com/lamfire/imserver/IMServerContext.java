package com.lamfire.imserver;

import com.lamfire.hydra.HydraSessionMgr;
import com.lamfire.hydra.SessionMgr;
import com.lamfire.jsppserver.JSPPSession;
import com.lamfire.utils.Threads;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * IMServerContext
 * Created by linfan on 2017/9/28.
 */
public class IMServerContext {
    private static final IMServerContext instance = new IMServerContext();

    public static IMServerContext getInstance(){
        return instance;
    }

    private final ThreadPoolExecutor threadPoolExecutor = Threads.newFixedThreadPool(2);
    private final SessionMgr sessions = new HydraSessionMgr("USERS");
    private IMMessageStore messageStore;
    private IMServerContext(){}

    public IMMessageStore getMessageStore() {
        return messageStore;
    }

    void setMessageStore(IMMessageStore messageStore) {
        this.messageStore = messageStore;
    }

    public void addUserSession(String userId, JSPPSession session){
        sessions.put(userId,session);
    }

    public JSPPSession getUserSession(String userId){
        return (JSPPSession)sessions.get(userId);
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }
}
