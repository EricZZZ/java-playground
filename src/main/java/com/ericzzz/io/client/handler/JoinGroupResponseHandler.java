package com.ericzzz.io.client.handler;

import com.ericzzz.io.protocol.response.JoinGroupResponsePacket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket responsePacket) throws Exception {
        if (responsePacket.isSuccess()) {
            System.out.println("加入群［" + responsePacket.getGroupId() + "］成功！");
        } else {
            System.out.println("加入群［" + responsePacket.getGroupId() + "］失败，原因为：" + responsePacket.getReason());
        }
    }

}
