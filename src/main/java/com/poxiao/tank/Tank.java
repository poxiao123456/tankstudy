package com.poxiao.tank;

import java.awt.*;
import java.util.Random;

/**
 * @author qinqi
 * @date 2020/10/30
 */
public class Tank extends BaseTank{

    int x;
    int y;
    private static final int SPEED = 2;
    Dir dir;
    private boolean moving = true;
    //Group group = Group.BAD;
    //Rectangle rectangle = new Rectangle();
    TankFrame tankFrame;
    GameModel gm;
    private FireStrategy fireStrategy;
    public static int HEIGHT = ResourceMgr.goodTankU.getHeight();
    public static int WIDTH = ResourceMgr.goodTankU.getWidth();

    //private boolean living = true;
    private Random random = new Random();


    public Tank() {
    }

    public Tank(int x, int y, Dir dir,Group group,GameModel gm) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        //this.tankFrame = tankFrame;
        this.gm = gm;

        rectangle.x = x;
        rectangle.y = y;
        rectangle.width = WIDTH;
        rectangle.height = HEIGHT;

        if(group == Group.GOOD) {
            String goodFSName = (String)PropertyMgr.get("goodFS");

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

    //    public Group getGroup() {
//        return group;
//    }
//
//    public void setGroup(Group group) {
//        this.group = group;
//    }

//    public boolean isLiving() {
////        return living;
////    }
////
////    public void setLiving(boolean living) {
////        this.living = living;
////    }

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
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
        if (this.x > TankFrame.GAME_WIDTH- Tank.WIDTH -2) {
            x = TankFrame.GAME_WIDTH - Tank.WIDTH -2;
        }
        if (this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT -2 ) {
            y = TankFrame.GAME_HEIGHT -Tank.HEIGHT -2;
        }
    }

    private void randomDir() {

        this.dir = Dir.values()[random.nextInt(4)];
    }

    public void fire() {
        //fireStrategy.fire(this);

        int bX = this.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
        int bY = this.y + Tank.HEIGHT/2 - Bullet.HEIGHT/2;

        Dir[] dirs = Dir.values();
        for(Dir dir : dirs) {
            gm.gf.createBullet(bX, bY, dir, group, gm);
        }

        if(group == Group.GOOD) {

            new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
        }
    }

    @Override
    public void die() {
        gm.tanks.remove(this);
    }
}
