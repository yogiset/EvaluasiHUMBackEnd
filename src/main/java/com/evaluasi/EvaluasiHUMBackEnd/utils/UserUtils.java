package com.evaluasi.EvaluasiHUMBackEnd.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserUtils {
    private UserUtils(){

    }

    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<>("{\"message\":\""+responseMessage+"\"}", httpStatus);
    }

    public static ResponseEntity<Object> getResponseEntityy(String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<>("{\"message\":\""+responseMessage+"\"}", httpStatus);
    }
}
