package com.lamfire.test;

import com.lamfire.hydra.MessageFactory;
import com.lamfire.hydra.Session;
import com.lamfire.jspp.JSPPUtils;
import com.lamfire.jspp.MESSAGE;
import com.lamfire.jsppserver.JSPPServer;
import com.lamfire.jsppserver.MessageHandler;
import com.lamfire.logger.Logger;
import com.lamfire.utils.OPSMonitor;

/**
 * Created by linfan on 2017/9/12.
 */
public class JSPPServerTest implements MessageHandler {
    private static final Logger LOGGER = Logger.getLogger(JSPPServerTest.class);

    OPSMonitor monitor = new OPSMonitor("1");

    public void onMessage(Session session, MESSAGE message) {
        //System.out.println(message);
        message.put("timestamp",System.currentTimeMillis());
        session.send(MessageFactory.message(0, JSPPUtils.encode(message)));
        monitor.done();
    }

    public void startupMonitor(){
        monitor.debug(true);
        monitor.startup();
    }

    public static void main(String[] args) {
        JSPPServerTest test = new JSPPServerTest();
        test.startupMonitor();

        JSPPServer server = new JSPPServer();
        server.setBind("0.0.0.0");
        server.setPort(9001);
        server.setMessageHandler(test);
        server.startup();
        LOGGER.info("Server startup on - " + server.getBind() +":" + server.getPort());
    }
}
