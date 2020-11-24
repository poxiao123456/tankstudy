package com.poxiao.tank.abstractFactory;

import com.poxiao.tank.*;
import com.poxiao.tank.cor.GameObject;
import com.poxiao.tank.enums.Dir;
import com.poxiao.tank.enums.Group;
import com.poxiao.tank.strategy.DefaultFireStrategy;
import com.poxiao.tank.strategy.FireStrategy;
import com.poxiao.tank.util.Audio;
import com.poxiao.tank.util.PropertyMgr;
import com.poxiao.tank.util.ResourceMgr;

import java.awt.*;
import java.util.Random;

/**
 * @author qinqi
 * @date 2020/11/23
 */
public class RectTank extends GameObject {

    private static final int SPEED = 2;
    private Dir dir;
    private boolean moving = true;
    private TankFrame tankFrame;
    private GameModel gm;
    private Group group = Group.BAD;
    private Rectangle rectangle = new Rectangle();
    private FireStrategy fireStrategy;
    private static int HEIGHT = ResourceMgr.goodTankU.getHeight();
    private static int WIDTH = ResourceMgr.goodTankU.getWidth();
    private Random random = new Random();


    public RectTank() {
    }

    public RectTank(int x, int y, Dir dir, Group group, GameModel gm) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.gm = gm;

        rectangle.x = x;
        rectangle.y = y;
        rectangle.width = WIDTH;
        rectangle.height = HEIGHT;

        if(group == Group.GOOD) {
            String goodFSName = (String) PropertyMgr.get("goodFS");

            try {
                fireStrategy = (FireStrategy)Class.forName(goodFSName).getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            fireStrategy = new DefaultFireStrategy();
        }
    }

    public TankFrame getTankFrame() {
        return tankFrame;
    }

    public void setTankFrame(TankFrame tankFrame) {
        this.tankFrame = tankFrame;
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

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(group == Group.GOOD ? Color.RED : Color.BLUE);
        g.fillRect(x, y, 40, 40);
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

        if(this.group == Group.BAD && random.nextInt(100) > 95) {
            this.fire();
        }

        if(this.group == Group.BAD && random.nextInt(100) > 95) {
            randomDir();
        }
        boundsCheck();
    }

    private void boundsCheck() {
        if(this.x < 2) {
            x = 2;
        }
        if (this.y < 28) {
            y = 28;
        }
        if (this.x > TankFrame.getGameWidth()- Tank.getWIDTH() -2) {
            x = TankFrame.getGameWidth() - Tank.getWIDTH() -2;
        }
        if (this.y > TankFrame.getGameHeight() - Tank.getHEIGHT() -2 ) {
            y = TankFrame.getGameHeight() -Tank.getHEIGHT() -2;
        }
    }

    private void randomDir() {

        this.dir = Dir.values()[random.nextInt(4)];
    }

    public void fire() {
        fireStrategy.fire(this);
    }

    public void die() {
        gm.remove(this);
    }
}
