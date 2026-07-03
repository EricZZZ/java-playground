package com.ericzzz.io.protocol.request;

import com.ericzzz.io.protocol.Packet;
import com.ericzzz.io.protocol.command.Command;

public class LogoutRequestPacket extends Packet {

    @Override
    public Byte getCommand() {
        return Command.LOGOUT_REQUEST;
    }
    
}
