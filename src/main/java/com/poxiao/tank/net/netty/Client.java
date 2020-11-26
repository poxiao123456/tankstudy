package com.poxiao.tank.net.netty;

import com.poxiao.tank.enums.Dir;
import com.poxiao.tank.enums.Group;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

import java.util.UUID;

/**
 * @author qq
 * @date 2020/11/26
 */
public class Client {

    private Channel channel = null;

    public void connect() {
        EventLoopGroup group = new NioEventLoopGroup(1);

        Bootstrap b = new Bootstrap();

        try {
            ChannelFuture f = b
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientChannelInitializer())
                    .connect("localhost", 8888);

            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (!future.isSuccess()) {
                        ServerFrame.INSTANCE.updateClientMsg("client not connected!---"+future.channel().localAddress().toString());
                    } else {
                        ServerFrame.INSTANCE.updateClientMsg("client connected!---"+future.channel().localAddress().toString());
                        // initialize the channel
                        channel = future.channel();
                    }
                }
            });

            f.sync();
            // wait until close
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public void send(String msg) {
        ByteBuf buf = Unpooled.copiedBuffer(msg.getBytes());
        channel.writeAndFlush(buf);
    }

    public static void main(String[] args) throws Exception {
        Client c = new Client();
        c.connect();
    }

    public void closeConnect() {
        this.send("_bye_");
        //channel.close();
    }
}

class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new TankJoinMsgEncoder())
                //.addLast(new TankJoinMsgDecoder())
                .addLast(new ClientHandler());
    }

}

class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = null;
        try {
            buf = (ByteBuf) msg;
            byte[] bytes = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(), bytes);
            String msgAccepted = new String(bytes);
            ServerFrame.INSTANCE.updateClientMsg("client receive msg---"+ctx.channel().localAddress().toString()+"---"+msgAccepted);
            // System.out.println(buf);
            // System.out.println(buf.refCnt());
        } finally {
            if (buf != null) {

                ReferenceCountUtil.release(buf);
            }
            // System.out.println(buf.refCnt());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //TankMsg tankMsg = new TankMsg(5, 8);
        TankJoinMsg tankMsg = new TankJoinMsg(5,8, Dir.UP,true, Group.GOOD, UUID.randomUUID());
        ctx.writeAndFlush(tankMsg);
        ServerFrame.INSTANCE.updateClientMsg("client send msg!---"+ctx.channel().localAddress().toString()+"---"+tankMsg);
    }
}
