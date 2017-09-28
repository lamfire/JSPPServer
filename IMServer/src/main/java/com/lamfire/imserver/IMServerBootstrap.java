package com.lamfire.imserver;

import com.lamfire.utils.StringUtils;

/**
 * IMServerBootstrap
 * Created by linfan on 2017/9/28.
 */
public class IMServerBootstrap {

    public static void main(String[] args) {
        String host ="0.0.0.0";
        int port = 9999;
        int threads = 8;

        if(args != null){
            for(String arg : args){
                if(StringUtils.contains(arg, "-p")){
                    port = Integer.valueOf(StringUtils.substringAfter(arg,"-p"));
                }

                if(StringUtils.contains(arg,"-h")){
                    host = (StringUtils.substringAfter(arg, "-h"));
                }

                if(StringUtils.contains(arg,"-t")){
                    threads = Integer.valueOf(StringUtils.substringAfter(arg,"-t"));
                }
            }
        }

        IMServer server = new IMServer();
        server.setBind(host);
        server.setPort(port);
        server.setThreads(threads);
        server.startup();
    }
}
