package im.jspp.iqserver;

import com.lamfire.hydra.Message;
import com.lamfire.hydra.MessageContext;
import com.lamfire.hydra.Snake;
import com.lamfire.logger.Logger;
import im.jspp.elements.IQ;
import im.jspp.elements.JSPP;
import im.jspp.hydra.PacketUtils;

/**
 * IQ处理服务器
 * User: lamfire
 * Date: 14-2-17
 * Time: 上午10:40
 * To change this template use File | Settings | File Templates.
 */
public class IQServer extends Snake {
    private static final Logger LOGGER = Logger.getLogger(IQServer.class);

    static{
        IQRegistry.get();
    }

    public IQServer(String host, int port) {
        super(host, port);
    }

    @Override
    protected void handleMessage(MessageContext messageContext, Message message) {
        JSPP jspp = decode(message);
        if(!(jspp instanceof IQ)){
            LOGGER.error("Message was not of 'IQ',ignored - " + jspp);
            return ;
        }

        IQ iq = (IQ)jspp;
        IQTask task = new IQTask(messageContext,iq);
        task.run();
    }

    public JSPP decode(Message message) {
        return PacketUtils.decode(message);
    }
}
