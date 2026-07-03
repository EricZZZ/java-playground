package com.ericzzz.io.client.console;

import java.util.Scanner;

import com.ericzzz.io.protocol.request.JoinGroupRequestPacket;

import io.netty.channel.Channel;

public class JoinGroupConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        JoinGroupRequestPacket joinGroupRequestPacket = new JoinGroupRequestPacket();
        System.out.println("输入 groupId，加入群聊：");
        String groupId = scanner.next();
        joinGroupRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(joinGroupRequestPacket);
    }

}
