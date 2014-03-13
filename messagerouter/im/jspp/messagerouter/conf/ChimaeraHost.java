package im.jspp.messagerouter.conf;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-2-20
 * Time: 下午5:44
 * To change this template use File | Settings | File Templates.
 */
public class ChimaeraHost {
    private String domain;
    private String host;
    private int port;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "ChimaeraHost{" +
                "domain='" + domain + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
