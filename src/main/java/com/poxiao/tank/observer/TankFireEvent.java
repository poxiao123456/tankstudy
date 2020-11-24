package com.poxiao.tank.observer;

import com.poxiao.tank.Tank;

/**
 * @author qq
 * @date 2020/11/24
 */
public class TankFireEvent {

    private Tank tank;

    public Tank getSource() {
        return tank;
    }

    public TankFireEvent(Tank tank) {
        this.tank = tank;
    }
}
