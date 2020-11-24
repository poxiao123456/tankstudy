package com.poxiao.tank.abstractFactory;

import com.poxiao.tank.Bullet;
import com.poxiao.tank.Explode;
import com.poxiao.tank.Tank;
import com.poxiao.tank.GameObject;
import com.poxiao.tank.enums.Dir;
import com.poxiao.tank.enums.Group;

/**
 * @author qinqi
 * @date 2020/11/23
 */
public class DefaultFactory extends GameFactory {

    @Override
    public GameObject createTank(int x, int y, Dir dir, Group group) {
        return new Tank(x, y, dir, group);
    }

    @Override
    public GameObject createExplode(int x, int y) {
        return new Explode(x, y);
    }

    @Override
    public GameObject createBullet(int x, int y, Dir dir, Group group) {
        return new Bullet(x, y, dir, group);
    }
}
