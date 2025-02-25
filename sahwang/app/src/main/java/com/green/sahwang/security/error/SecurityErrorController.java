package com.green.sahwang.security.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SecurityErrorController {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> handleMyException(UserException userException){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("error/myerror");
    }

}
