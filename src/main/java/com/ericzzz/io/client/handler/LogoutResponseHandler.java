package com.ericzzz.io.client.handler;

import com.ericzzz.io.protocol.response.LogoutResponsePacket;
import com.ericzzz.io.util.SessionUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket logoutResponsePacket) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
    
}
