package me.rate.rateme.data.cache.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import me.rate.rateme.data.cache.RedisStringOperations;
import me.rate.rateme.data.model.TaskModel;
import me.rate.rateme.exception.CacheOperationException;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

@Slf4j
@Component
public class RedisStringOperationsImpl implements RedisStringOperations<String, TaskModel> {

    private final JedisPool jedisPool;
    private final ObjectWriter writer;
    private final ObjectReader reader;

    public RedisStringOperationsImpl(JedisPool jedisPool) {
        this.jedisPool = jedisPool;

        ObjectMapper objectMapper = new ObjectMapper();
        this.writer = objectMapper.writerFor(TaskModel.class);
        this.reader = objectMapper.readerFor(TaskModel.class);
    }

    @Override
    public TaskModel get(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            String serializedValue = jedis.get(key);
            if (serializedValue == null) {
                log.warn("cannot find Task object in cache by key '{}'", key);
                throw new CacheOperationException("cannot find Task object in cache");
            }
            return reader.readValue(serializedValue);
        } catch (JsonProcessingException exception) {
            log.error("cannot deserialize Task object from cache -> message: '{}'",
                      exception.getMessage());
            throw new CacheOperationException("cannot deserialize Task object from cache");
        } catch (JedisException exception) {
            log.error("cannot get Task object from cache -> message: '{}'", exception.getMessage());
            throw new CacheOperationException("cannot get Task object from cache");
        }
    }

    @Override
    public void set(String key, TaskModel value) {
        try (Jedis jedis = jedisPool.getResource()) {
            String serializedValue = writer.writeValueAsString(value);
            jedis.set(key, serializedValue);
        } catch (JsonProcessingException exception) {
            log.error("cannot serialize Task object before caching -> message: '{}'",
                      exception.getMessage());
            throw new CacheOperationException("cannot serialize Task object");
        } catch (JedisException exception) {
            log.error("cannot cache Task object -> message: '{}'", exception.getMessage());
            throw new CacheOperationException("cannot cache Task object");
        }
    }

    @Override
    public void expire(String key, long seconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.expire(key, seconds);
        } catch (JedisException exception) {
            log.error("cannot cache Task object -> message: '{}'", exception.getMessage());
            throw new CacheOperationException("cannot cache Task object");
        }
    }

    @Override
    public boolean exists(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.exists(key);
        } catch (JedisException exception) {
            log.error("cannot check Task object cache existence -> message: '{}'",
                      exception.getMessage());
            throw new CacheOperationException("cannot check Task object cache existence");
        }
    }
}
