package com.poxiao.tank;

import java.awt.*;

/**
 * @author qinqi
 * @date 2020/11/20
 */
public abstract class BaseTank {
    public Group group = Group.BAD;
    public Rectangle rectangle = new Rectangle();

    public abstract void paint(Graphics g);

    public Group getGroup() {
        return this.group;
    }

    public abstract void die();

    public abstract int getX();

    public abstract int getY();
}
