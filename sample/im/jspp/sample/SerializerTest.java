package im.jspp.sample;

import im.jspp.elements.ARGS;
import im.jspp.elements.ATTACH;
import im.jspp.elements.IQ;
import im.jspp.elements.MESSAGE;
import im.jspp.hydra.PacketUtils;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-3-7
 * Time: 下午3:22
 * To change this template use File | Settings | File Templates.
 */
public class SerializerTest {

    public static IQ getIQ(){
        IQ iq = new IQ();
        iq.setNs("user.auth");
        iq.setId("1203123");

        ARGS a = new ARGS();
        a.put("accounts","admin");
        a.put("password","123456");
        iq.setArgs(a);

        return iq;
    }

    public static MESSAGE getMessage(){
        //创建JSPP消息
        MESSAGE m = new MESSAGE();
        m.setType(MESSAGE.TYPE_CHAT);
        m.setTo("530ee6ba015ffffe0ee68082@s1.jspp.im");
        m.setFrom("medusa@s1.jspp.im");
        m.setBody("hello");
        m.setId("100001");

        //设置附件
        ATTACH attach = new ATTACH();
        attach.setType(ATTACH.TYPE_IMAGE);
        attach.setBody("http://www.lamfire.com/1.jpg");
        m.setAttach(attach);

        //设置自定义属性
        m.put("icon", "http://www.lamfire.com/icon.jpg");

        return m;
    }

    public static void main(String[] args) {
        System.out.println(getIQ());
        System.out.println(getMessage());
    }
}
