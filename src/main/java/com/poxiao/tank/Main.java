package com.poxiao.tank;

import com.poxiao.tank.util.Audio;

/**
 * @author qq
 * @date 2020/10/30
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {

        new Thread(()->new Audio("audio/war1.wav").loop()).start();

        while(true) {
            Thread.sleep(25);
            TankFrame.getInstance().repaint();
        }
    }
}
