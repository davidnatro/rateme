package me.rate.rateme.controller.advice;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import me.rate.rateme.data.model.ExceptionModel;
import me.rate.rateme.exception.CacheOperationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionAdvice {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ExceptionModel handleEntityNotFoundException(EntityNotFoundException exception) {
        return new ExceptionModel(exception.getMessage(), NOT_FOUND.value());
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(EntityExistsException.class)
    public ExceptionModel handleEntityExistsException(EntityExistsException exception) {
        return new ExceptionModel(exception.getMessage(), NOT_FOUND.value());
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ExceptionModel handleAccessDeniedException(AccessDeniedException exception) {
        return new ExceptionModel(exception.getMessage(), FORBIDDEN.value());
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CacheOperationException.class)
    public ExceptionModel handleCacheOperationException(CacheOperationException exception) {
        return new ExceptionModel(exception.getMessage(), INTERNAL_SERVER_ERROR.value());
    }
}
