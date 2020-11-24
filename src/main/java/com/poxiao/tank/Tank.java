package com.poxiao.tank;

import com.poxiao.tank.cor.GameObject;
import com.poxiao.tank.enums.Dir;
import com.poxiao.tank.enums.Group;
import com.poxiao.tank.strategy.DefaultFireStrategy;
import com.poxiao.tank.strategy.FireStrategy;
import com.poxiao.tank.util.PropertyMgr;
import com.poxiao.tank.util.ResourceMgr;

import java.awt.*;
import java.util.Random;

/**
 * @author qinqi
 * @date 2020/10/30
 */
public class Tank extends GameObject {

    private int oldX, oldY;
    private static final int SPEED = 2;
    private Dir dir;
    private boolean moving = true;
    private Group group = Group.BAD;
    private Rectangle rectangle = new Rectangle();
    private FireStrategy fireStrategy;
    private static int HEIGHT = ResourceMgr.goodTankU.getHeight();
    private static int WIDTH = ResourceMgr.goodTankU.getWidth();
    private Random random = new Random();


    public Tank() {
    }

    public Tank(int x, int y, Dir dir,Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;

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

        GameModel.getInstance().add(this);
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static void setHEIGHT(int HEIGHT) {
        Tank.HEIGHT = HEIGHT;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static void setWIDTH(int WIDTH) {
        Tank.WIDTH = WIDTH;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
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
        int nextInt = random.nextInt(2);
        switch(dir) {
            case LEFT:
                g.drawImage(this.group == Group.GOOD?
                        (nextInt == 1 ? ResourceMgr.goodTankL : ResourceMgr.goodTankL2) :
                        (nextInt == 1 ? ResourceMgr.badTankL : ResourceMgr.badTankL2), x, y, null);
                break;
            case UP:
                g.drawImage(this.group == Group.GOOD?
                        (nextInt == 1 ? ResourceMgr.goodTankU : ResourceMgr.goodTankU2) :
                        (nextInt == 1 ? ResourceMgr.badTankU : ResourceMgr.badTankU2), x, y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD?
                        (nextInt == 1 ? ResourceMgr.goodTankR : ResourceMgr.goodTankR2) :
                        (nextInt == 1 ? ResourceMgr.badTankR : ResourceMgr.badTankR2), x, y, null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.GOOD?
                        (nextInt == 1 ? ResourceMgr.goodTankD : ResourceMgr.goodTankD2) :
                        (nextInt == 1 ? ResourceMgr.badTankD : ResourceMgr.badTankD2), x, y, null);
                break;
                default :
                    break;
        }

        if (!moving) {
            return;
        }
        move();
    }

    private void move() {
        //记录移动之前的位置
        oldX = x;
        oldY = y;

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
        if (this.x > TankFrame.getGameWidth()- Tank.WIDTH -2) {
            x = TankFrame.getGameWidth() - Tank.WIDTH -2;
        }
        if (this.y > TankFrame.getGameHeight() - Tank.HEIGHT -2 ) {
            y = TankFrame.getGameHeight() -Tank.HEIGHT -2;
        }
    }

    private void randomDir() {

        this.dir = Dir.values()[random.nextInt(4)];
    }

    public void fire() {
        fireStrategy.fire(this);
    }

    public void die() {
        GameModel.getInstance().remove(this);
    }

    public void stop() {
        moving = false;
    }

    public void back() {
        x = oldX;
        y = oldY;
    }
}
