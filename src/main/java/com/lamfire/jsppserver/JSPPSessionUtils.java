package com.lamfire.jsppserver;

import com.lamfire.hydra.Session;

/**
 * JSPPSessionUtils
 * Created by linfan on 2017/9/28.
 */
public class JSPPSessionUtils {
    private static final String JSPP_SESSION_ATTR_NAME = "_JSPP_SESSION_";

    public synchronized static final JSPPSession toJSPPSession(Session session,JSPPCoder jsppCoder){
        JSPPSession jsppSession = (JSPPSession)session.attr(JSPP_SESSION_ATTR_NAME);
        if(jsppSession == null){
            jsppSession=new JSPPSessionImpl(session,jsppCoder);
            session.attr(JSPP_SESSION_ATTR_NAME,jsppSession);
        }
        return jsppSession;
    }
}
