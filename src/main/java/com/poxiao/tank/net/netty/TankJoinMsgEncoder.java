package com.poxiao.tank.net.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author qq
 * @date 2020/11/26
 */
public class TankJoinMsgEncoder extends MessageToByteEncoder<TankJoinMsg> {

    @Override
    protected void encode(ChannelHandlerContext ctx, TankJoinMsg msg, ByteBuf buf) throws Exception {
        buf.writeBytes(msg.toBytes());
    }
}
