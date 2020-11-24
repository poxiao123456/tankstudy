package com.poxiao.tank;

import java.awt.*;

/**
 * @author qinqi
 * @date 2020/11/23
 */
public class RectExplode extends BaseExplode{

    private int x, y;
    public static int HEIGHT = ResourceMgr.explodes[0].getHeight();
    public static int WIDTH = ResourceMgr.explodes[0].getWidth();
    TankFrame tf = null;
    GameModel gm;
    private int step = 0;



    public RectExplode(int x, int y, GameModel gm) {
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

    @Override
    public void paint(Graphics g) {

//        g.drawImage(ResourceMgr.explodes[step++], x, y, null);
//
//        if(step >= ResourceMgr.explodes.length) {
//            die();
//        }
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillRect(x, y, 10*step, 10*step);
        step++;

        if(step >= 15) {
            die();
        }

        g.setColor(c);
    }

    private void die() {
        gm.explodes.remove(this);
    }
}
