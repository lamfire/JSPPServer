package im.jspp.elements;

import com.lamfire.utils.StringUtils;

import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 13-11-11
 * Time: 下午8:40
 * To change this template use File | Settings | File Templates.
 */
public class JID {
    private String id;
    private String domain;
    private String resource;

    public JID(){

    }

    public JID(String id, String domain){
        this.id = id;
        this.domain = domain;
    }

    public JID(String id, String domain,String resource){
        this.id = id;
        this.domain = domain;
        this.resource = resource;
    }

    public JID(String jid){
        if(StringUtils.isBlank(jid)){
            return ;
        }
        Pattern pattern = Pattern.compile("[@,/]+");
        String[] strs = pattern.split(jid);
        this.setId(strs[0]);
        if(strs.length >= 2){
            this.setDomain(strs[1]);
        }
        if(strs.length >= 3){
            this.setResource(strs[2]);
        }
    }

    public String getId() {
        return id;
    }

    public String getDomain() {
        return domain;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String toString(){
        if(StringUtils.isBlank(this.resource)){
            return String.format("%s@%s", id, domain);
        }
        return String.format("%s@%s/%s", id, domain,resource);
    }

    public static JID from(String jid){
        return new JID(jid);
    }
}
