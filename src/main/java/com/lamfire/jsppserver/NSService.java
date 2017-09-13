package com.lamfire.jsppserver;

import com.lamfire.hydra.Session;
import com.lamfire.jspp.SERVICE;

/**
 * NS 服务接口
 */
public interface NSService {
    SERVICE service(SERVICE service);
}
