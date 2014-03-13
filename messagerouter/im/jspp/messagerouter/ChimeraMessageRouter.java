package im.jspp.messagerouter;

import com.lamfire.chimaera.OnMessageListener;
import com.lamfire.chimaera.Poller;
import com.lamfire.chimaera.client.ChimaeraCli;
import com.lamfire.logger.Logger;
import im.jspp.elements.JSPP;
import im.jspp.hydra.PacketUtils;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-2-20
 * Time: 上午10:42
 * To change this template use File | Settings | File Templates.
 */
public class ChimeraMessageRouter implements Router {
    private static final Logger LOGGER = Logger.getLogger(ChimeraMessageRouter.class);
    public static final String KEY_MESSAGE_ROUTER = "MESSAGE_ROUTER";
    private String host;
    private int port;
    private String domain;
    private ChimaeraCli cli;
    private Poller poller;
    private String maiaId;

    public ChimeraMessageRouter(String domain,String host,int port){
        this.domain = domain;
        this.host = host;
        this.port = port;
        cli = new ChimaeraCli();
        cli.setMaxConnections(1);
        cli.open(host,port);
        this.poller = cli.getPoller();
    }

    public void listen(String maiaId,OnMessageListener listener){
        this.maiaId = maiaId;
        this.poller.bind(KEY_MESSAGE_ROUTER,maiaId,listener);
        LOGGER.info("[LISTEN]:" + KEY_MESSAGE_ROUTER +"[" + maiaId +"]@"+ host +":" + port);
    }

    public void shutdown(){
        this.poller.unbind(KEY_MESSAGE_ROUTER,maiaId);
        cli.close();
    }

    public void route(JSPP jspp){
        if(jspp == null){
            return;
        }
        this.poller.publish(domain, maiaId, PacketUtils.encode(jspp));
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("[ROUTE]:" + jspp.toJSONString());
        }
    }
}
