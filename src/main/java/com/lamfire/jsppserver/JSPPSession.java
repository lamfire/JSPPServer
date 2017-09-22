package com.lamfire.jsppserver;

import com.lamfire.hydra.Session;
import com.lamfire.jspp.JSPP;

import java.net.SocketAddress;

/**
 * JSPPSession
 * Created by linfan on 2017/9/22.
 */
public interface JSPPSession {
    void send(JSPP jspp);

    JSPPCoder getJSPPCoder();

    Session getSession();

    long getId();

    void close();

    SocketAddress getRemoteAddress();

    boolean isActive();

    boolean isOpen();

    boolean isWritable();

    Object attr(String name);

    void attr(String name,Object value);
}
