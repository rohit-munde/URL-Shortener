package com.example.url_shortener;

import com.example.url_shortener.exception.BusinessException;
import com.example.url_shortener.response.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<@NonNull ApiErrorResponse> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        HttpStatus errorStatus = ex.getHttpErrorStatus();
        ApiErrorResponse apiError = new ApiErrorResponse(errorStatus.value(), errorStatus.getReasonPhrase(), ex.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(apiError, errorStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<@NonNull ApiErrorResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        HashMap<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> validationErrors.put(error.getField(), error.getDefaultMessage()));

        ApiErrorResponse apiError = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                "Validation failed for one or more fields",
                request.getRequestURI(),
                validationErrors
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
