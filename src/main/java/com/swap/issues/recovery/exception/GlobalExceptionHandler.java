package com.swap.issues.recovery.exception;

import com.swap.issues.recovery.client.github.repository_commits.exception.GitHubCommitsException;
import com.swap.issues.recovery.client.github.repository_issues.exception.GitHubIssuesException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomHttpException.class)
    public ResponseEntity<ApiError> handleCustomHttpException(CustomHttpException ex, HttpServletRequest request) {
        log.error("[GlobalExceptionHandler] - Erro capturado: ", ex);
        ApiError apiError = new ApiError(ex.getStatus(), ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(apiError, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {
        log.error("[GlobalExceptionHandler] - Erro capturado: ", ex);
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor");
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(GitHubIssuesException.class)
    public ResponseEntity<ApiError> handleGitHubIssuesException(GitHubIssuesException ex) {
        log.error("[GlobalExceptionHandler] - Erro capturado: ", ex);
        ApiError apiError = new ApiError(ex.getStatus(), ex.getMessage());
        return new ResponseEntity<>(apiError, ex.getStatus());
    }

    @ExceptionHandler(GitHubCommitsException.class)
    public ResponseEntity<ApiError> handleGitHubCommitsException(GitHubCommitsException ex) {
        log.error("[GlobalExceptionHandler] - Erro capturado: ", ex);
        ApiError apiError = new ApiError(ex.getStatus(), ex.getMessage());
        return new ResponseEntity<>(apiError, ex.getStatus());
    }
}
