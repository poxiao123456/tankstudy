package com.poxiao.tank;

/**
 * @author qinqi
 * @date 2020/11/20
 */
public class DefaultFireStrategy implements FireStrategy{
    @Override
    public void fire(Tank tank) {
        int bX = tank.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
        int bY = tank.y + Tank.HEIGHT/2 - Bullet.HEIGHT/2;

        new Bullet(bX,bY,tank.dir,tank.group,tank.gm);

        if(tank.group == Group.GOOD) {
            new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
        }
    }
}
