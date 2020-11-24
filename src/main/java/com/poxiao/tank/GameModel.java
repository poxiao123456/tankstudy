package com.poxiao.tank;

import com.poxiao.tank.abstractFactory.DefaultFactory;
import com.poxiao.tank.abstractFactory.GameFactory;
import com.poxiao.tank.cor.ColliderChain;
import com.poxiao.tank.cor.GameObject;
import com.poxiao.tank.enums.Dir;
import com.poxiao.tank.enums.Group;
import com.poxiao.tank.util.PropertyMgr;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qinqi
 * @date 2020/11/23
 */
public class GameModel {

    private static final GameModel INSTANCE = new GameModel();
    private Tank myTank = new Tank(400,300, Dir.DOWN, Group.GOOD,this);
    private List<GameObject> objects = new ArrayList<>();
    private ColliderChain chain = new ColliderChain();
    public GameFactory gf = new DefaultFactory();

    private GameModel() {
        int initTankCount = Integer.parseInt((String) PropertyMgr.get("initTankCount"));

        // 初始化敌方坦克
        for (int i = 0; i < initTankCount; i++) {
            add(new Tank(50 + i * 80, 200, Dir.DOWN, Group.BAD, this));
        }
    }

    public static GameModel getInstance() {
        return INSTANCE;
    }

    public void add(GameObject go) {
        this.objects.add(go);
    }

    public void remove(GameObject go) {
        this.objects.remove(go);
    }

    public Tank getMainTank() {
        return myTank;
    }

    public void paint(Graphics g) {
        myTank.paint(g);

        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).paint(g);
        }

        //互相碰撞
        for(int i=0; i<objects.size(); i++) {
            for(int j=i+1; j<objects.size(); j++) {
                GameObject o1 = objects.get(i);
                GameObject o2 = objects.get(j);
                chain.collide(o1, o2);
            }
        }
    }
}
