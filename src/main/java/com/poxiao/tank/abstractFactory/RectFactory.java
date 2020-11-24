package com.poxiao.tank.abstractFactory;

import com.poxiao.tank.cor.GameObject;
import com.poxiao.tank.enums.Dir;
import com.poxiao.tank.enums.Group;

/**
 * @author qinqi
 * @date 2020/11/23
 */
public class RectFactory extends GameFactory {

    @Override
    public GameObject createTank(int x, int y, Dir dir, Group group) {
        return new RectTank(x, y, dir, group);
    }

    @Override
    public GameObject createExplode(int x, int y) {
        return new RectExplode(x, y);
    }

    @Override
    public GameObject createBullet(int x, int y, Dir dir, Group group) {
        return new RectBullet(x, y, dir, group);
    }
}
