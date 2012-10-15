package com.marcusilgner.ghcity.helpers;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import java.io.IOException;
import java.io.InputStream;

public class Json {
    public static <T> T fromJson(Object o, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, true);
        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);

        T result = null;
        try {
            if(o instanceof InputStream)
                result = mapper.readValue((InputStream) o, clazz);
            if(o instanceof String)
                result = mapper.readValue((String) o, clazz);
        } catch (IOException e) {
            // pass
        }

        return result;
    }
}
