package com.poxiao.tank;

/**
 * @author qinqi
 * @date 2020/10/30
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
//        TankFrame tankFrame = new TankFrame();

//        int initTankCount = Integer.parseInt((String)PropertyMgr.get("initTankCount"));

//        //初始化敌方坦克
//        for(int i=0; i<initTankCount; i++) {
//            //tankFrame.tanks.add(new Tank(50 + i*80, 200, Dir.DOWN,Group.BAD, tankFrame));
//            tankFrame.tanks.add(tankFrame.gf.createTank(50 + i*80, 200, Dir.DOWN, Group.BAD, tankFrame));
//        }
        new Thread(()->new Audio("audio/war1.wav").loop()).start();


        while(true) {
            Thread.sleep(25);
//            tankFrame.repaint();
            TankFrame.getTf().repaint();
        }
    }
}
