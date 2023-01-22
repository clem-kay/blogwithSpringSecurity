package com.example.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CannotSaveUserException extends RuntimeException{
    public CannotSaveUserException(String message) {
        super(message);
    }
}
