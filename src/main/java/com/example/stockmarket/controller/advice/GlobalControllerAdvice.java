package com.example.stockmarket.controller.advice;

import com.example.stockmarket.controller.response.ErrorResponse;
import com.example.stockmarket.exception.LoginIsOccupiedException;
import com.example.stockmarket.exception.NotEnoughMoneyException;
import com.example.stockmarket.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception){
        String message = "во время обработки произошла ошибка";
        return buildResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    public ResponseEntity<ErrorResponse> handleException(NotEnoughMoneyException notEnoughMoneyException){
        return buildResponseEntity(notEnoughMoneyException, HttpStatus.BAD_REQUEST, notEnoughMoneyException.getMessage());
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ObjectNotFoundException objectNotFoundException){
        return buildResponseEntity(objectNotFoundException, HttpStatus.NOT_FOUND, objectNotFoundException.getMessage());
    }
    @ExceptionHandler(LoginIsOccupiedException.class)
    public ResponseEntity<ErrorResponse> handleException(LoginIsOccupiedException loginIsOccupiedException){
        return buildResponseEntity(loginIsOccupiedException, HttpStatus.BAD_REQUEST, loginIsOccupiedException.getMessage());
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(Exception exception, HttpStatus httpStatus, String message){
        log.error(exception.getMessage(), exception);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(message);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
