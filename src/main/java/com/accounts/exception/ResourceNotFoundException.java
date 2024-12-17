package com.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String source,String fieldName,String fieldValue){
        super( String.format("%s is not found for field: %s : value: %s",source,fieldName,fieldValue));
    }
}
