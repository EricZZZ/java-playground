package com.ericzzz.io.server;

import com.ericzzz.io.codec.PacketCodecHandler;
import com.ericzzz.io.codec.Spliter;
import com.ericzzz.io.server.handler.AuthHandler;
import com.ericzzz.io.server.handler.IMHandler;
import com.ericzzz.io.server.handler.LoginRequestHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyServer {

    private static final int PORT = 8000;

    public static void main(String[] args) {

        final ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        // 1. 必须先对 ServerBootstrap 进行完整配置
        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        // ch.pipeline().addLast(new ServerHandler());
                        // inBound，处理读数据的逻辑链
                        // ch.pipeline().addLast(new InBoundHandlerA());
                        // ch.pipeline().addLast(new InBoundHandlerB());
                        // ch.pipeline().addLast(new InBoundHandlerC());

                        // outBound，处理写数据的逻辑链
                        // ch.pipeline().addLast(new OutBoundHandlerA());
                        // ch.pipeline().addLast(new OutBoundHandlerB());
                        // ch.pipeline().addLast(new OutBoundHandlerC());

                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        ch.pipeline().addLast(AuthHandler.INSTANCE);
                        ch.pipeline().addLast(IMHandler.INSTANCE);
                    }
                });

        // 3. 配置完成后，再调用绑定方法
        bind(serverBootstrap, PORT);
    }

    /**
     * 3. 提取出的独立静态 bind 方法，用于实现端口冲突时自动递增重试
     */
    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) {
                if (future.isSuccess()) {
                    System.out.println("端口[" + port + "]绑定成功");
                } else {
                    System.err.println("端口[" + port + "]绑定失败");
                }
            }
        });
    }
}