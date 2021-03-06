package com.poxiao.tank.strategy;

import com.poxiao.tank.Bullet;
import com.poxiao.tank.GameObject;
import com.poxiao.tank.enums.Group;
import com.poxiao.tank.Tank;
import com.poxiao.tank.util.Audio;

/**
 * @author qinqi
 * @date 2020/11/20
 */
public class DefaultFireStrategy implements FireStrategy{
    @Override
    public void fire(GameObject gameObject) {
        Tank tank = (Tank) gameObject;
        int bX = tank.getX() + Tank.getWIDTH()/2 - Bullet.getWIDTH()/2;
        int bY = tank.getY() + Tank.getHEIGHT()/2 - Bullet.getHEIGHT()/2;

        new Bullet(bX,bY,tank.getDir(),tank.getGroup());

        //todo Bug? new Bullet把自己加了一遍
//        GameModel.getInstance().add(
//                new RectDecorator(
//                        new TailDecotator(
//                                new Bullet(bX, bY, tank.getDir(), tank.getGroup()))));

        if(tank.getGroup() == Group.GOOD) {
            new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
        }
    }
}
