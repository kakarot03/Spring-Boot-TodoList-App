package com.todo.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends RuntimeException{

    private static final long serialVersionUId = 1L;

    public TaskNotFoundException(String message) {
        super(message);
    }
}
