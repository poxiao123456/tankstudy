package com.poxiao.tank.cor;

import com.poxiao.tank.GameObject;
import com.poxiao.tank.Tank;

/**
 * @author qq
 * @date 2020/11/24
 */
public class TankTankCollider implements Collider{

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if(o1 instanceof Tank && o2 instanceof Tank) {
            Tank t1 = (Tank)o1;
            Tank t2 = (Tank)o2;
            if(t1.getRectangle().intersects(t2.getRectangle())) {
                t1.back();
                t2.back();
            }

        }

        return true;

    }
}
