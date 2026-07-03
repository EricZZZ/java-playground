package com.ericzzz.io.client.console;

import java.util.Scanner;

import com.ericzzz.io.protocol.request.QuitGroupRequestPacket;

import io.netty.channel.Channel;

public class QuitGroupConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        QuitGroupRequestPacket requestPacket = new QuitGroupRequestPacket();

        System.out.println("输入 groupId，退出群聊：");
        String groupId = scanner.next();

        requestPacket.setGroupId(groupId);
        channel.writeAndFlush(requestPacket);
        
    }
    
    
}
