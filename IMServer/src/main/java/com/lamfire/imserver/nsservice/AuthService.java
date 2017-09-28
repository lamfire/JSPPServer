package com.lamfire.imserver.nsservice;

import com.lamfire.imserver.IMServerContext;
import com.lamfire.imserver.OfflineMessagePusher;
import com.lamfire.jspp.ARGS;
import com.lamfire.jspp.RESULT;
import com.lamfire.jspp.SERVICE;
import com.lamfire.jsppserver.JSPPSession;
import com.lamfire.jsppserver.NSService;
import com.lamfire.jsppserver.anno.NS;

/**
 * AuthService
 * Created by linfan on 2017/9/28.
 */
@NS(name = "user.auth")
public class AuthService implements NSService {
    public SERVICE service(JSPPSession session,SERVICE service) {
        ARGS args = service.getArgs();
        String userId = args.getString("userId");
        String token = args.getString("token");

        session.attr("USER",userId);

        //登记在线用户
        IMServerContext context = IMServerContext.getInstance();
        context.addUserSession(userId,session);

        //推送离线消息
        OfflineMessagePusher pusher = new OfflineMessagePusher(IMServerContext.getInstance().getMessageStore(),userId,session);
        context.getThreadPoolExecutor().submit(pusher);

        //返回
        RESULT result = new RESULT();
        result.put("status",200);
        service.setType("result");
        service.setResult(result);
        return service;
    }
}
