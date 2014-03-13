package im.jspp.jsppserver;

import com.lamfire.chimaera.client.ChimaeraCli;
import com.lamfire.chimaera.store.FireQueue;
import im.jspp.elements.JID;
import im.jspp.elements.JSPP;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-2-28
 * Time: 下午2:49
 * To change this template use File | Settings | File Templates.
 */
public class OfflineMessageStore {

    private ChimaeraCli chimaeraCli;

    public OfflineMessageStore(ChimaeraCli chimaeraCli){
        this.chimaeraCli = chimaeraCli;
    }

    public void addMessage(JSPP jspp){
        JID jid = new JID(jspp.getTo());
        addMessage(jid,jspp.toJSONString().getBytes());
    }

    public void addMessage(JID jid ,byte[] data){
        FireQueue queue = this.chimaeraCli.getFireStore(jid.getDomain()).getFireQueue(jid.getId());
        queue.push(data);
    }

    public byte[] getMessage(JID jid){
        FireQueue queue = this.chimaeraCli.getFireStore(jid.getDomain()).getFireQueue(jid.getId());
        return queue.pop();
    }
}
