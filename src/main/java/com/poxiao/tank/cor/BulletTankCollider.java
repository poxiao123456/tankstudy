package com.poxiao.tank.cor;

import com.poxiao.tank.Bullet;
import com.poxiao.tank.Explode;
import com.poxiao.tank.GameObject;
import com.poxiao.tank.Tank;

/**
 * @author qq
 * @date 2020/11/24
 */
public class BulletTankCollider implements Collider{

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if(o1 instanceof Bullet && o2 instanceof Tank) {
            Bullet b = (Bullet)o1;
            Tank t = (Tank)o2;

            //TODO copy code from method collideWith
            if(b.getGroup() == t.getGroup()) {
                return true;
            }

            if(b.getRectangle().intersects(t.getRectangle())) {
                t.die();
                b.die();
                int eX = t.getX() + Tank.getWIDTH()/2 - Explode.getWIDTH()/2;
                int eY = t.getY() + Tank.getHEIGHT()/2 - Explode.getHEIGHT()/2;
                new Explode(eX, eY);
                return false;
            }

        } else if (o1 instanceof Tank && o2 instanceof Bullet) {
            return collide(o2, o1);
        }

        return true;

    }
}
