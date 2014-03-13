package im.jspp.iqserver.bootstrap;


import im.jspp.iqserver.IQServer;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-2-17
 * Time: 上午11:22
 * To change this template use File | Settings | File Templates.
 */
public class IQServerBootstrap {
    public static void main(String[] args) {
        IQServer server = new IQServer("0.0.0.0",6001);
        server.bind();
    }
}
