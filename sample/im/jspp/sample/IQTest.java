package im.jspp.sample;

import com.lamfire.hydra.reply.ReplyFuture;
import com.lamfire.hydra.reply.ReplySnake;
import im.jspp.elements.*;
import im.jspp.hydra.PacketUtils;


/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-2-18
 * Time: 上午9:47
 * To change this template use File | Settings | File Templates.
 */
public class IQTest {
    ReplySnake snake = new ReplySnake("127.0.0.1",6000);

    IQTest(){
        snake.connect();
    }

    public void testAuth(){
        IQ iq = new IQ();
        iq.setNs("user.auth");
        iq.setId("1203123");

        ARGS a = new ARGS();
        a.put("accounts","admin");
        a.put("password","123456");
        iq.setArgs(a);

        System.out.println("[Auth]:" + iq);
        ReplyFuture future = snake.send(PacketUtils.encode(iq)) ;
        byte[] bytes = future.getReply();
        JSPP jspp = PacketUtils.decode(bytes);
        System.out.println("[Auth-Result]:" + jspp);
    }

    public void testRegister(){
        IQ iq = new IQ();
        iq.setNs("user.register");
        iq.setId("1203123");

        ARGS a = new ARGS();
        a.put("accounts","lamfire");
        a.put("password","123456");
        a.put("nickname","lamfire");
        a.put("gender",1);
        iq.setArgs(a);

        System.out.println("[Register]:" + iq);
        ReplyFuture future = snake.send(PacketUtils.encode(iq)) ;
        byte[] bytes = future.getReply();
        JSPP jspp = PacketUtils.decode(bytes);
        System.out.println("[Register-Result]:" + jspp);
    }

    public void testPreformance() {
        IQ iq = new IQ();
        iq.setNs("user.auth");
        iq.setId("1203123");
        ARGS a = new ARGS();
        a.put("accounts","lamfire");
        a.put("password","123456");
        iq.setArgs(a);

        int count = 0;
        long startAt = System.currentTimeMillis();
        while(true){
            iq.put("serial",count);
            ReplyFuture future = snake.send(PacketUtils.encode(iq)) ;
            byte[] bytes = future.getReply();
            if((++count) % 1000 == 0){
                System.out.println(count +" - " + new String(bytes) +" " + ( System.currentTimeMillis() - startAt));
                startAt = System.currentTimeMillis();
            }
        }
    }

    public static void main(String[] args) {
        IQTest test = new IQTest();
        test.testRegister();
        //test.testAuth();
        //test.testPreformance();
    }
}
