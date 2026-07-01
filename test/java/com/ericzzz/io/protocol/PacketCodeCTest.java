package com.ericzzz.io.protocol;

import java.util.UUID;

import org.junit.Test;

import com.ericzzz.io.protocol.request.LoginRequestPacket;
import com.ericzzz.io.serialize.Serializer;
import com.ericzzz.io.serialize.impl.JSONSerializer;

public class PacketCodeCTest {
    @Test
    public void encode() {
        Serializer serializer = new JSONSerializer();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setVersion((byte) 1);
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUserName("zhangsan");
        loginRequestPacket.setPassword("password");

        // PacketCodeC packetCodeC = PacketCodeC.INSTANCE;
        // ByteBuf byteBuf = packetCodeC.encode(ByteBufAllocator.DEFAULT, loginRequestPacket);
        // Packet decodedPacket = packetCodeC.decode(byteBuf);

        // Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));
    }
}