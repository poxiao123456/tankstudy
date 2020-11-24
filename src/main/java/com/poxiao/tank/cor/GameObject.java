package com.poxiao.tank.cor;

import java.awt.*;

/**
 * @author qq
 * @date 2020/11/24
 */
public abstract class GameObject {
    public int x, y;

    public abstract void paint(Graphics g);
    public abstract int getWidth();
    public abstract int getHeight();
}
