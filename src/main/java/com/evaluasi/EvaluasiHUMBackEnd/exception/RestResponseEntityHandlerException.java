package com.evaluasi.EvaluasiHUMBackEnd.exception;

import com.evaluasi.EvaluasiHUMBackEnd.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class RestResponseEntityHandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AllException.class)
    public ResponseEntity<ErrorMessage> departmentNotFoundException(AllException exception, WebRequest request){
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND,
                exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
