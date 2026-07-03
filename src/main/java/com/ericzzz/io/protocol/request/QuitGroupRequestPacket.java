package com.ericzzz.io.protocol.request;

import com.ericzzz.io.protocol.Packet;
import com.ericzzz.io.protocol.command.Command;

public class QuitGroupRequestPacket extends Packet {
    private String groupId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public Byte getCommand() {
        return Command.QUIT_GROUP_REQUEST;
    }

    
}
