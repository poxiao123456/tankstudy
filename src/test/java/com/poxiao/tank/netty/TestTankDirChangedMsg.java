package com.poxiao.tank.netty;

import com.poxiao.tank.enums.Dir;
import com.poxiao.tank.enums.MsgType;
import com.poxiao.tank.net.netty.MsgDecoder;
import com.poxiao.tank.net.netty.MsgEncoder;
import com.poxiao.tank.net.netty.TankStartMovingMsg;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * @author qq
 * @date 2020/11/27
 */
public class TestTankDirChangedMsg {

    @Test
    public void testEncoder() {
        EmbeddedChannel ch = new EmbeddedChannel();


        UUID id = UUID.randomUUID();
        TankStartMovingMsg msg = new TankStartMovingMsg(id, 5, 10, Dir.LEFT);
        ch.pipeline()
                .addLast(new MsgEncoder());

        ch.writeOutbound(msg);

        ByteBuf buf = (ByteBuf)ch.readOutbound();
        MsgType msgType = MsgType.values()[buf.readInt()];
        assertEquals(MsgType.TankStartMoving, msgType);

        int length = buf.readInt();
        assertEquals(28, length);

        UUID uuid = new UUID(buf.readLong(), buf.readLong());
        int x = buf.readInt();
        int y = buf.readInt();
        int dirOrdinal = buf.readInt();
        Dir dir = Dir.values()[dirOrdinal];

        assertEquals(5, x);
        assertEquals(10, y);
        assertEquals(Dir.LEFT, dir);
        assertEquals(id, uuid);
    }

    @Test
    public void testDecoder() {
        EmbeddedChannel ch = new EmbeddedChannel();


        UUID id = UUID.randomUUID();
        TankStartMovingMsg msg = new TankStartMovingMsg(id, 5, 10, Dir.LEFT);
        ch.pipeline()
                .addLast(new MsgDecoder());

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankStartMoving.ordinal());
        byte[] bytes = msg.toBytes();
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);

        ch.writeInbound(buf.duplicate());

        TankStartMovingMsg msgR = (TankStartMovingMsg)ch.readInbound();

        assertEquals(5, msgR.getX());
        assertEquals(10, msgR.getY());
        assertEquals(Dir.LEFT, msgR.getDir());
        assertEquals(id, msgR.getId());
    }
}
