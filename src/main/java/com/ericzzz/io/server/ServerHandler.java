package com.ericzzz.io.server;

import java.util.Date;

import com.ericzzz.io.protocol.Packet;
import com.ericzzz.io.protocol.PacketCodeC;
import com.ericzzz.io.protocol.request.LoginRequestPacket;
import com.ericzzz.io.protocol.response.LoginResponsePacket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
         ByteBuf byteBuf = (ByteBuf) msg;
         // 解码
         Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

         LoginResponsePacket loginResponsePacket = new LoginResponsePacket();

         loginResponsePacket.setVersion(packet.getVersion());

         // 判断是否是登录请求数据包
         if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            // 登录校验
            if (valid(loginRequestPacket)) {
                
                // 登录成功
                loginResponsePacket.setSuccess(true);
            }
            else {
                // 登录失败
                loginResponsePacket.setReason("账户密码校验失败");
                loginResponsePacket.setSuccess(false);
            }
         }
         
         // 编码
         byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(),loginResponsePacket);
         ctx.channel().writeAndFlush(byteBuf);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        System.out.println(new Date() + ": 服务器校验登录请求：" + loginRequestPacket.toString());
        return false;
    }
    
}
