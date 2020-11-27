package com.poxiao.tank.net.netty;

import com.poxiao.tank.GameModel;
import com.poxiao.tank.Tank;
import com.poxiao.tank.enums.MsgType;

import java.io.*;
import java.util.UUID;

/**
 * @author qq
 * @date 2020/11/26
 */
public class TankStopMsg extends Msg{

    public static final MsgType TYPE = MsgType.TankStop;

    UUID id;
    int x, y;

    public TankStopMsg(UUID id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public TankStopMsg() {

    }

    public TankStopMsg(Tank t) {
        this.id = t.getId();
        this.x = t.getX();
        this.y = t.getY();
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.writeInt(x);
            dos.writeInt(y);
            dos.flush();
            bytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(dos != null) {
                    dos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bytes;
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try {

            this.id = new UUID(dis.readLong(), dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void handle() {
        //if this msg is send by myself do nothing
        if (this.id.equals(GameModel.getInstance().getMainTank().getId())) {
            return;
        }

        Tank t = GameModel.getInstance().findByUUID(this.id);

        if (t != null) {
            t.setMoving(false);
            t.setX(this.x);
            t.setY(this.y);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName())
                .append("[")
                .append("uuid=" + id + " | ")
                .append("x=" + x + " | ")
                .append("y=" + y + " | ")
                .append("]");
        return builder.toString();
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankStop;
    }
}
