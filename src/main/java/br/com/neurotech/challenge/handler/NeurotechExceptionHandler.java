package br.com.neurotech.challenge.handler;

import br.com.neurotech.challenge.exception.EntityNotFoundException;
import br.com.neurotech.challenge.exception.DuplicatedClientException;
import br.com.neurotech.challenge.exception.OperationUnsupportedException;
import br.com.neurotech.challenge.handler.model.ExceptionHandlerMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class NeurotechExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleClientNotFoundException(
            EntityNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        return super.handleExceptionInternal(ex,
                new ExceptionHandlerMessage(
                        status.value(),
                        ex.getMessage(),
                        LocalDateTime.now()
                ),
                new HttpHeaders(), status, request
        );
    }

    @ExceptionHandler(DuplicatedClientException.class)
    public ResponseEntity<Object> handleDuplicatedClientException(
            DuplicatedClientException ex, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;

        return super.handleExceptionInternal(ex,
                new ExceptionHandlerMessage(
                        status.value(),
                        ex.getMessage(),
                        LocalDateTime.now()
                ),
                new HttpHeaders(), status, request
        );
    }

    @ExceptionHandler(OperationUnsupportedException.class)
    public ResponseEntity<Object> handleOperationUnsupportedFoundException(
            OperationUnsupportedException ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return super.handleExceptionInternal(ex,
                new ExceptionHandlerMessage(
                        status.value(),
                        ex.getMessage(),
                        LocalDateTime.now()
                ),
                new HttpHeaders(), status, request
        );
    }

}
