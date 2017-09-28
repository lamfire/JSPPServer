package com.lamfire.imserver;

import com.lamfire.imserver.store.Store;
import com.lamfire.jsppserver.*;
import com.lamfire.logger.Logger;
import com.lamfire.utils.FileUtils;

/**
 * IMServer
 * Created by linfan on 2017/9/28.
 */
public class IMServer {
    private static final Logger LOGGER = Logger.getLogger(IMServer.class);

    private JSPPServer server = null;
    private String bind = "0.0.0.0";
    private int port = 9999;
    private int threads = 16;
    private String messageStoreDir = "/data/imserver/messages";
    private IMMessageStore messageStore;

    public synchronized void startup(){
        if(server != null){
            return;
        }

        LOGGER.info("[MESSAGE_STORE_DIR] -> " + messageStoreDir);
        messageStore = new Store(messageStoreDir);
        IMServerContext.getInstance().setMessageStore(messageStore);

        NSRegisteredServiceHandler serviceHandler = new NSRegisteredServiceHandler();
        serviceHandler.nsPackage(IMServer.class.getPackage().getName());
        server = new JSPPServer();
        server.setMessageHandler(new IMMessageHandler(messageStore));
        server.setServiceHandler(serviceHandler);
        server.setBind(bind);
        server.setPort(port);
        server.setThreads(threads);
        server.startup();
        LOGGER.info("[SERVER_STARTUP_ON] -> " + bind +":" + port);
    }

    public void shutdown(){
        if(server != null) {
            server.shutdown();
            server = null;
        }
    }

    public String getBind() {
        return bind;
    }

    public void setBind(String bind) {
        this.bind = bind;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getMessageStoreDir() {
        return messageStoreDir;
    }

    public void setMessageStoreDir(String messageStoreDir) {
        this.messageStoreDir = messageStoreDir;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }
}
