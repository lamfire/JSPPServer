package com.lamfire.test;

import com.lamfire.code.CRC32;
import com.lamfire.jspp.JSPPUtils;
import com.lamfire.jspp.MESSAGE;
import com.lamfire.utils.Bytes;
import com.lamfire.utils.RandomUtils;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Socket client sample
 * User: linfan
 * Date: 15-8-18
 * Time: 上午11:00
 */
public class SocketClient {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1",9001));
        OutputStream os = socket.getOutputStream();

        MESSAGE message = new MESSAGE();
        message.setFrom("lamfire");
        message.setTo("hayash");
        message.setType("text");
        message.setBody("hello");


        byte[] content = JSPPUtils.encode(message);
        os.write(Bytes.toBytes(content.length + 12));   //bodylength + headerlength
        os.write(Bytes.toBytes(0));                      //id
        os.write(Bytes.toBytes(content.length));        //bodylength
        os.write(Bytes.toBytes(0));                      //option
        os.write(content);                               //body
        os.flush();


    }
}
