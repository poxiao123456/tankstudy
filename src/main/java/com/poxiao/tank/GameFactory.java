package com.poxiao.tank;

/**
 * @author qinqi
 * @date 2020/11/23
 */
public abstract class GameFactory {

    public abstract BaseTank createTank(int x, int y, Dir dir, Group group, GameModel gm);
    public abstract BaseExplode createExplode(int x, int y, GameModel gm);
    public abstract BaseBullet createBullet(int x, int y, Dir dir, Group group, GameModel gm);
}
