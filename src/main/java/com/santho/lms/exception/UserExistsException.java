package com.santho.lms.exception;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class UserExistsException extends RuntimeException{
    public UserExistsException(String message){
        super(message);
    }
}
