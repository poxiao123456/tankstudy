package com.poxiao.tank.net.netty;

import com.poxiao.tank.enums.MsgType;

import java.io.*;
import java.util.UUID;

/**
 * @author qq
 * @date 2020/11/27
 */
public class TankDieMsg extends Msg{

    UUID bulletId; //who killed me
    UUID id;
    public TankDieMsg(UUID id) {
        this.id = id;
    }

    public TankDieMsg() {

    }

    public TankDieMsg(UUID playerId, UUID id) {
        this.bulletId = playerId;
        this.id = id;
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
            //TODO:�ȶ�TYPE��Ϣ������TYPE��Ϣ����ͬ����Ϣ
            //�Թ���Ϣ����
            this.id = new UUID(dis.readLong(), dis.readLong());
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
		/*GameObject go = GameModel.findByUUID(id);
		if(go != null && go instanceof Player) {
			Player p = (Player)go;
			p.die();
		}*/
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName())
                .append("[")
                .append("uuid=" + id + " | ")
                .append("]");
        return builder.toString();
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankDie;
    }
}
