package com.poxiao.tank.decorator;

import com.poxiao.tank.cor.GameObject;

import java.awt.*;

/**
 * @author qq
 * @date 2020/11/24
 */
public class TailDecotator extends Decorator{

    public TailDecotator(GameObject go) {

        super(go);
    }

    @Override
    public void paint(Graphics g) {
        this.x = go.x;
        this.y = go.y;
        go.paint(g);

        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawLine(go.x, go.y, go.x + getWidth(), go.y + getHeight());
        g.setColor(c);
    }


    @Override
    public int getWidth() {
        return super.go.getWidth();
    }

    @Override
    public int getHeight() {
        return super.go.getHeight();
    }
}
