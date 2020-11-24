package com.poxiao.tank;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qinqi
 * @date 2020/11/23
 */
public class GameModel {

    private static final GameModel gm = new GameModel();

    static GameModel getInstance() {
        return gm;
    }

    Tank myTank = new Tank(400,300, Dir.DOWN,Group.GOOD,this);
    List<BaseBullet> bullets = new ArrayList<>();
    List<BaseTank> tanks = new ArrayList<>();
    List<BaseExplode> explodes = new ArrayList<>();

    public GameFactory gf = new DefaultFactory();


    private GameModel() {
        int initTankCount = Integer.parseInt((String) PropertyMgr.get("initTankCount"));

        // 初始化敌方坦克
        for (int i = 0; i < initTankCount; i++) {
            //tanks.add(new Tank(50 + i * 80, 200, Dir.DOWN, Group.BAD, this));
            tanks.add(gf.createTank(50 + i * 80, 200, Dir.DOWN, Group.BAD, this));
        }
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.white);
        g.drawString("子弹的数量:" + bullets.size(), 10, 60);
        g.drawString("坦克的数量:" + tanks.size(), 10, 80);
        g.drawString("爆炸的数量:" + explodes.size(), 10, 100);
        g.setColor(c);

        myTank.paint(g);

        for (int i = 0; i < tanks.size(); i++) {
            tanks.get(i).paint(g);
        }


        for(int i=0; i<bullets.size(); i++) {
            bullets.get(i).paint(g);
        }

        for(int i=0; i<explodes.size(); i++) {
            explodes.get(i).paint(g);
        }

        for(int i=0; i<bullets.size(); i++) {
            for(int j = 0; j<tanks.size(); j++) {
                bullets.get(i).collideWith(tanks.get(j));
            }
        }
    }

    public Tank getMainTank() {
        return myTank;
    }
}
