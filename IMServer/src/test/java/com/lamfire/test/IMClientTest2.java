package com.lamfire.test;

import com.lamfire.code.PUID;
import com.lamfire.hydra.*;
import com.lamfire.jspp.*;
import com.lamfire.jsppserver.DefaultJSPPCoder;
import com.lamfire.jsppserver.JSPPCoder;
import com.lamfire.jsppserver.JSPPSession;
import com.lamfire.jsppserver.JSPPSessionUtils;

/**
 * Created by linfan on 2017/9/28.
 */
public class IMClientTest2 implements MessageReceivedListener{

        public static void main(String[] args) {
            SnakeBuilder builder = new SnakeBuilder();
            builder.host("127.0.0.1");
            builder.port(9999);
            builder.messageReceivedListener(new IMClientTest2());
            Snake snake = builder.build();
            snake.startup();

            SERVICE service = new SERVICE();
            service.setFrom("hayash");
            service.setType("set");
            service.setNs("user.auth");

            ARGS params = new ARGS();
            params.put("userId","hayash");
            params.put("token", PUID.puidAsString());
            service.setArgs(params);


            JSPPSession jsppSession = JSPPSessionUtils.toJSPPSession(snake.getSession(),new DefaultJSPPCoder());

            //发送
            jsppSession.send(service);
        }

        public void onMessageReceived(Session session, Message message) {
            System.out.println("[RECEIVED] : " + new String(message.content()));
            JSPP jspp = JSPPUtils.decode(message.content());
            if(JSPPUtils.getProtocolType(jspp) == ProtocolType.SERVICE){
                SERVICE service = (SERVICE) jspp;
                if(service.getNs().equals("user.auth")){
                    if(service.getResult().getInteger("status")==200){
                        onAuthSuccess(session,service);
                    }
                }
            }
        }

    private void onAuthSuccess(Session session,SERVICE service){
        MESSAGE message = new MESSAGE();
        message.setFrom(service.getTo());
        message.setTo("lamfire");
        message.setType("text");
        message.setBody("hello");

        JSPPSession jsppSession = JSPPSessionUtils.toJSPPSession(session,new DefaultJSPPCoder());
        //发送
        jsppSession.send(message);
    }
}
