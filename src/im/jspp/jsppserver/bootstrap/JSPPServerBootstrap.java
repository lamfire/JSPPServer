package im.jspp.jsppserver.bootstrap;


import im.jspp.jsppserver.JSPPServer;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-2-18
 * Time: 上午9:49
 * To change this template use File | Settings | File Templates.
 */
public class JSPPServerBootstrap {
    public static void main(String[] args) {
        JSPPServer server = JSPPServer.get();
        server.startup();
    }
}
