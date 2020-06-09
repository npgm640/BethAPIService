package com.beth.infy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

public class HandleExceptions extends ResponseEntityExceptionHandler {

    //exception exceeding file size
    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    ResponseEntity<?> handleException(HttpServletRequest request, Throwable exception) {
        HttpStatus status = getStatus(request);
        return new ResponseEntity<>(exception.getMessage(), status);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer code = (Integer) request.getAttribute("java.servlet.error.status_code");
        if(code == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(code);
    }
}
