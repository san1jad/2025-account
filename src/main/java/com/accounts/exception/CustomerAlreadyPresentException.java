package com.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomerAlreadyPresentException extends RuntimeException{
    public CustomerAlreadyPresentException(String message){
        super(message);
    }
}
