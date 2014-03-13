package im.jspp.sample;

import com.lamfire.hydra.Message;
import com.lamfire.hydra.MessageContext;
import com.lamfire.hydra.net.Session;
import com.lamfire.hydra.reply.OnReceivedPushMessageListener;
import com.lamfire.hydra.reply.ReplyFuture;
import com.lamfire.hydra.reply.ReplySnake;
import com.lamfire.utils.Threads;
import im.jspp.elements.*;
import im.jspp.hydra.PacketUtils;

import java.util.Iterator;


/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-2-18
 * Time: 上午9:47
 * To change this template use File | Settings | File Templates.
 */
public class MESSAGETest implements OnReceivedPushMessageListener {
    public static void main(String[] args) {
        ReplySnake snake = new ReplySnake("127.0.0.1",6000);
        snake.setOnReceivedPushMessageListener(new MESSAGETest());
        snake.connect();

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

        int count = 0;
        long startAt = System.currentTimeMillis();
        Iterator<Session> it = snake.getPollerSessionIterator();
        while(true){
            m.put("iid",count);
            Session session = it.next();
            if(session.getSendBufferedSize()  > 10000){
                Threads.sleep(1000);
            }
            PacketUtils.send(session,count,m);
            if((++count) % 10000 == 0){
                System.out.println(count +" - " +m +" " + ( System.currentTimeMillis() - startAt));
                startAt = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void onReceivedPushMessage(MessageContext context, Message message) {
        JSPP jspp = PacketUtils.decode(message);
        System.out.println("[RECEIVED_PUSH]:" +jspp);
    }
}
