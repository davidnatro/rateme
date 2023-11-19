package me.rate.rateme.data.cache;

public interface RedisStringOperations<K, V> extends CacheOperations<K> {

    V get(K key);

    void set(K key, V value);

    void expire(K key, long seconds);
}
