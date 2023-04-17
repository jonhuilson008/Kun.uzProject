package com.example.exps;

public class CategoryAlreadyExistException extends RuntimeException {
    public CategoryAlreadyExistException(String message) {
        super(message);
    }
}
