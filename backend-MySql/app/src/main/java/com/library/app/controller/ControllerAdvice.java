package com.library.app.controller;

import com.library.app.exception.BookException;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.library.app.exception.MessageException;
import com.library.app.exception.PaymentException;
import com.library.app.exception.ReviewException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(BookException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleBookException(BookException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ReviewException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleReviewException(ReviewException e) {
        return e.getMessage();
    }

    @ExceptionHandler(MessageException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleMessageException(MessageException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ParseException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleParseException(ParseException e) {
        return e.getMessage();
    }

    @ExceptionHandler(PaymentException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handlePaymentException(PaymentException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
