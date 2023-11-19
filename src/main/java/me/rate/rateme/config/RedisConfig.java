package me.rate.rateme.config;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import me.rate.rateme.config.property.RedisProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    private final RedisProperty redisProperty;

    @Bean
    public JedisPool jedisPool(JedisPoolConfig poolConfig) {
        return new JedisPool(poolConfig, redisProperty.getHost(), redisProperty.getPort(),
                             (int) Duration.ofSeconds(redisProperty.getTimeout()).toMillis(),
                             redisProperty.getPassword(), redisProperty.getDb());
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setTestWhileIdle(true);
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestOnCreate(true);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setMaxWait(Duration.ofSeconds(redisProperty.getTimeout()));
        poolConfig.setMinIdle(redisProperty.getMinIdle());
        poolConfig.setMaxTotal(redisProperty.getMaxTotal());
        poolConfig.setJmxEnabled(false);
        return poolConfig;
    }
}
