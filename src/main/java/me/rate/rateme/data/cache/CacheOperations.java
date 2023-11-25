package me.rate.rateme.data.cache;

public interface CacheOperations<K> {

  boolean exists(K key);
}
