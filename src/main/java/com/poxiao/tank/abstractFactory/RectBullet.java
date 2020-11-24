package com.poxiao.tank.abstractFactory;

import com.poxiao.tank.*;
import com.poxiao.tank.cor.GameObject;
import com.poxiao.tank.enums.Dir;
import com.poxiao.tank.enums.Group;
import com.poxiao.tank.util.ResourceMgr;

import java.awt.*;

/**
 * @author qinqi
 * @date 2020/11/23
 */
public class RectBullet extends GameObject {

    private static final int SPEED = 6;
    private Dir dir;
    private Group group = Group.BAD;
    private Rectangle rectangle = new Rectangle();
    private TankFrame tankFrame;
    public static int WIDTH = ResourceMgr.bulletD.getWidth();
    public static int HEIGHT = ResourceMgr.bulletD.getHeight();


    public RectBullet() {
    }

    public RectBullet(int x, int y, Dir dir,Group group) {
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

        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, 20, 20);
        g.setColor(c);

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

    public void collideWith(BaseTank tank) {
        if(this.group == tank.getGroup()) {
            return;
        }

        if(rectangle.intersects(tank.rectangle)) {
            tank.die();
            die();
            int eX = tank.getX() + Tank.getWIDTH()/2 - Explode.getWIDTH()/2;
            int eY = tank.getY() + Tank.getHEIGHT()/2 - Explode.getHEIGHT()/2;

            GameModel.getInstance().add(new Explode(eX, eY));
        }

    }

    private void die() {
        GameModel.getInstance().remove(this);
    }
}
