package com.poxiao.tank;

import com.poxiao.tank.enums.Dir;
import com.poxiao.tank.enums.Group;
import com.poxiao.tank.util.ResourceMgr;

import java.awt.*;

/**
 * @author qq
 * @date 2020/11/19
 */
public class Bullet extends GameObject {

    private static final int SPEED = 6;
    private Dir dir;
    private Group group = Group.BAD;
    private Rectangle rectangle = new Rectangle();
    private static int WIDTH = ResourceMgr.bulletD.getWidth();
    private static int HEIGHT = ResourceMgr.bulletD.getHeight();

    public Bullet() {
    }

    public Bullet(int x, int y, Dir dir,Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;

        rectangle.x = x;
        rectangle.y = y;
        rectangle.width = WIDTH;
        rectangle.height = HEIGHT;

        GameModel.getInstance().add(this);
    }


    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static void setWIDTH(int WIDTH) {
        Bullet.WIDTH = WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static void setHEIGHT(int HEIGHT) {
        Bullet.HEIGHT = HEIGHT;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
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

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    @Override
    public void paint(Graphics g) {
        switch(dir) {
            case LEFT:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
                default:
                    break;
        }

        move();
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    private void move() {
        switch (dir) {
            case UP:
                y-=SPEED;
                break;
            case DOWN:
                y+=SPEED;
                break;
            case LEFT:
                x-=SPEED;
                break;
            case RIGHT:
                x+=SPEED;
                break;
            default:
                break;
        }

        rectangle.x = this.x;
        rectangle.y = this.y;

        if(x < 0 || y < 0 || x > TankFrame.getGameWidth() || y > TankFrame.getGameHeight()) {
            die();
        }
    }

    public void die() {
        GameModel.getInstance().remove(this);
    }

}
