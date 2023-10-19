package com.ilianikonov.stockmarket.controller.advice;

import com.ilianikonov.stockmarket.controller.response.ErrorResponse;
import com.ilianikonov.stockmarket.exception.NotEnoughMoneyException;
import com.ilianikonov.stockmarket.exception.ObjectNotFoundException;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException methodArgumentNotValidException){
        String message = "ошибка валидации";
        Map<Object, Object> fieldsError = getFieldsError(methodArgumentNotValidException);
        return buildResponseEntity(methodArgumentNotValidException, HttpStatus.BAD_REQUEST, message, fieldsError);
    }

    @Nullable
    private Map<Object, Object> getFieldsError(MethodArgumentNotValidException methodArgumentNotValidException){
        Object[] detailMessageArguments = methodArgumentNotValidException.getDetailMessageArguments();
        Map <Object, Object> data = new HashMap<>();
        if (detailMessageArguments != null && (detailMessageArguments.length >= 1 && (detailMessageArguments[1] instanceof List)) && (((List<?>) detailMessageArguments[1]).get(0) instanceof String)) {
            List<String> detailMessageArgument = (List<String>) detailMessageArguments[1];
            for (String x: detailMessageArgument) {
                String[] tokens = x.split(": ");
                String valueToken = "";
                for(int i = 1; i < tokens[1].toCharArray().length - 1; i++){
                    valueToken += tokens[1].toCharArray()[i];
                }

                data.put(tokens[0],valueToken);
            }
        }
        return data.isEmpty() ? null : data;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception){
        String message = "во время обработки произошла ошибка";
        return buildResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR, message, null);
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    public ResponseEntity<ErrorResponse> handleException(NotEnoughMoneyException notEnoughMoneyException){
        return buildResponseEntity(notEnoughMoneyException, HttpStatus.BAD_REQUEST, notEnoughMoneyException.getMessage(), null);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ObjectNotFoundException objectNotFoundException){
        return buildResponseEntity(objectNotFoundException, HttpStatus.NOT_FOUND, objectNotFoundException.getMessage(), null);
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(Exception exception, HttpStatus httpStatus, String message, Map<Object,Object> data){
        log.error(exception.getMessage(), exception);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(message);
        errorResponse.setData(data);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
