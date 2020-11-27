package com.poxiao.tank.net.netty;

import com.poxiao.tank.GameModel;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author qq
 * @date 2020/11/26
 */
public class Client {


    int i = 0;

    public static final Client INSTANCE = new Client();

    private Channel channel = null;

    public Client() {
    }

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
                        ServerFrame.INSTANCE.updateClientMsg((i++)+":client not connected!---"+future.channel().localAddress().toString());
                    } else {
                        ServerFrame.INSTANCE.updateClientMsg((i++)+":client connected!---"+future.channel().localAddress().toString());
                        // initialize the channel
                        channel = future.channel();
                    }
                }
            });

            f.sync();
            // wait until close
            f.channel().closeFuture().sync();
            System.out.println("connection closed!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public void send(Msg msg) {
        channel.writeAndFlush(msg);
    }

    public static void main(String[] args) throws Exception {
        Client c = new Client();
        c.connect();
    }

    public void closeConnect() {
        //this.send("_bye_");
        //channel.close();
    }
}

class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new MsgEncoder())
                .addLast(new MsgDecoder())
                .addLast(new ClientHandler());
    }

}

/**
 * SimpleChannelInboundHandler可以指定消息类型
 */
class ClientHandler extends SimpleChannelInboundHandler<Msg> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        msg.handle();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(new TankJoinMsg(GameModel.getInstance().getMainTank()));
    }
}
