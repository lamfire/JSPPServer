package im.jspp.messagerouter.conf;

import com.lamfire.logger.Logger;
import com.lamfire.utils.Maps;
import com.lamfire.utils.XMLParser;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-2-20
 * Time: 下午5:34
 * To change this template use File | Settings | File Templates.
 */
public class ConfigFileParser {
    private static final Logger LOGGER = Logger.getLogger(ConfigFileParser.class);
    public static final String XML_RESOURCE = "message-router.xml";

    private static ConfigFileParser INSTANCE;

    public static synchronized  ConfigFileParser get(){
        if(INSTANCE == null){
            try{
                INSTANCE = new ConfigFileParser();
            }catch (Exception e){
                LOGGER.error(e.getMessage(),e);
            }
        }
        return INSTANCE;
    }

    private String id;

    private Map<String,ChimaeraHost> chimaeraHostMap = Maps.newHashMap();

    private ConfigFileParser() throws IOException, XPathExpressionException {
        XMLParser parser = XMLParser.load(XML_RESOURCE, ConfigFileParser.class);
        this.id = parser.getNodeValue("/message-router/id");

        LOGGER.info("[CONFIG]: id=" +id);

        NodeList list = parser.getNodeList("/message-router/chimaera");
        if(list.getLength() == 0){
            return ;
        }

        for(int i=0;i< list.getLength();i++){
            Node node = list.item(i);
            String host = node.getAttributes().getNamedItem("host").getNodeValue();
            int port = Integer.parseInt(node.getAttributes().getNamedItem("port").getNodeValue());
            String domain = node.getAttributes().getNamedItem("domain").getNodeValue();

            ChimaeraHost setting = new ChimaeraHost();
            setting.setHost(host);
            setting.setPort(port);
            setting.setDomain(domain);
            chimaeraHostMap.put(domain,setting);

            LOGGER.info("[CONFIG]: found chimaera -> " +setting);
        }
    }

    public String getId() {
        return id;
    }

    public Map<String, ChimaeraHost> getChimaeraHostMap() {
        return chimaeraHostMap;
    }
}
