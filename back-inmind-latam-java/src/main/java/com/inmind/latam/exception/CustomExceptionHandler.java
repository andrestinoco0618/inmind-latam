package com.inmind.latam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.inmind.latam.dto.ExceptionResponseDto;

@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        ExceptionResponseDto exceptionResponse = new ExceptionResponseDto(
            String.valueOf(status.value()),
            status.name(),
            ex.getMessage(),
            request.getDescription(false)
        );

        return ResponseEntity.status(status).body(exceptionResponse);
    }
    
    @ExceptionHandler(QuestionNotReadyException.class)
    public final ResponseEntity<ExceptionResponseDto> handleQuestionNotReadyException(QuestionNotReadyException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NO_CONTENT;

        ExceptionResponseDto exceptionResponse = new ExceptionResponseDto(
            String.valueOf(status.value()),
            status.name(),
            ex.getMessage(),
            request.getDescription(false)
        );

        return ResponseEntity.status(status).body(exceptionResponse);
    }
    
}