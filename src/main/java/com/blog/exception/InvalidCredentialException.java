package com.blog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidCredentialException extends RuntimeException{
    private String message;

    public InvalidCredentialException(String message) {
        this.message = message;
    }


}
