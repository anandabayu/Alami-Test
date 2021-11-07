package com.anandabayu.alami.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;

@RestControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HashMap<String, Object> handleNoHandlerFound(NoHandlerFoundException e) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", e.getLocalizedMessage());
        return response;
    }
}
