package com.ericzzz.io.attribute;

import com.ericzzz.io.session.Session;

import io.netty.util.AttributeKey;

public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
