package com.poxiao.tank.abstractFactory;

import com.poxiao.tank.*;
import com.poxiao.tank.cor.GameObject;
import com.poxiao.tank.enums.Dir;
import com.poxiao.tank.enums.Group;

/**
 * @author qinqi
 * @date 2020/11/23
 */
public class RectFactory extends GameFactory {

    @Override
    public GameObject createTank(int x, int y, Dir dir, Group group, GameModel gm) {
        return new RectTank(x, y, dir, group, gm);
    }

    @Override
    public GameObject createExplode(int x, int y, GameModel gm) {
        return new RectExplode(x, y, gm);
    }

    @Override
    public GameObject createBullet(int x, int y, Dir dir, Group group, GameModel gm) {
        return new RectBullet(x, y, dir, group, gm);
    }
}
