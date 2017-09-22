package com.lamfire.jsppserver;

import com.lamfire.hydra.Session;
import com.lamfire.jspp.PRESENCE;
import com.lamfire.jspp.SERVICE;

/**
 * 服务处理接口
 */
public interface ServiceHandler {

    void onService(JSPPSession session, SERVICE service);
}
