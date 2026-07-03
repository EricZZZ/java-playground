package com.ericzzz.io.protocol.request;

import java.util.List;

import com.ericzzz.io.protocol.Packet;
import com.ericzzz.io.protocol.command.Command;

public class CreateGroupRequestPacket extends Packet {
    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

}
