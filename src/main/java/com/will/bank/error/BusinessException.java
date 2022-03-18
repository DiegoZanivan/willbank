package com.will.bank.error;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException  {

    private HttpStatus httpStatus;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, HttpStatus status) {
        super(message);
        this.httpStatus = status;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
