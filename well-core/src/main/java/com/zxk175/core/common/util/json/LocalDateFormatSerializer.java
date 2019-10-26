package com.zxk175.core.common.util.json;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateFormatSerializer implements ObjectSerializer {

    private final String pattern;

    public LocalDateFormatSerializer(String pattern) {
        this.pattern = pattern;
    }

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        if (object == null) {
            serializer.out.writeNull();
            return;
        }

        LocalDateTime localDateTime = (LocalDateTime) object;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        String text = dateTimeFormatter.format(localDateTime);

        serializer.write(text);
    }
}
