package net.reduck.translator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Reduck
 * @since 2020/11/6 18:03
 */
public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectMapper mapperFormatter = new ObjectMapper();

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JsonUtils.class);
    static {
        mapperFormatter.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
    }


    public static <T> T json2Object(String content, Class<T> t) {
        if (content == null || "".equals(content)) {
            return null;
        }

        if (String.class.equals(t)) {
            return (T) content;
        }

        try {
            return mapper.readValue(content, t);
        } catch (IOException e) {
            log.error("Type cast error!", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T json2Object(byte[] data, Class<T> t) {
        if (data == null || data.length == 0) {
            return null;
        }

        try {
            return mapper.readValue(data, t);
        } catch (IOException e) {
            log.error("Type cast error!", e);
            throw new RuntimeException(e);
        }
    }

    public static String object2Json(Object o) {
        try {
            if (o instanceof String) {
                return (String) o;
            }
            return mapper.writeValueAsString(o);
        } catch (Exception e) {
            log.error("Type cast error!", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T json2Object(String content, TypeReference<T> valueTypeRef) {
        if (content == null || "".equals(content)) {
            return null;
        }

        try {
            return mapper.readValue(content, valueTypeRef);
        } catch (IOException e) {
            log.error("Type cast error!", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> Map<String, T> json2Map(String json, Class<?> elementClasses) {
        try {
            return mapper.readValue(json,
                    mapper.getTypeFactory().constructParametricType(Map.class, String.class, elementClasses)
            );
        } catch (Exception e) {
            log.error("Type cast error!", e);
            throw new RuntimeException(e);
        }
    }

    public static String object2JsonFormat(Object o) {
        try {
            if (o instanceof String) {
                return jsonFormat(o.toString());
            }

            return mapperFormatter.writeValueAsString(o);
        } catch (Exception e) {
            log.error("Type cast error!", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> json2List(String content, Class<T> t) {
        JavaType javaType = getCollectionType(ArrayList.class, t);
        try {
            return mapper.readValue(content, javaType);
        } catch (Exception e) {
            log.error("Type cast error!", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T cast(Object original, Class<T> target) {
        return json2Object(object2Json(original), target);
    }

    public static String jsonFormat(String data) {
        return object2JsonFormat(json2Object(data, HashMap.class));
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}
