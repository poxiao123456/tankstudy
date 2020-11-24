package com.poxiao.tank.abstractFactory;

import java.awt.*;

/**
 * @author qinqi
 * @date 2020/11/23
 */
public abstract class BaseBullet {

    public abstract void paint(Graphics g);

    public abstract void collideWith(BaseTank tank);
}
