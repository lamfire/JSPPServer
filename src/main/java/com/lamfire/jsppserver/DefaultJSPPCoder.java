package com.lamfire.jsppserver;

import com.lamfire.hydra.Message;
import com.lamfire.hydra.MessageFactory;
import com.lamfire.jspp.JSPP;
import com.lamfire.jspp.JSPPUtils;
import com.lamfire.logger.Logger;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * DefaultJSPPCoder
 * Created by linfan on 2017/9/22.
 */
public class DefaultJSPPCoder implements JSPPCoder {
    private static final Logger LOGGER = Logger.getLogger(DefaultJSPPCoder.class);
    private final AtomicInteger atomicInteger = new AtomicInteger();
    public JSPP decode(JSPPSession jsppSession,Message message) {
        return JSPPUtils.decode(message.content());
    }

    public Message encode(JSPPSession jsppSession,JSPP jspp) {
        return MessageFactory.message(atomicInteger.getAndIncrement(), JSPPUtils.encode(jspp));
    }
}
