package im.jspp.messagerouter;

import com.lamfire.chimaera.OnMessageListener;
import com.lamfire.logger.Logger;
import com.lamfire.utils.Maps;
import im.jspp.elements.JSPP;
import im.jspp.elements.TO;
import im.jspp.hydra.PacketUtils;
import im.jspp.messagerouter.conf.ChimaeraHost;
import im.jspp.messagerouter.conf.ConfigFileParser;

import java.util.Map;

/**
 * 消息路由服务器
 * User: lamfire
 * Date: 14-2-19
 * Time: 下午5:21
 * To change this template use File | Settings | File Templates.
 */
public class MessageRouterServer implements OnMessageListener {
    private static final Logger LOGGER = Logger.getLogger(MessageRouterServer.class);
    private static final MessageRouterServer INSTANCE = new MessageRouterServer();

    public static MessageRouterServer get(){
        return INSTANCE;
    }

    private final Map<String,Router> routers = Maps.newHashMap();
    private String maiaId;

    private MessageRouterServer(){

    }

    public void startup(){
        ConfigFileParser conf = ConfigFileParser.get() ;
        this.maiaId = conf.getId();
        Map<String, ChimaeraHost> map = conf.getChimaeraHostMap();
        for(Map.Entry<String,ChimaeraHost> e : map.entrySet()){
            ChimaeraHost host = e.getValue();
            ChimeraMessageRouter router = new ChimeraMessageRouter(host.getDomain(),host.getHost(),host.getPort());
            router.listen(this.maiaId,this);
            routers.put(host.getDomain(),router);
        }
    }

    public void shutdown(){
        for(Map.Entry<String,Router> e : routers.entrySet()){
            ChimeraMessageRouter router = (ChimeraMessageRouter) e.getValue();
            router.shutdown();
        }
    }

    public Router getMessageRouter(String domain){
        return routers.get(domain);
    }

    @Override
    public void onMessage(String id, byte[] bytes) {
        JSPP jspp = PacketUtils.decode(bytes);
        TO to = new TO(jspp.getTo());
        String domain = to.getDomain();
        Router router = getMessageRouter(domain);
        if(router == null){
            LOGGER.warn("MessageRouter["+domain+"] not found.ignore message:" + jspp.toJSONString());
            return;
        }
        router.route(jspp);
    }
}
