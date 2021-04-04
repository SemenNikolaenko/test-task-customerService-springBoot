package com.wevservice.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(InvalidInputDataException.class)
    public ResponseEntity handleInvalidInput(InvalidInputDataException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity handleNoData(NoDataFoundException e){
        return new ResponseEntity(e.getMessage(),HttpStatus.NO_CONTENT);
    }
    @ExceptionHandler(ExistingCustomerException.class)
    public ResponseEntity handleExistCustomerException(ExistingCustomerException e){
        return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
