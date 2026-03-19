package com.malgn.exception;

public class ContentAccessDeniedException extends RuntimeException {
    public ContentAccessDeniedException(String message) {
        super(message);
    }
}
