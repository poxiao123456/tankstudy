package com.poxiao.tank.net.netty;

import com.poxiao.tank.enums.MsgType;

/**
 * @author qq
 * @date 2020/11/26
 */
public abstract class Msg {

    //找到新的消息并且发送自己的位置
    public abstract void handle();
    public abstract byte[] toBytes();
    public abstract void parse(byte[] bytes);
    public abstract MsgType getMsgType();
}
