package com.ericzzz.io.serialize.impl;

import com.alibaba.fastjson2.JSON;
import com.ericzzz.io.serialize.Serializer;
import com.ericzzz.io.serialize.SerializerAlgorithm;

public class JSONSerializer implements Serializer {


    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        
        return JSON.parseObject(bytes,clazz);
    }

    @Override
    public byte getSerializerAlgorithm() {

        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        
        return JSON.toJSONBytes(object);
    }

}
