package com.example.homekeydoor.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RestConflictException extends HomeRestException {
    public RestConflictException() {
        super();
    }

    public RestConflictException(String message) {
        super(message);
    }

}
