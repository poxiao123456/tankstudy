package com.poxiao.tank;

import com.poxiao.tank.abstractFactory.DefaultFactory;
import com.poxiao.tank.abstractFactory.GameFactory;
import com.poxiao.tank.cor.ColliderChain;
import com.poxiao.tank.enums.Dir;
import com.poxiao.tank.enums.Group;
import com.poxiao.tank.util.PropertyMgr;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qinqi
 * @date 2020/11/23
 */
public class GameModel {

    private static final GameModel INSTANCE = new GameModel();
    private Tank myTank;
    private List<GameObject> objects = new ArrayList<>();
    private ColliderChain chain = new ColliderChain();
    public GameFactory gf = new DefaultFactory();

    static {
        INSTANCE.init();
    }

    private GameModel() {
    }

    private void init() {
        // 初始化主战坦克
        myTank = new Tank(200, 400, Dir.DOWN, Group.GOOD);

        int initTankCount = Integer.parseInt((String) PropertyMgr.get("initTankCount"));

        // 初始化敌方坦克
        for (int i = 0; i < initTankCount; i++) {
            new Tank(50 + i * 80, 200, Dir.DOWN, Group.BAD);
        }

        // 初始化墙
        add(new Wall(150, 150, 200, 50));
        add(new Wall(550, 150, 200, 50));
        add(new Wall(300, 300, 50, 200));
        add(new Wall(550, 300, 50, 200));
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

    public void save() {
        File f = new File("C:\\Users\\25311\\Desktop\\tank.data");
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(myTank);
            oos.writeObject(objects);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void load() {
        File f = new File("C:\\Users\\25311\\Desktop\\tank.data");
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            myTank = (Tank)ois.readObject();
            objects = (List)ois.readObject();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
