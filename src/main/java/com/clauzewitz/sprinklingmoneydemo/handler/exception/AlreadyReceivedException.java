package com.clauzewitz.sprinklingmoneydemo.handler.exception;

import org.springframework.http.HttpStatus;

public class AlreadyReceivedException extends RuntimeException {
    private final int status = HttpStatus.BAD_REQUEST.value();

    public AlreadyReceivedException() {
        super("Already Received");
    }

    public AlreadyReceivedException(String message) {
        super(message);
    }

    public AlreadyReceivedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyReceivedException(Throwable cause) {
        super(cause);
    }

    protected AlreadyReceivedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getStatus() {
        return status;
    }
}
