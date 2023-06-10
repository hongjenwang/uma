package com.umabikerental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(Collectors.toList());

        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, errors);

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotEnoughBikesAvailableException.class)
    public ResponseEntity<Object> handleNotEnoughBikesAvailableException(NotEnoughBikesAvailableException ex) {
        List<String> errors = Arrays.asList(ex.getMessage());
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, errors);
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }
}