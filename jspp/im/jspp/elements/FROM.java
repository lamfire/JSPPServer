package im.jspp.elements;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 13-11-11
 * Time: 下午8:40
 * To change this template use File | Settings | File Templates.
 */
public class FROM extends JID{

    public FROM() {
    }

    public FROM(String id, String domain) {
        super(id, domain);
    }

    public FROM(String id, String domain, String resource) {
        super(id, domain, resource);
    }

    public FROM(String jid) {
        super(jid);
    }
}
