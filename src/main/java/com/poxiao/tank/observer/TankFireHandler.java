package com.poxiao.tank.observer;

import com.poxiao.tank.Tank;

/**
 * @author qq
 * @date 2020/11/24
 */
public class TankFireHandler implements TankFireOberver{
    @Override
    public void actionOnFire(TankFireEvent e) {
        Tank t = e.getSource();
        t.fire();
    }
}
