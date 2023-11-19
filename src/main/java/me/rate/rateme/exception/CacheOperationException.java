package me.rate.rateme.exception;

public class CacheOperationException extends RuntimeException {

    public CacheOperationException() { }

    public CacheOperationException(String message) {
        super(message);
    }
}
