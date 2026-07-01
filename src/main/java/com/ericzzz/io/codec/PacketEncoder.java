package com.ericzzz.io.codec;

import com.ericzzz.io.protocol.Packet;
import com.ericzzz.io.protocol.PacketCodeC;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) throws Exception {
        PacketCodeC.INSTANCE.encode(out, packet);
        
    }

    
    
}
