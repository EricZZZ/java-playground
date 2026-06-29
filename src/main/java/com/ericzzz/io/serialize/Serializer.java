package com.ericzzz.io.serialize;

import com.ericzzz.io.serialize.impl.JSONSerializer;

public interface Serializer {

    /** JSON 序列化*/
    byte JSON_SERIALIZER = 1;

    Serializer DEFAULT = new JSONSerializer();
    /**
     * 获取序列化算法
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * Java 对象转换成二进制数据
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 二进制数据转换成 Java 对象
     * @param bytes
     * @param clazz
     * @return
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
