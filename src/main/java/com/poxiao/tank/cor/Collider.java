package com.poxiao.tank.cor;

import com.poxiao.tank.GameObject;

/**
 * @author qq
 * @date 2020/11/24
 */
public  interface Collider {
    boolean collide(GameObject o1, GameObject o2);
}
