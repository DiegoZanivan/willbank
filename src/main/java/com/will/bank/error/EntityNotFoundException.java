package com.will.bank.error;

public class EntityNotFoundException extends RuntimeException  {

    public EntityNotFoundException(String message) {
        super(message);
    }

}