package de.tomze.backend.controller;

import io.jsonwebtoken.JwtException;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.NotAuthorizedException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({
            EntityNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestException> handle404(Throwable e) {
        return createRestException(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            NotAuthorizedException.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<RestException> handle409(Throwable e) {
        return createRestException(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({
            IllegalArgumentException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestException> handle400(Throwable e) {
        return createRestException(e, HttpStatus.BAD_REQUEST);
    }




    private ResponseEntity<RestException> createRestException(Throwable e, HttpStatus httpStatus) {
        RestException restException = new RestException(e.getMessage(), httpStatus);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(restException, httpHeaders, httpStatus);
    }



    @Getter
    public static class RestException {

        private final String error;
        private final int status;

        public RestException(String error, HttpStatus httpStatus) {
            this.error = error;
            this.status = httpStatus.value();
        }
    }
}