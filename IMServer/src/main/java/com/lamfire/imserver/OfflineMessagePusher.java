package com.lamfire.imserver;

import com.lamfire.jspp.JSPP;
import com.lamfire.jspp.JSPPUtils;
import com.lamfire.jsppserver.JSPPSession;

import java.util.List;

/**
 * OfflineMessagePusher
 * Created by linfan on 2017/9/28.
 */
public class OfflineMessagePusher implements Runnable{
    private IMMessageStore imMessageStore;
    private String userId;
    private JSPPSession session;

    public OfflineMessagePusher(IMMessageStore imMessageStore,String userId, JSPPSession session) {
        this.userId = userId;
        this.session = session;
        this.imMessageStore = imMessageStore;
    }

    public void run() {
        List<JSPP> list = imMessageStore.get(userId);
        if(list == null || list.isEmpty()){
            return;
        }
        for(JSPP m : list){
            session.send(m);
        }
        imMessageStore.remove(userId);
    }
}
