package com.clauzewitz.sprinklingmoneydemo.handler.exception;

import org.springframework.http.HttpStatus;

public class NoPermissionException extends RuntimeException {
    private final int status = HttpStatus.FORBIDDEN.value();

    public NoPermissionException() {
        super("No Permission");
    }

    public NoPermissionException(String message) {
        super(message);
    }

    public NoPermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPermissionException(Throwable cause) {
        super(cause);
    }

    protected NoPermissionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getStatus() {
        return status;
    }
}
