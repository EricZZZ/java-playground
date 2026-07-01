package com.ericzzz.io.protocol.request;

import com.ericzzz.io.protocol.Packet;
import com.ericzzz.io.protocol.command.Command;

public class MessageRequestPacket extends Packet{
    
    private String message;

    @Override
    public Byte getCommand() {
        
        return Command.MESSAGE_REQUEST;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    

    
}
