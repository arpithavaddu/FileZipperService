package com.files.filezipper.exception;

public class NoFilesSelectedException extends Exception {
    private final String message;

    public NoFilesSelectedException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
