package com.poxiao.tank.strategy;

import com.poxiao.tank.Bullet;
import com.poxiao.tank.GameModel;
import com.poxiao.tank.GameObject;
import com.poxiao.tank.enums.Dir;
import com.poxiao.tank.enums.Group;
import com.poxiao.tank.Tank;
import com.poxiao.tank.net.netty.BulletNewMsg;
import com.poxiao.tank.net.netty.Client;
import com.poxiao.tank.util.Audio;

/**
 * @author qinqi
 * @date 2020/11/20
 */
public class FourDirFireStrategy implements FireStrategy{
    @Override
    public void fire(GameObject gameObject) {
        Tank tank = (Tank) gameObject;
        int bX = tank.getX() + Tank.getWIDTH()/2 - Bullet.getWIDTH()/2;
        int bY = tank.getY() + Tank.getHEIGHT()/2 - Bullet.getHEIGHT()/2;

        Dir[] dirs = Dir.values();
        for (Dir dir : dirs) {
//            Bullet bullet = (Bullet)GameModel.getInstance().gf.createBullet(bX, bY, dir, tank.getGroup());
            Bullet bullet = new Bullet(tank.getId(), bX, bY, dir, tank.getGroup());
            GameModel.getInstance().addBullet(bullet);

            Client.INSTANCE.send(new BulletNewMsg(bullet));
        }

        if(tank.getGroup() == Group.GOOD) {
            new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
        }
    }
}
