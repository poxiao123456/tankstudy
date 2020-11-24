package com.poxiao.tank;

/**
 * @author qinqi
 * @date 2020/11/23
 */
public class RectFactory extends GameFactory{

    @Override
    public BaseTank createTank(int x, int y, Dir dir, Group group, GameModel gm) {
        return new RectTank(x, y, dir, group, gm);
    }

    @Override
    public BaseExplode createExplode(int x, int y, GameModel gm) {
        return new RectExplode(x, y, gm);
    }

    @Override
    public BaseBullet createBullet(int x, int y, Dir dir, Group group, GameModel gm) {
        return new RectBullet(x, y, dir, group, gm);
    }
}
