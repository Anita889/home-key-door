package com.example.homekeydoor.exceptions;

public class HomeRestException extends RuntimeException {

    public HomeRestException() {
    }

    public HomeRestException(String message) {
        super(message);
    }

    public HomeRestException(Throwable cause) {
        super(cause);
    }

    public HomeRestException(String message, Throwable cause) {
        super(message, cause);
    }

}