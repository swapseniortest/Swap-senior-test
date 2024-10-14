package com.swap.issues.recovery.client.github.repository_issues.exception;

import com.swap.issues.recovery.exception.CustomHttpException;
import org.springframework.http.HttpStatus;

public class GitHubIssuesException extends CustomHttpException {
    public GitHubIssuesException(String message, HttpStatus status) {
        super(message, status);
    }
}
