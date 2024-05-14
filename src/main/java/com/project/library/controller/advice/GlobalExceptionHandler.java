package com.project.library.controller.advice;

import com.project.library.exceptions.ModelErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

/*    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ModelErrorMessage> handleRecordNotFoundException(GeneralException ex, HttpServletRequest req){
        var response=ModelErrorMessage.builder()
                .message(ex.getMessage())
                .path(req.getRequestURI())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.badRequest().body(response);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ModelErrorMessage> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request){
        var resp = ModelErrorMessage.builder()
                .message(ex.getMessage()).statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI()).build();
        return ResponseEntity.badRequest().body(resp);
    }*/

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ModelErrorMessage> handleConstraintViolationException(Exception ex, HttpServletRequest request){
        var resp = ModelErrorMessage.builder()
                .message(ex.getMessage()).statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI()).build();
        return ResponseEntity.badRequest().body(resp);
    }
}
