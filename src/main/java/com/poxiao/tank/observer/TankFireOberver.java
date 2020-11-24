package com.poxiao.tank.observer;

import java.io.Serializable;

/**
 * @author qq
 * @date 2020/11/24
 */
public interface TankFireOberver extends Serializable {

    void actionOnFire(TankFireEvent e);
}
