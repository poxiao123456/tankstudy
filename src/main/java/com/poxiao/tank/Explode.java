package com.poxiao.tank;

import com.poxiao.tank.util.Audio;
import com.poxiao.tank.util.ResourceMgr;

import java.awt.*;

/**
 * @author qinqi
 * @date 2020/11/19
 */
public class Explode extends GameObject {

    private static int HEIGHT = ResourceMgr.explodes[0].getHeight();
    private static int WIDTH = ResourceMgr.explodes[0].getWidth();
    private int step = 0;

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
        GameModel.getInstance().add(this);

        new Thread(()->new Audio("audio/explode.wav").play()).start();
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static void setHEIGHT(int HEIGHT) {
        Explode.HEIGHT = HEIGHT;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static void setWIDTH(int WIDTH) {
        Explode.WIDTH = WIDTH;
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

        g.drawImage(ResourceMgr.explodes[step++], x, y, null);

        if(step >= ResourceMgr.explodes.length) {
            die();
        }
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    private void die() {
        GameModel.getInstance().remove(this);
    }
}
