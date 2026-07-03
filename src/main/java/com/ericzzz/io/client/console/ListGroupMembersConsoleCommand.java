package com.ericzzz.io.client.console;

import java.util.Scanner;

import com.ericzzz.io.protocol.request.ListGroupMembersRequestPacket;

import io.netty.channel.Channel;

public class ListGroupMembersConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        ListGroupMembersRequestPacket requestPacket = new ListGroupMembersRequestPacket();
        
        System.out.println("输入 groupId，获取群成员列表：");
        String groupId = scanner.next();

        requestPacket.setGroupId(groupId);
        channel.writeAndFlush(requestPacket);
    }
    
    
}
