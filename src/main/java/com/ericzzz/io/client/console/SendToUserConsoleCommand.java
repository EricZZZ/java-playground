package com.ericzzz.io.client.console;

import java.util.Scanner;

import com.ericzzz.io.protocol.request.MessageRequestPacket;

import io.netty.channel.Channel;

public class SendToUserConsoleCommand implements ConsoleCommand{

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("发送消息给某个某个用户：");

        String toUserId = scanner.next();
        String message = scanner.next();
        channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
        
    }
    
}
