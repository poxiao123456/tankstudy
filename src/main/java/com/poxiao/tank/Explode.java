package com.poxiao.tank;

import java.awt.*;

/**
 * @author qinqi
 * @date 2020/11/19
 */
public class Explode extends BaseExplode{

    private int x, y;
    public static int HEIGHT = ResourceMgr.explodes[0].getHeight();
    public static int WIDTH = ResourceMgr.explodes[0].getWidth();
    //TankFrame tf = null;
    GameModel gm;
    private int step = 0;


    //private boolean living = true;

    public Explode(int x, int y, GameModel gm) {
        this.x = x;
        this.y = y;
        //this.tf = tf;
        this.gm = gm;

        new Thread(()->new Audio("audio/explode.wav").play()).start();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

//    public boolean isLiving() {
//        return living;
//    }
//
//    public void setLiving(boolean living) {
//        this.living = living;
//    }

    @Override
    public void paint(Graphics g) {

        g.drawImage(ResourceMgr.explodes[step++], x, y, null);

        if(step >= ResourceMgr.explodes.length) {
            die();
        }
    }

    private void die() {
        gm.explodes.remove(this);
    }
}
