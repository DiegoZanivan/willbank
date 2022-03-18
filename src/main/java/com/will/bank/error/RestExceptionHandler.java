package com.will.bank.error;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException ex) {
        BusinessError error = new BusinessError(HttpStatus.NOT_FOUND);
        error.setMessage(ex.getMessage());
        return buildResponseEntity(error);
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<Object> handleBusinessException(
            BusinessException ex) {
        BusinessError error = new BusinessError(HttpStatus.INTERNAL_SERVER_ERROR);

        if (ex.getHttpStatus() != null) {
            error.setStatus(ex.getHttpStatus());
        }

        error.setMessage(ex.getMessage());
        return buildResponseEntity(error);
    }

    private ResponseEntity<Object> buildResponseEntity(BusinessError businessError) {
        return new ResponseEntity<>(businessError, businessError.getStatus());
    }

}
