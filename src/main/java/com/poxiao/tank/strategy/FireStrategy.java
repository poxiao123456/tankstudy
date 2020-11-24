package com.poxiao.tank.strategy;

import com.poxiao.tank.GameObject;

import java.io.Serializable;

/**
 * @author qinqi
 * @date 2020/11/20
 */
public interface FireStrategy extends Serializable {
    void fire(GameObject baseTank);
}
