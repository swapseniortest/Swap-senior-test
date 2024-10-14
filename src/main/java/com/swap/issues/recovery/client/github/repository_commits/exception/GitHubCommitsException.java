package com.swap.issues.recovery.client.github.repository_commits.exception;

import com.swap.issues.recovery.exception.CustomHttpException;
import org.springframework.http.HttpStatus;

public class GitHubCommitsException extends CustomHttpException {

    public GitHubCommitsException(String message, HttpStatus status) {
        super(message, status);
    }
}
