package com.example.homekeydoor.exceotions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class RestUnauthorizedAccessException extends HomeRestException {

    public RestUnauthorizedAccessException() {
        super("Unauthorized action");
    }

    public RestUnauthorizedAccessException(String message) {
        super(message);
    }

}