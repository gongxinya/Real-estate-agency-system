package stacs.estate.cs5031p3code.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * A class for representing Redis using FastJson serialisation.
 *
 * @author 220032952
 * @version 0.0.1
 */
public class FastJsonRedisSerialiser<T> implements RedisSerializer<T> {

    /**
     * The default charset.
     */
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * The type of java class.
     */
    private final Class<T> clazz;

    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    /**
     * The constructor method.
     *
     * @param clazz The type of class.
     */
    public FastJsonRedisSerialiser(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    /**
     * The method for serializing a java object.
     *
     * @param t The java object.
     * @return Return the type code after serialization.
     * @throws SerializationException The serialization exception.
     */
    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    /**
     * The method for deserializing a java object.
     *
     * @param bytes The byte code.
     * @return Return a java object.
     * @throws SerializationException The serialization exception.
     */
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        var str = new String(bytes, DEFAULT_CHARSET);
        return JSON.parseObject(str, clazz);
    }

    /**
     * The method for getting the type of Java class.
     *
     * @param clazz The class.
     * @return Return the Java type.
     */
    protected JavaType getJavaType(Class<?> clazz) {
        return TypeFactory.defaultInstance().constructType(clazz);
    }
}