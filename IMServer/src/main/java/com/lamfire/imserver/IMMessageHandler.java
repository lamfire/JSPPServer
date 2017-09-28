package com.lamfire.imserver;

import com.lamfire.jspp.JSPPUtils;
import com.lamfire.jspp.MESSAGE;
import com.lamfire.jsppserver.JSPPSession;
import com.lamfire.jsppserver.MessageHandler;
import com.lamfire.logger.Logger;

/**
 * IMMessageHandler
 * Created by linfan on 2017/9/28.
 */
public class IMMessageHandler implements MessageHandler {
    private static final Logger LOGGER = Logger.getLogger(IMMessageHandler.class);
    private IMMessageStore store;

    public IMMessageHandler(IMMessageStore store) {
        this.store = store;
    }

    public void onMessage(JSPPSession session, MESSAGE message) {
        String to = message.getTo();
        message.setFrom((String)session.attr("USER"));

        IMServerContext context = IMServerContext.getInstance();
        JSPPSession toSession = context.getUserSession(to);
        if(toSession == null){
            this.store.add(to, message);
            LOGGER.debug("[ADD_MESSAGE_TO_STORE] : " + message);
            return;
        }
        toSession.send(message);
    }
}
