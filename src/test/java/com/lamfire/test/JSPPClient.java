package com.lamfire.test;

import com.lamfire.hydra.*;
import com.lamfire.hydra.netty.NettySession;
import com.lamfire.jspp.JSPP;
import com.lamfire.jspp.JSPPUtils;
import com.lamfire.jspp.MESSAGE;
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

        Session session = snake.getSession();

        session.send(MessageFactory.message(0, JSPPUtils.encode(message)));
        session.send(MessageFactory.message(0, JSPPUtils.encode(message)));
        session.send(MessageFactory.message(0, JSPPUtils.encode(message)));
        session.send(MessageFactory.message(0, JSPPUtils.encode(message)));
        session.send(MessageFactory.message(0, JSPPUtils.encode(message)));

    }

    public void onMessageReceived(Session session, Message message) {
        JSPP jspp = JSPPUtils.decode(message.content());
        //System.out.println(jspp);
        session.send(message);
    }
}
