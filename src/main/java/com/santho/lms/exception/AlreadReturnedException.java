package com.santho.lms.exception;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class AlreadReturnedException extends RuntimeException{
    public AlreadReturnedException(String message){
        super(message);
    }
}
