package br.com.neurotech.challenge.handler.model;

import java.time.LocalDateTime;

public record ExceptionHandlerMessage(
        Integer code,
        String message,
        LocalDateTime localDateTime) {
}
