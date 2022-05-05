package com.files.filezipper.controller;


import com.files.filezipper.exception.NoFilesSelectedException;
import com.files.filezipper.message.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ResponseMessage> handleMultiPartException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Please select files to zip"));
    }

    @ExceptionHandler(NoFilesSelectedException.class)
    public ResponseEntity<ResponseMessage> handleNoFileSelectedException(NoFilesSelectedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(e.getMessage()));
    }

}
