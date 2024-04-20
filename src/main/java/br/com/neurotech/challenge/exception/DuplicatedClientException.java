package br.com.neurotech.challenge.exception;

public class DuplicatedClientException extends RuntimeException {
    public DuplicatedClientException(String message) {
        super(message);
    }
}
