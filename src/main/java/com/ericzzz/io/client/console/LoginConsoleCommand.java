package com.ericzzz.io.client.console;

import java.util.Scanner;

import com.ericzzz.io.protocol.request.LoginRequestPacket;

import io.netty.channel.Channel;

public class LoginConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        System.out.println("输入用户名登录: ");
        loginRequestPacket.setUserName(scanner.nextLine());
        loginRequestPacket.setPassword("pwd");

        // 发送登录数据包
        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();

    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
