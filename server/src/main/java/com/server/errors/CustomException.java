package com.server.errors;

public class CustomException extends RuntimeException {
    private int errorCode;

    public CustomException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "{" +
                "message='" + getMessage() + '\'' +
                ", errorCode=" + errorCode +
                '}';
    }
}
