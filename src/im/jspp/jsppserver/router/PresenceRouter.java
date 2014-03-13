package im.jspp.jsppserver.router;

import com.lamfire.hydra.Message;
import com.lamfire.hydra.PollerDestination;
import im.jspp.elements.PRESENCE;
import im.jspp.hydra.PacketUtils;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-2-17
 * Time: 上午10:28
 * To change this template use File | Settings | File Templates.
 */
public class PresenceRouter extends PollerDestination {
    public static final String KEY_NAME = "PRESENCE";
    public PresenceRouter(String host, int port) {
        super(host, port);
        this.setAutoConnectRetry(true);
        this.setAutoConnectRetryTime(10);
        this.setKeepAlive(true);
    }

    @Override
    public String getName() {
        return KEY_NAME;
    }

    public void route(PRESENCE p){
        Message m = new Message();
        m.setId(-1);
        m.setBody(PacketUtils.encode(p));
        this.forwardMessage(m);
    }
}
