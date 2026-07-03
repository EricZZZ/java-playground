package com.ericzzz.io.server.handler;

import com.ericzzz.io.protocol.request.QuitGroupRequestPacket;
import com.ericzzz.io.protocol.response.QuitGroupResponsePacket;
import com.ericzzz.io.util.SessionUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket requestPacket) throws Exception {
        // 1. 获取群对应的 ChannelGroup，然后将当前用户的Channel移除
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.remove(ctx.channel());
        // 2. 构造退群响应发送给客户端
        QuitGroupResponsePacket responsePacket = new QuitGroupResponsePacket();
        responsePacket.setGroupId(groupId);
        responsePacket.setSuccess(true);
        
        ctx.channel().writeAndFlush(responsePacket);
    }
    
}
