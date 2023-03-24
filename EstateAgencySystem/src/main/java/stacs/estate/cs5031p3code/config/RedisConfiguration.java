package stacs.estate.cs5031p3code.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import stacs.estate.cs5031p3code.utils.FastJsonRedisSerialiser;

/**
 * A configuration class for configuring Redis.
 *
 * @author 220032952
 * @version 0.0.1
 */
@Configuration
public class RedisConfiguration {

    /**
     * The method for configuring redis template.
     *
     * @param connectionFactory The redis connection factory.
     * @return Return a redis template.
     */
    @Bean
    @SuppressWarnings(value = {"unchecked", "rawtypes"})
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        var template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        // New a FastJsonRedisSerialiser.
        var serializer = new FastJsonRedisSerialiser(Object.class);

        // Using StringRedisSerializer to serialize and deserialize the key.
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        // The key of Hash also uses the way of StringRedisSerializer.
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
        return template;
    }
}