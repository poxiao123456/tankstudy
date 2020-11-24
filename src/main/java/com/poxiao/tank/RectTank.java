package com.poxiao.tank;

import java.awt.*;
import java.util.Random;

/**
 * @author qinqi
 * @date 2020/11/23
 */
public class RectTank extends BaseTank{


    int x;
    int y;
    private static final int SPEED = 2;
    Dir dir;
    private boolean moving = true;
    TankFrame tankFrame;
    GameModel gm;
    private FireStrategy fireStrategy;
    public static int HEIGHT = ResourceMgr.goodTankU.getHeight();
    public static int WIDTH = ResourceMgr.goodTankU.getWidth();

    private Random random = new Random();


    public RectTank() {
    }

    public RectTank(int x, int y, Dir dir,Group group,GameModel gm) {
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

        int bX = this.x + RectTank.WIDTH / 2 - Bullet.WIDTH / 2;
        int bY = this.y + RectTank.HEIGHT / 2 - Bullet.HEIGHT / 2;

        Dir[] dirs = Dir.values();
        for (Dir dir : dirs) {
            gm.gf.createBullet(bX, bY, dir, group, gm);
        }

        if (group == Group.GOOD) {
            new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
        }
    }


    @Override
    public void die() {
        gm.tanks.remove(this);
    }
}
