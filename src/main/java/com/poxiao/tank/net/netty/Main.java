package com.poxiao.tank.net.netty;

import com.poxiao.tank.TankFrame;
import com.poxiao.tank.util.Audio;

/**
 * @author qq
 * @date 2020/11/26
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = TankFrame.getInstance();

        new Thread(()->new Audio("audio/war1.wav").loop()).start();

        while(true) {
            Thread.sleep(25);
            tf.repaint();
        }

    }
}
