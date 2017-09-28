package com.lamfire.jsppserver;

import com.lamfire.hydra.Session;
import com.lamfire.jspp.JSPP;

import java.net.SocketAddress;

/**
 * JSPPSession
 * Created by linfan on 2017/9/22.
 */
public interface JSPPSession extends Session{
    void send(JSPP jspp);
}
