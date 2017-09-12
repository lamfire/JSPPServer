package com.lamfire.jsppserver;

import com.lamfire.hydra.Session;
import com.lamfire.jspp.MESSAGE;

/**
 * 消息处理接口
 */
public interface MessageHandler {
    void onMessage(Session session, MESSAGE message);
}
