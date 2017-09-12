package com.lamfire.test;

import com.lamfire.hydra.MessageFactory;
import com.lamfire.hydra.Session;
import com.lamfire.hydra.Snake;
import com.lamfire.hydra.SnakeBuilder;
import com.lamfire.jspp.JSPPUtils;
import com.lamfire.jspp.MESSAGE;

/**
 * Created by linfan on 2017/9/12.
 */
public class JSPPClient {

    public static void main(String[] args) {
        SnakeBuilder builder = new SnakeBuilder();
        builder.host("127.0.0.1");
        builder.port(9001);
        Snake snake = builder.build();
        snake.startup();

        MESSAGE message = new MESSAGE();
        message.setFrom("lamfire");
        message.setTo("hayash");
        message.setType("text");
        message.setBody("hello");

        Session session = snake.getSession();
        session.send(MessageFactory.message(0, JSPPUtils.encode(message)));
    }
}
