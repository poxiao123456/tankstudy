package com.poxiao.tank.net.netty;

import com.poxiao.tank.GameModel;
import com.poxiao.tank.Tank;
import com.poxiao.tank.enums.Dir;
import com.poxiao.tank.enums.Group;
import com.poxiao.tank.enums.MsgType;

import java.io.*;
import java.util.UUID;

/**
 * @author qq
 * @date 2020/11/26
 */
public class TankJoinMsg extends Msg{

    public int x, y;
    public Dir dir;
    public boolean moving;
    public Group group;
    public UUID id;

    public TankJoinMsg(Tank t) {
        this.x = t.getX();
        this.y = t.getY();
        this.dir = t.getDir();
        this.group = t.getGroup();
        this.id = t.getId();
        this.moving = t.isMoving();
    }

    public TankJoinMsg(int x, int y, Dir dir, boolean moving, Group group, UUID id) {
        super();
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.moving = moving;
        this.group = group;
        this.id = id;
    }

    public TankJoinMsg() {
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            //dis.readInt();

            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Dir.values()[dis.readInt()];
            this.moving = dis.readBoolean();
            this.group = Group.values()[dis.readInt()];
            this.id = new UUID(dis.readLong(), dis.readLong());
            //this.name = dis.readUTF();
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
    public MsgType getMsgType() {
        return MsgType.TankJoin;
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            //dos.writeInt(TYPE.ordinal());
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeBoolean(moving);
            dos.writeInt(group.ordinal());
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            //dos.writeUTF(name);
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
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName())
                .append("[")
                .append("uuid=" + id + " | ")
                //.append("name=" + name + " | ")
                .append("x=" + x + " | ")
                .append("y=" + y + " | ")
                .append("moving=" + moving + " | ")
                .append("dir=" + dir + " | ")
                .append("group=" + group + " | ")
                .append("]");
        return builder.toString();
    }

    @Override
    public void handle() {
        //找新加入的坦克
        if(this.id.equals(GameModel.getInstance().getMainTank().getId()) ||
                GameModel.getInstance().findByUUID(this.id) != null) {
            return;
        }
        //System.out.println(this);
        Tank t = new Tank(this);
        GameModel.getInstance().addTank(t);

        //发送自己的位置
        Client.INSTANCE.send(new TankJoinMsg(GameModel.getInstance().getMainTank()));
    }
}
