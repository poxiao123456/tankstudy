package com.poxiao.tank.net.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author qq
 * @date 2020/11/26
 */
public class Server {

    int i = 0;

    public static void main(String[] args){
        Server server = new Server();
        server.serverStart();
    }

    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public void serverStart() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);

        try {
            ServerBootstrap b = new ServerBootstrap();
            ChannelFuture f = b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pl = ch.pipeline();
                            pl
                                    .addLast(new MsgEncoder())
                                    .addLast(new MsgDecoder())
                                    .addLast(new ServerChildHandler());
                        }
                    })
                    .bind(8888)
                    .sync();

            ServerFrame.INSTANCE.updateServerMsg((i++)+":server started!");

            f.channel().closeFuture().sync(); //close()->ChannelFuture
            ServerFrame.INSTANCE.updateServerMsg((i++)+":server closed!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }


    class ServerChildHandler extends ChannelInboundHandlerAdapter { //SimpleChannleInboundHandler Codec

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            Server.clients.add(ctx.channel());
            ServerFrame.INSTANCE.updateServerMsg((i++)+":client online!---"+getClientIp(ctx));
        }

        private String getClientIp(ChannelHandlerContext ctx) {
            return ctx.channel().remoteAddress().toString();
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ServerFrame.INSTANCE.updateServerMsg((i++)+":receive client msg---"+msg.toString());
            Server.clients.writeAndFlush(msg);
//        try {
//            TankJoinMsg tm = (TankJoinMsg)msg;
//            ServerFrame.INSTANCE.updateServerMsg("server receive msg!---"+getClientIp(ctx)+"---"+tm.toString());
//        } finally {
//            ReferenceCountUtil.release(msg);
//        }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            Server.clients.remove(ctx.channel());
            ServerFrame.INSTANCE.updateServerMsg((i++)+":client happen error!---"+getClientIp(ctx)+"---"+cause.getMessage());
            ctx.close();
        }
    }

}
