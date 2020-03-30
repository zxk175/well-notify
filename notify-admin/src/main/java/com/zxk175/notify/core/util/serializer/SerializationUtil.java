package com.zxk175.notify.core.util.serializer;

import org.nustaq.serialization.FSTConfiguration;

/**
 * @author zxk175
 * @since 2020-03-29 13:44
 */
public class SerializationUtil {

    private static FSTConfiguration configuration = FSTConfiguration.createDefaultConfiguration();


    public static byte[] serialize(Object object) {
        return configuration.asByteArray(object);
    }

    static Object deserialize(byte[] bytes) {
        return configuration.asObject(bytes);
    }

}
