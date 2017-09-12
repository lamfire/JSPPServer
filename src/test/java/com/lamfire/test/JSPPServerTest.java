package com.lamfire.test;

import com.lamfire.hydra.Session;
import com.lamfire.jspp.MESSAGE;
import com.lamfire.jsppserver.JSPPServer;
import com.lamfire.jsppserver.MessageHandler;
import com.lamfire.logger.Logger;

/**
 * Created by linfan on 2017/9/12.
 */
public class JSPPServerTest implements MessageHandler {
    private static final Logger LOGGER = Logger.getLogger(JSPPServerTest.class);
    public void onMessage(Session session, MESSAGE message) {
        System.out.println(message);
    }

    public static void main(String[] args) {
        JSPPServer server = new JSPPServer();
        server.setBind("0.0.0.0");
        server.setPort(9001);
        server.setMessageHandler(new JSPPServerTest());
        server.startup();
        LOGGER.info("Server startup on - " + server.getBind() +":" + server.getPort());
    }
}
