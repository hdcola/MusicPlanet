package com.jac.fsd.musicplanet.exception;

import com.jac.fsd.musicplanet.DTO.ErrorDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDto> handleRuntimeException(RuntimeException e){
        log.error("runtime exception",e);
        return new ResponseEntity<>(
                new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),"internal server error"),
                HttpStatus.INTERNAL_SERVER_ERROR );
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorDto> handleWeatherException(AuthException e){
        log.error("authentication exception",e);
        return new ResponseEntity<>(
                new ErrorDto(e.getStatus().value(),e.getMessage()),
                e.getStatus());
    }
}
