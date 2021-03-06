package com.clauzewitz.sprinklingmoneydemo.handler.exception;

import org.springframework.http.HttpStatus;

public class ExpiredException extends RuntimeException {
    private final int status = HttpStatus.NOT_FOUND.value();

    public ExpiredException() {
        super("Expired Content");
    }

    public ExpiredException(String message) {
        super(message);
    }

    public ExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpiredException(Throwable cause) {
        super(cause);
    }

    protected ExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getStatus() {
        return status;
    }
}
