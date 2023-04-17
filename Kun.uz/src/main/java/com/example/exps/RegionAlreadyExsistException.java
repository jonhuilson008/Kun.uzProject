package com.example.exps;

public class RegionAlreadyExsistException extends RuntimeException{
    public RegionAlreadyExsistException(String message) {
        super(message);
    }
}
