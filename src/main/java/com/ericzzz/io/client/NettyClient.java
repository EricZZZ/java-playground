package com.ericzzz.io.client;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.ericzzz.io.client.handler.LoginResponseHandler;
import com.ericzzz.io.client.handler.MessageResponseHandler;
import com.ericzzz.io.codec.PacketDecoder;
import com.ericzzz.io.codec.PacketEncoder;
import com.ericzzz.io.codec.Spliter;
import com.ericzzz.io.protocol.request.LoginRequestPacket;
import com.ericzzz.io.protocol.request.MessageRequestPacket;
import com.ericzzz.io.util.SessionUtil;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    private static final int MAX_RETRY = 5;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();

        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap
                // 1. 指定线程模式
                .group(group)
                // 2. 指定IO类型为NIO
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                // 3. IO 处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());

                    }
                });

        connect(bootstrap, HOST, PORT, MAX_RETRY);

    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功，启动控制台线程...");
                Channel channel = ((ChannelFuture) future).channel();
                // 连接成功之后，启动控制台线程
                stratConsoleThread(channel);
            } else if (retry == 0) {
                System.out.println("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.out.println(new Date() + ": 连接失败，第" + order + "次重连...");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay,
                        TimeUnit.SECONDS);
            }
        });
    }

    private static void stratConsoleThread(Channel channel) {
        Scanner sc = new Scanner(System.in);
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)) {
                    System.out.println("输入用户名登录：");
                    String username = sc.nextLine();
                    loginRequestPacket.setUserName(username);
                    // 使用默认密码
                    loginRequestPacket.setPassword("pwd");
                    // 发送登录数据包
                    channel.writeAndFlush(loginRequestPacket);
                    waitForLoginResponse();
                } else {
                    String toUserId = sc.next();
                    String message = sc.next();
                    channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
                }
            }
        }).start();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
