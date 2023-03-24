package stacs.estate.cs5031p3code.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * A class for operating Redis.
 *
 * @author 220032952
 * @version 0.0.1
 */
@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Component
public class RedisCache {

    /**
     * The Redis template.
     */
    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * Cache basic objects, Integer, String, entity classes, etc.
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * Cache basic objects, Integer, String, entity classes, etc.
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param timeout  The time.
     * @param timeUnit The time unit.
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * Set the expired time.
     *
     * @param key     The key of cache.
     * @param timeout The time.
     * @return true=successful setting；false=failed setting.
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * Set the expired time.
     *
     * @param key     The key of cache.
     * @param timeout The time.
     * @param unit    The time unit.
     * @return true=successful setting；false=failed setting.
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, unit));
    }

    /**
     * Get the object in cache by key.
     *
     * @param key The key of cache.
     * @return The corresponding data by key.
     */
    public <T> T getCacheObject(final String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * Delete a single object by key.
     *
     * @param key The key of cache.
     */
    public void deleteObject(final String key) {
        redisTemplate.delete(key);
    }

    /**
     * Delete collection objects.
     *
     * @param collection The multi-objects.
     * @return Return the result.
     */
    public long deleteObject(final Collection collection) {
        return redisTemplate.delete(collection);
    }

    /**
     * Set data of list.
     *
     * @param key      The key.
     * @param dataList The list data.
     * @return Return the result.
     */
    public <T> long setCacheList(final String key, final List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * Get data of list.
     *
     * @param key The key.
     * @return The data by key.
     */
    public <T> List<T> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * Set data of set.
     *
     * @param key     The key.
     * @param dataSet The set data.
     * @return Return the result.
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        for (T t : dataSet) {
            setOperation.add(t);
        }
        return setOperation;
    }

    /**
     * Get data of set.
     *
     * @param key The key.
     * @return The data by key.
     */
    public <T> Set<T> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * Set data of map;
     *
     * @param key     The key.
     * @param dataMap The map data.
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * Get data of map.
     *
     * @param key The key.
     * @return The data by key.
     */
    public <T> Map<String, T> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * Set data to hash.
     *
     * @param key   The key.
     * @param hKey  The hash key.
     * @param value The value.
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * Get data from hash.
     *
     * @param key  The key.
     * @param hKey The hash key.
     * @return The value.
     */
    public <T> T getCacheMapValue(final String key, final String hKey) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * Delete data from hash.
     *
     * @param key  The key.
     * @param hkey The hash key.
     */
    public void delCacheMapValue(final String key, final String hkey) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, hkey);
    }

    /**
     * Getting series of data in hash.
     *
     * @param key   The key.
     * @param hKeys The collection of hash keys.
     * @return The collection of value.
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * Get a list of cached basic objects.
     *
     * @param pattern The prefix of string.
     * @return The list of objects.
     */
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }
}