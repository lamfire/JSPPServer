package im.jspp.jsppserver.router;

import com.lamfire.hydra.PollerDestination;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-2-17
 * Time: 上午10:28
 * To change this template use File | Settings | File Templates.
 */
public class IQRouter extends PollerDestination {
    public static final String KEY_NAME = "IQ";
    public IQRouter(String host, int port) {
        super(host, port);
        this.setAutoConnectRetry(true);
        this.setAutoConnectRetryTime(10);
        this.setKeepAlive(true);
    }

    @Override
    public String getName() {
        return KEY_NAME;
    }
}
