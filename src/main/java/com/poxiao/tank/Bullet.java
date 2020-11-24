package com.poxiao.tank;

import java.awt.*;

/**
 * @author qinqi
 * @date 2020/11/19
 */
public class Bullet extends BaseBullet{

    private int x;
    private int y;
    private static final int SPEED = 6;
    private Dir dir;
    private Group group = Group.BAD;
    Rectangle rectangle = new Rectangle();
    //TankFrame tankFrame;
    GameModel gm;
    public static int WIDTH = ResourceMgr.bulletD.getWidth();
    public static int HEIGHT = ResourceMgr.bulletD.getHeight();
    //private boolean living = true;


    public Bullet() {
    }

    public Bullet(int x, int y, Dir dir,Group group,GameModel gm) {
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

        gm.bullets.add(this);
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

//    public boolean isLiving() {
//        return living;
//    }
//
//    public void setLiving(boolean living) {
//        this.living = living;
//    }

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

        if(x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
            die();
        }
    }

    @Override
    public void collideWith(BaseTank tank) {
        if(this.group == tank.getGroup()) {
            return;
        }

        if(rectangle.intersects(tank.rectangle)) {
            tank.die();
            die();
            int eX = tank.getX() + Tank.WIDTH/2 - Explode.WIDTH/2;
            int eY = tank.getY() + Tank.HEIGHT/2 - Explode.HEIGHT/2;
            //tankFrame.explodes.add(new Explode(eX, eY, tankFrame));
            gm.explodes.add(gm.gf.createExplode(eX, eY, gm));
        }

    }

    private void die() {
        gm.bullets.remove(this);
    }

}
