package com.poxiao.tank.decorator;

import com.poxiao.tank.GameObject;

import java.awt.*;

/**
 * @author qq
 * @date 2020/11/24
 */
public abstract class Decorator extends GameObject {

    protected GameObject go;

    public Decorator(GameObject go) {
        this.go = go;
    }

    @Override
    public abstract void paint(Graphics g);
}
