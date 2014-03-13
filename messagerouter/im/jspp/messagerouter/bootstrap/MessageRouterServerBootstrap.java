package im.jspp.messagerouter.bootstrap;

import im.jspp.messagerouter.MessageRouterServer;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-2-21
 * Time: 上午9:59
 * To change this template use File | Settings | File Templates.
 */
public class MessageRouterServerBootstrap {
    public static void main(String[] args) {
        MessageRouterServer server = MessageRouterServer.get();
        server.startup();
    }
}
