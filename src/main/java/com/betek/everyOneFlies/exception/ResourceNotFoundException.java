package com.betek.everyOneFlies.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String menssage){
        super(menssage);
    }

    public ResourceNotFoundException(String resourceName, String identifier) {
        super(String.format("%s not found with %s", resourceName, identifier));
    }
}
