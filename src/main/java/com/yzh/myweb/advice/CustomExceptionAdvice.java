package com.yzh.myweb.advice;

import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.yzh.myweb.common.CustomResponseBody;
import com.yzh.myweb.exception.CustomException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice({"com.yzh.myweb.controller"})
@Slf4j
public class CustomExceptionAdvice {

    @ExceptionHandler({
            Exception.class
    })
    public ResponseEntity<CustomResponseBody> handleCustomException(Exception e) {
        log.info(String.format("handling exception: %s.", e));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");

        CustomResponseBody responseBody = new CustomResponseBody();
        ResponseEntity<CustomResponseBody> responseEntity;
        HttpStatus httpStatus = HttpStatus.OK;

        if (e instanceof CustomException) {
            CustomException customException = (CustomException) e;

            responseBody.setCode(customException.getErrorCode());
            responseBody.setMessage(customException.getMessage());
        } else if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) e;

            String message = validException.getBindingResult().getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            responseBody.setCode("201");
            responseBody.setMessage(message);
        } else {
            responseBody.setCode("110");
            responseBody.setMessage(e.getMessage());
        }

        responseEntity = new ResponseEntity<>(responseBody, headers, httpStatus);
        return responseEntity;
    }

}
