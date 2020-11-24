package com.poxiao.tank;
import	java.awt.event.KeyAdapter;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qinqi
 * @date 2020/10/30
 */
public class TankFrame extends Frame {

    GameModel gm = GameModel.getInstance();
    private static final TankFrame tf = new TankFrame();

    static TankFrame getTf() {
        return tf;
    }
    //    Tank myTank = new Tank(400,300, Dir.DOWN,Group.GOOD,this);
//    List<BaseBullet> bullets = new ArrayList<>();
//    List<BaseTank> tanks = new ArrayList<>();
//    List<BaseExplode> explodes = new ArrayList<>();
//
//    public GameFactory gf = new DefaultFactory();

    static final int GAME_WIDTH = 1080, GAME_HEIGHT = 960;

    private TankFrame() {
        setVisible(true);
        setTitle("tank-study");
        setSize(GAME_WIDTH,GAME_HEIGHT);
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("窗口关闭");
                System.exit(0);
            }
        });

        addKeyListener(new MyKeyListener());
    }

    @Override
    public void paint(Graphics g) {
//        Color c = g.getColor();
//        g.setColor(Color.white);
//        g.drawString("子弹的数量:" + bullets.size(), 10, 60);
//        g.drawString("坦克的数量:" + tanks.size(), 10, 80);
//        g.drawString("爆炸的数量:" + explodes.size(), 10, 100);
//        g.setColor(c);
//
//        myTank.paint(g);
//
//        for (int i = 0; i < tanks.size(); i++) {
//            tanks.get(i).paint(g);
//        }
//
//
//        for(int i=0; i<bullets.size(); i++) {
//            bullets.get(i).paint(g);
//        }
//
//        for(int i=0; i<explodes.size(); i++) {
//            explodes.get(i).paint(g);
//        }
//
//        for(int i=0; i<bullets.size(); i++) {
//            for(int j = 0; j<tanks.size(); j++) {
//                bullets.get(i).collideWith(tanks.get(j));
//            }
//        }

        gm.paint(g);
    }

    Image offScreenImage = null;
    @Override
    public void update(Graphics g) {
        if(offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    class MyKeyListener extends KeyAdapter{

        boolean bU = false;
        boolean bD = false;
        boolean bL = false;
        boolean bR = false;

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_UP:
                    bU = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    break;
                case KeyEvent.VK_LEFT:
                    bL = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    break;
                default:
                    break;
            }
            setMainTankDir();
            new Thread(()->new Audio("audio/tank_move.wav").play()).start();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_UP:
                    bU = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    break;
                case KeyEvent.VK_LEFT:
                    bL = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    break;
                case KeyEvent.VK_CONTROL:
                    gm.getMainTank().fire();
                    break;
                default:
                    break;
            }
            setMainTankDir();
        }

        private void setMainTankDir() {
            setTankMoving();
            if (bU) {
                gm.getMainTank().setDir(Dir.UP);
            }
            if (bD) {
                gm.getMainTank().setDir(Dir.DOWN);
            }
            if (bL) {
                gm.getMainTank().setDir(Dir.LEFT);
            }
            if (bR) {
                gm.getMainTank().setDir(Dir.RIGHT);
            }
        }

        private void setTankMoving() {
            if (!bU && !bD && !bL && !bR) {
                gm.getMainTank().setMoving(false);
            }else {
                gm.getMainTank().setMoving(true);
            }
        }
    }
}
