package com.poxiao.tank.net.netty;

import com.poxiao.tank.enums.Dir;
import com.poxiao.tank.enums.Group;
import com.poxiao.tank.enums.MsgType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.UUID;

/**
 * @author qq
 * @date 2020/11/26
 */
public class MsgDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() < 8) {
            return;
        }

        in.markReaderIndex();

        //类型
        MsgType msgType = MsgType.values()[in.readInt()];
        int length = in.readInt();

        //长度
        if(in.readableBytes()< length) {
            in.resetReaderIndex();
            return;
        }

        //数据
        byte[] bytes = new byte[length];
        in.readBytes(bytes);

        Msg msg;

        //com.poxiao.tank.net.netty.TankDirChangedMsg
        msg = (Msg)Class.forName("com.poxiao.tank.net.netty." + msgType.toString() + "Msg").getDeclaredConstructor().newInstance();
//        switch(msgType) {
//            case TankJoin:
//                msg = new TankJoinMsg();
//                break;
//            case TankStartMoving:
//                msg = new TankStartMovingMsg();
//                break;
//            case TankStop:
//                msg = new TankStopMsg();
//                break;
//            default:
//                //未知消息类型处理
//                in.clear();
//                return;
//        }

        msg.parse(bytes);
        out.add(msg);
    }
}
