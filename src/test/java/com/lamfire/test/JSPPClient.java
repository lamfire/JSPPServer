package com.lamfire.test;

import com.lamfire.hydra.*;
import com.lamfire.hydra.netty.NettySession;
import com.lamfire.jspp.JSPP;
import com.lamfire.jspp.JSPPUtils;
import com.lamfire.jspp.MESSAGE;
import com.lamfire.jsppserver.DefaultJSPPCoder;
import com.lamfire.jsppserver.JSPPCoder;
import com.lamfire.jsppserver.JSPPSession;
import com.lamfire.jsppserver.JSPPSessionUtils;
import io.netty.channel.AbstractChannel;

/**
 * Created by linfan on 2017/9/12.
 */
public class JSPPClient implements MessageReceivedListener{

    public static void main(String[] args) {
        SnakeBuilder builder = new SnakeBuilder();
        builder.host("127.0.0.1");
        builder.port(9001);
        builder.messageReceivedListener(new JSPPClient());
        Snake snake = builder.build();
        snake.startup();

        MESSAGE message = new MESSAGE();
        message.setFrom("lamfire");
        message.setTo("hayash");
        message.setType("text");
        message.setBody("hello");

        JSPPSession jsppSession = JSPPSessionUtils.toJSPPSession(snake.getSession(),new DefaultJSPPCoder());


        //发送100个
        for(int i=0;i<100;i++) {
            jsppSession.send(message);
        }

    }

    public void onMessageReceived(Session session, Message message) {
        JSPP jspp = JSPPUtils.decode(message.content());
        //System.out.println(jspp);
        session.send(message);
    }
}
