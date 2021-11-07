package com.pangaea.publisher.controller;

import com.pangaea.publisher.dto.ExceptionResponse;
import com.pangaea.publisher.exception.BadRequestException;
import com.pangaea.publisher.exception.RestClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Slf4j
@RestController
@ControllerAdvice
public class ExceptionController {


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> badRequestException(BadRequestException ex) {

        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .responseMessage(!StringUtils.isEmpty(ex.getMessage()) ? ex.getMessage() : "Unknown error occurred")
                        .errors(new ArrayList<>())
                        .build(), HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<ExceptionResponse> badRequestException(RestClientException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .responseMessage(ex.getMessage())
                        .errors(new ArrayList<>())
                        .build(), HttpStatus.EXPECTATION_FAILED);
    }


    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ExceptionResponse> badRequestException(MissingRequestHeaderException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .responseMessage(ex.getMessage())
                        .errors(new ArrayList<>())
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> badRequestException(Exception ex) {
        if (ex instanceof NullPointerException) {
            return new ResponseEntity<>(
                    ExceptionResponse.builder()
                            .responseMessage("An error occurred, please contact the administrator")
                            .errors(new ArrayList<>())
                            .build(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .responseMessage("An error occurred, please contact the administrator")
                        .errors(new ArrayList<>())
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
