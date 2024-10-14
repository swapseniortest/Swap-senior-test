package com.swap.issues.recovery.exception;

import org.springframework.http.HttpStatus;

public class WebhookSiteException extends CustomHttpException{
    public WebhookSiteException(String message, HttpStatus status) {
        super(message, status);
    }
}
