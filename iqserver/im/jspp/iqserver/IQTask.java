package im.jspp.iqserver;


import com.lamfire.hydra.MessageContext;
import com.lamfire.logger.Logger;
import im.jspp.elements.ERROR;
import im.jspp.elements.IQ;
import im.jspp.hydra.PacketUtils;

/**
 * IQ处理任务
 * User: lamfire
 * Date: 13-11-18
 * Time: 上午10:56
 * To change this template use File | Settings | File Templates.
 */
public class IQTask implements Runnable{
    private static final Logger LOGGER = Logger.getLogger(IQTask.class);
    private MessageContext context;
    private IQ iq;

    public IQTask(MessageContext context, IQ iq){
        this.context = context;
        this.iq = iq;
    }

    @Override
    public void run() {
        try{
            String ns = iq.getNs();
            IQHandler service = IQRegistry.get().lookup(ns);
            if(service == null){
                String message = "No registered IQ '"+ns+"'";
                iq.setType(IQ.TYPE_ERROR);
                iq.setError(new ERROR(500,message));
                PacketUtils.send(context, iq);
                LOGGER.error(message);
                return;
            }
            IQ resultIQ = service.execute(iq);
            if(resultIQ != null){
                PacketUtils.send(context, resultIQ);
            }
        }catch (Throwable e){
            iq.setType(IQ.TYPE_ERROR);
            iq.setError(new ERROR(500,e.getMessage()));
            PacketUtils.send(context, iq);
            LOGGER.error(e.getMessage(),e);
        }
    }
}
