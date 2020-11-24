package com.poxiao.tank;

import com.poxiao.tank.cor.GameObject;
import com.poxiao.tank.enums.Dir;
import com.poxiao.tank.enums.Group;
import com.poxiao.tank.util.ResourceMgr;

import java.awt.*;

/**
 * @author qinqi
 * @date 2020/11/19
 */
public class Bullet extends GameObject {

    private static final int SPEED = 6;
    private Dir dir;
    private Group group = Group.BAD;
    private Rectangle rectangle = new Rectangle();
    private GameModel gm;
    private static int WIDTH = ResourceMgr.bulletD.getWidth();
    private static int HEIGHT = ResourceMgr.bulletD.getHeight();

    public Bullet() {
    }

    public Bullet(int x, int y, Dir dir,Group group,GameModel gm) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.gm = gm;

        rectangle.x = x;
        rectangle.y = y;
        rectangle.width = WIDTH;
        rectangle.height = HEIGHT;

        gm.add(this);
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

    public boolean collideWith(Tank tank) {
        if(this.group == tank.getGroup()) {
            return false;
        }

        if(rectangle.intersects(tank.getRectangle())) {
            tank.die();
            die();
            int eX = tank.getX() + Tank.getWIDTH()/2 - Explode.getWIDTH()/2;
            int eY = tank.getY() + Tank.getHEIGHT()/2 - Explode.getHEIGHT()/2;
            new Explode(eX, eY, gm);
            return true;
        }
        return false;
    }

    private void die() {
        gm.remove(this);
    }

}