package com.example.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNameOrPasswordException extends RuntimeException{
    public UserNameOrPasswordException(String message) {
        super(message);
    }
}
