package com.clauzewitz.sprinklingmoneydemo.handler.exception;

import org.springframework.http.HttpStatus;

public class NotReceivedException extends RuntimeException {
    private final int status = HttpStatus.FORBIDDEN.value();

    public NotReceivedException() {
        super("Not Received");
    }

    public NotReceivedException(String message) {
        super(message);
    }

    public NotReceivedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotReceivedException(Throwable cause) {
        super(cause);
    }

    protected NotReceivedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getStatus() {
        return status;
    }
}
