package com.poxiao.tank;

import com.poxiao.tank.net.netty.Client;
import com.poxiao.tank.util.Audio;

/**
 * @author qq
 * @date 2020/10/30
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {

        new Thread(()->new Audio("audio/war1.wav").loop()).start();

        new Thread(()-> {
            while(true) {
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                TankFrame.getInstance().repaint();
            }
        }).start();

        //or you can new a thread to run this
        Client.INSTANCE.connect();
    }
}
