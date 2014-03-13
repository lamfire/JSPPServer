package im.jspp.jsppserver;

import com.lamfire.logger.Logger;
import com.lamfire.utils.PropertiesUtils;
import com.lamfire.utils.TypeConvertUtils;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-2-19
 * Time: 下午2:08
 * To change this template use File | Settings | File Templates.
 */
public class Configure {
    private static final Logger LOGGER = Logger.getLogger(Configure.class);
    private static final Configure instance = new Configure();

    public static Configure get(){
        return instance;
    }

    private String domain;

    private String bind ;
    private int port ;

    private String iqHost ;
    private int iqPort;

    private String messageHost ;
    private int messagePort;

    private String presenceHost ;
    private int presencePort;

    private Configure(){
        Properties prop = PropertiesUtils.load("jsppserver.properties", Configure.class);
        LOGGER.info("[CONFIGURE]:" + prop);
        this.domain = prop.getProperty("domain");

        this.bind = prop.getProperty("bind");
        this.port = TypeConvertUtils.intValue(prop.getProperty("port"));

        this.iqHost = prop.getProperty("router.iq.host");
        this.iqPort = TypeConvertUtils.intValue(prop.getProperty("router.iq.port"));

        this.messageHost = prop.getProperty("router.message.host");
        this.messagePort = TypeConvertUtils.intValue(prop.getProperty("router.message.port"));

        this.presenceHost = prop.getProperty("router.presence.host");
        this.presencePort = TypeConvertUtils.intValue(prop.getProperty("router.presence.port"));
    }

    public String getBind() {
        return bind;
    }

    public int getPort() {
        return port;
    }

    public String getIqHost() {
        return iqHost;
    }

    public int getIqPort() {
        return iqPort;
    }

    public String getMessageHost() {
        return messageHost;
    }

    public int getMessagePort() {
        return messagePort;
    }

    public String getPresenceHost() {
        return presenceHost;
    }

    public int getPresencePort() {
        return presencePort;
    }

    public String getDomain(){
        return this.domain;
    }
}
