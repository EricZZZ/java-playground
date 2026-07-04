package com.ericzzz.io.protocol.response;

import com.ericzzz.io.protocol.Packet;
import com.ericzzz.io.protocol.command.Command;

public class HeartBeatResponsePacket extends Packet{

    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_RESPONSE;
    }
    
}
