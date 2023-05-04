package com.example.exps;

public class AppForbiddenException extends RuntimeException {
    public AppForbiddenException(String message) {
        super(message);
    }
}
