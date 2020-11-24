package com.poxiao.tank.abstractFactory;

import com.poxiao.tank.*;
import com.poxiao.tank.cor.GameObject;
import com.poxiao.tank.util.Audio;
import com.poxiao.tank.util.ResourceMgr;

import java.awt.*;

/**
 * @author qinqi
 * @date 2020/11/23
 */
public class RectExplode extends GameObject {

    private static int HEIGHT = ResourceMgr.explodes[0].getHeight();
    private static int WIDTH = ResourceMgr.explodes[0].getWidth();
    private TankFrame tf = null;
    private int step = 0;



    public RectExplode(int x, int y) {
        this.x = x;
        this.y = y;

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
        GameModel.getInstance().remove(this);
    }
}
