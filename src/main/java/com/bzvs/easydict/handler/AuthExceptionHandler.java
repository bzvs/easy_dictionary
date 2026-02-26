package com.bzvs.easydict.handler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AuthExceptionHandler {

    private static final String DESCRIPTION_PROPERTY = "description";

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {
        log.error(exception.getMessage(), exception);

        String description = "Unknown internal server error.";
        HttpStatus httpCode = HttpStatus.INTERNAL_SERVER_ERROR;

        if (exception instanceof BadCredentialsException) {
            httpCode = HttpStatus.UNAUTHORIZED;
            description = "The username or password is incorrect";
        }
        if (exception instanceof AccountStatusException) {
            httpCode = HttpStatus.FORBIDDEN;
            description = "The account is locked";
        }
        if (exception instanceof AccessDeniedException) {
            httpCode = HttpStatus.FORBIDDEN;
            description = "You are not authorized to access this resource";
        }
        if (exception instanceof SignatureException) {
            httpCode = HttpStatus.FORBIDDEN;
            description = "The JWT signature is invalid";
        }
        if (exception instanceof ExpiredJwtException) {
            httpCode = HttpStatus.FORBIDDEN;
            description = "The JWT token has expired";
        }
        if (exception instanceof IllegalArgumentException && exception.getMessage() != null
                && exception.getMessage().contains("refresh token")) {
            httpCode = HttpStatus.UNAUTHORIZED;
            description = "Invalid or expired refresh token";
        }

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(httpCode, exception.getMessage());
        errorDetail.setProperty(DESCRIPTION_PROPERTY, description);

        return errorDetail;
    }
}
