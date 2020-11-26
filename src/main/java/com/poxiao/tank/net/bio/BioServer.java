package com.poxiao.tank.net.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author qq
 * @date 2020/11/24
 */
public class BioServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(9999);
        System.out.println("server star success");
        Socket client = server.accept();
        System.out.println("client join in:"+client.getInetAddress().getHostAddress());


    }
}
