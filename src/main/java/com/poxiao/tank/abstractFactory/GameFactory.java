package com.poxiao.tank.abstractFactory;

import com.poxiao.tank.GameObject;
import com.poxiao.tank.enums.Dir;
import com.poxiao.tank.enums.Group;

/**
 * @author qq
 * @date 2020/11/23
 */
public abstract class GameFactory {

    public abstract GameObject createTank(int x, int y, Dir dir, Group group);
    public abstract GameObject createExplode(int x, int y);
    public abstract GameObject createBullet(int x, int y, Dir dir, Group group);
}
