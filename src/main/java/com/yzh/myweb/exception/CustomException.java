package com.yzh.myweb.exception;

public abstract class CustomException extends RuntimeException {

    public CustomException(String message, Throwable t) {
        super(message, t);
    }

    public CustomException(String message) {
        super(message);
    }

    public abstract String getErrorCode();

}
