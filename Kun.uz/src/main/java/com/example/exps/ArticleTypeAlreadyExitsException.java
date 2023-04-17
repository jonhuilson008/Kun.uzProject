package com.example.exps;

public class ArticleTypeAlreadyExitsException extends RuntimeException{
    public ArticleTypeAlreadyExitsException(String message) {
        super(message);
    }
}
