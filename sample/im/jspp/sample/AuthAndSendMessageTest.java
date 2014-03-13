package im.jspp.sample;

import com.lamfire.hydra.Message;
import com.lamfire.hydra.MessageContext;
import com.lamfire.hydra.reply.OnReceivedPushMessageListener;
import com.lamfire.hydra.reply.ReplyFuture;
import com.lamfire.hydra.reply.ReplySnake;
import im.jspp.elements.*;
import im.jspp.hydra.PacketUtils;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-2-27
 * Time: 下午4:17
 * To change this template use File | Settings | File Templates.
 */
public class AuthAndSendMessageTest {

    OnReceivedPushMessageListener listener = new OnReceivedPushMessageListener() {
        @Override
        public void onReceivedPushMessage(MessageContext context, Message message) {
            JSPP jspp = PacketUtils.decode(message);
            System.out.println("[RECEIVED_PUSH]:" +jspp);
            String to = jspp.getTo();
            String from = jspp.getFrom();

            jspp.setTo(from);
            jspp.setFrom(to);

            snake.send(PacketUtils.encode(jspp));
        }
    } ;


    ReplySnake snake = new ReplySnake("127.0.0.1",6000);

    AuthAndSendMessageTest(){
        snake.setOnReceivedPushMessageListener(listener);
        snake.connect();
    }

    public void auth(){
        IQ iq = new IQ();
        iq.setNs("user.auth");
        iq.setId("1203123");

        ARGS a = new ARGS();
        a.put("accounts","lamfire");
        a.put("password","123456");
        iq.setArgs(a);

        System.out.println("[Auth]:" + iq);
        ReplyFuture future = snake.send(PacketUtils.encode(iq)) ;
        byte[] bytes = future.getReply();
        JSPP jspp = PacketUtils.decode(bytes);
        System.out.println("[Auth-Result]:" + jspp);
    }

    public void sendMessage(){
        //创建JSPP消息
        MESSAGE m = new MESSAGE();
        m.setType(MESSAGE.TYPE_CHAT);
        m.setTo("5319c09801c7cbd12cc6dfec@s1.jspp.im");
        m.setFrom("531a855b01ffefe4b2f685fe@x61.jspp.im");
        m.setBody("hello");
        m.setId("100001");

        //设置附件
        ATTACH attach = new ATTACH();
        attach.setType(ATTACH.TYPE_IMAGE);
        attach.setBody("http://www.lamfire.com/1.jpg");
        m.setAttach(attach);

        snake.send(PacketUtils.encode(m)) ;
    }

    public static void main(String[] args) {
        AuthAndSendMessageTest test = new AuthAndSendMessageTest();
        test.auth();
        test.sendMessage();
    }
}
