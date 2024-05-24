package com.taskify.exceptions;

import com.taskify.app.AppConstants;
import com.taskify.utils.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiResponse resourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        String exceptionMessage = resourceNotFoundException.getMessage();
        return new ApiResponse(
                404,
                HttpStatus.NOT_FOUND,
                exceptionMessage,
                null,
                false
        );
    }

    @ExceptionHandler(ExpiredRefreshTokenException.class)
    public ApiResponse runtimeExceptionHandler(ExpiredRefreshTokenException expiredRefreshTokenException) {
        String exceptionMessage = expiredRefreshTokenException.getMessage();

        return new ApiResponse(
                403,
                HttpStatus.FORBIDDEN,
                exceptionMessage,
                null,
                false
        );
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ApiResponse expiredJwtException(Exception exception) {
        System.out.println("fired");
        String exceptionMessage = exception.getMessage();

        return new ApiResponse(
                403,
                HttpStatus.UNAUTHORIZED,
                exceptionMessage,
                null,
                false
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse runtimeExceptionHandler(IllegalArgumentException illegalArgumentException) {
        String exceptionMessage = illegalArgumentException.getMessage();

        return new ApiResponse(
                403,
                HttpStatus.UNAUTHORIZED,
                exceptionMessage,
                null,
                false
        );
    }

}
