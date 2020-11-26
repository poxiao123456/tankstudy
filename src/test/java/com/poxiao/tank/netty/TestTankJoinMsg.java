package com.poxiao.tank.netty;

import com.poxiao.tank.enums.Dir;
import com.poxiao.tank.enums.Group;
import com.poxiao.tank.net.netty.TankJoinMsg;
import com.poxiao.tank.net.netty.TankJoinMsgDecoder;
import com.poxiao.tank.net.netty.TankJoinMsgEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * @author qq
 * @date 2020/11/26
 */
public class TestTankJoinMsg {

    @Test
    public void testEncoder() {
        EmbeddedChannel ch = new EmbeddedChannel();


        UUID id = UUID.randomUUID();
        TankJoinMsg msg = new TankJoinMsg(5, 10, Dir.DOWN, true, Group.BAD, id);
        ch.pipeline()
                .addLast(new TankJoinMsgEncoder());

        ch.writeOutbound(msg);

        ByteBuf buf = (ByteBuf)ch.readOutbound();

        int x = buf.readInt();
        int y = buf.readInt();
        int dirOrdinal = buf.readInt();
        Dir dir = Dir.values()[dirOrdinal];
        boolean moving = buf.readBoolean();
        int groupOrdinal = buf.readInt();
        Group g = Group.values()[groupOrdinal];
        UUID uuid = new UUID(buf.readLong(), buf.readLong());

        assertEquals(5, x);
        assertEquals(10, y);
        assertEquals(Dir.DOWN, dir);
        assertEquals(true, moving);
        assertEquals(Group.BAD, g);
        assertEquals(id, uuid);
    }

    @Test
    public void testDecoder() {
        EmbeddedChannel ch = new EmbeddedChannel();


        UUID id = UUID.randomUUID();
        TankJoinMsg msg = new TankJoinMsg(5, 10, Dir.DOWN, true, Group.BAD, id);
        ch.pipeline()
                .addLast(new TankJoinMsgDecoder());

        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(msg.toBytes());

        ch.writeInbound(buf.duplicate());

        TankJoinMsg msgR = (TankJoinMsg)ch.readInbound();



        assertEquals(5, msgR.x);
        assertEquals(10, msgR.y);
        assertEquals(Dir.DOWN, msgR.dir);
        assertEquals(true, msgR.moving);
        assertEquals(Group.BAD, msgR.group);
        assertEquals(id, msgR.id);
    }
}
