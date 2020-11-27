package com.poxiao.tank.net.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author qq
 * @date 2020/11/26
 */
public class MsgEncoder extends MessageToByteEncoder<Msg> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Msg msg, ByteBuf buf) throws Exception {
        byte[] bytes = msg.toBytes();

        buf.writeInt(msg.getMsgType().ordinal());
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
    }
}
