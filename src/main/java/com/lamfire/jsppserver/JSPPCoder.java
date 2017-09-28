package com.lamfire.jsppserver;

import com.lamfire.hydra.Message;
import com.lamfire.jspp.JSPP;

/**
 * JSPPCoder
 * Created by linfan on 2017/9/22.
 */
public interface JSPPCoder {
    JSPP decode(JSPPSession jsppSession,Message message);
    Message encode(JSPPSession jsppSession,JSPP jspp);
}
