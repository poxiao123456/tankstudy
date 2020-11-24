package com.poxiao.tank.cor;

import com.poxiao.tank.GameObject;
import com.poxiao.tank.Tank;
import com.poxiao.tank.Wall;

/**
 * @author qq
 * @date 2020/11/24
 */
public class TankWallCollider implements Collider{

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if(o1 instanceof Tank && o2 instanceof Wall) {
            Tank t = (Tank)o1;
            Wall w = (Wall)o2;


            if(t.getRectangle().intersects(w.getRect())) {
                t.back();
            }

        } else if (o1 instanceof Wall && o2 instanceof Tank) {
            return collide(o2, o1);
        }

        return true;

    }
}
