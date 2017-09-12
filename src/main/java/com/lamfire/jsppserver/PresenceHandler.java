package com.lamfire.jsppserver;

import com.lamfire.hydra.Session;
import com.lamfire.jspp.MESSAGE;
import com.lamfire.jspp.PRESENCE;

/**
 * 状态处理接口
 */
public interface PresenceHandler {

    void onPresence(Session session, PRESENCE presence);
}
