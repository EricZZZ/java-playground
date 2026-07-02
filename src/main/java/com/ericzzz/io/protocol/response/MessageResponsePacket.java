package com.ericzzz.io.protocol.response;

import com.ericzzz.io.protocol.Packet;
import com.ericzzz.io.protocol.command.Command;

public class MessageResponsePacket extends Packet {
    private String fromUserId;
    private String fromUserName;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

}
