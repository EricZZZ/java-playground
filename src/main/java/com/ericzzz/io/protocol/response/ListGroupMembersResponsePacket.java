package com.ericzzz.io.protocol.response;

import java.util.List;

import com.ericzzz.io.protocol.Packet;
import com.ericzzz.io.protocol.command.Command;
import com.ericzzz.io.session.Session;

public class ListGroupMembersResponsePacket extends Packet {
    private String groupId;

    private List<Session> sessionList;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<Session> getSessionList() {
        return sessionList;
    }

    public void setSessionList(List<Session> sessionList) {
        this.sessionList = sessionList;
    }

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }

    
}
