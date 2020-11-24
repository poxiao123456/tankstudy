package com.poxiao.tank;

import java.awt.*;
import java.io.Serializable;

/**
 * @author qq
 * @date 2020/11/24
 */
public abstract class GameObject implements Serializable {
    public int x, y;

    public abstract void paint(Graphics g);
    public abstract int getWidth();
    public abstract int getHeight();
}
