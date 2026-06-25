package com.ericzzz.io;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyServer {
    public static void main(String[] args) {
        int port = 8000;
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        
        // 1. 必须先对 ServerBootstrap 进行完整配置
        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new FirstServerHandler());
                    }
                });
        
                //  2. 设置TCP参数
                serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
                serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);

        // 3. 配置完成后，再调用绑定方法
        bind(serverBootstrap, port);
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
                    // 递归尝试下一个端口
                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }
}